package br.unicesumar.time05.indigena;

import br.unicesumar.time05.consultapersonalizada.ConstrutorDeSQL;
import br.unicesumar.time05.consultapersonalizada.ParametrosConsulta;
import br.unicesumar.time05.consultapersonalizada.RetornoConsultaPaginada;
import br.unicesumar.time05.etnia.Etnia;
import br.unicesumar.time05.etnia.EtniaService;
import br.unicesumar.time05.ocorrencia.Ocorrencia;
import br.unicesumar.time05.relatorios.formatoRelatorio;
import br.unicesumar.time05.terraindigena.TerraIndigena;
import br.unicesumar.time05.terraindigena.TerraIndigenaService;
import br.unicesumar.time05.upload.UploadService;
import classesbase.ServiceBase;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class IndigenaService extends ServiceBase<CriarIndigena, Long, IndigenaRepository> {

    public IndigenaService() {
        setConstrutorDeSQL(new ConstrutorDeSQL(Indigena.class));
    }

    @Autowired
    private TerraIndigenaService terraService;
    @Autowired
    private EtniaService etniaService;
    @Autowired
    private UploadService uploadService;

    //Select modigicado dia 08/08 Bruno Fiorentini/Thiago Marialva
    private final String SQLConsultaIndigena = " SELECT I.CODIGOASSINDI, "
            + " I.CODIGOSUS, "
            + " I.CPF, "
            + " I.DATANASCIMENTO, "
            + " E.DESCRICAO, "
            + " I.ESCOLARIDADE, "
            + " I.ESTADOCIVIL, "
            + " I.GENERO, "
            + " I.NOME, "
            + " I.TELEFONE, "
            + " TI.NOMETERRA, "
            + " (SELECT CASE "
            + "          WHEN IND.CODIGOASSINDI IS NOT NULL THEN 'BLOQUEADO' "
            + "          ELSE 'SEM BLOQUEIO' "
            + "         END AS STATUS "
            + "    FROM INDIGENA IND "
            + "    INNER JOIN INDIGENA_OCORRENCIAS IO "
            + "      ON IO.CODIGOASSINDI = I.CODIGOASSINDI "
            + "    INNER JOIN OCORRENCIA O "
            + "      ON O.IDOCORRENCIA = IO.IDOCORRENCIA "
            + "   WHERE O.DATABLOQUEIO > CURRENT_DATE "
            + "     AND IND.CODIGOASSINDI = I.CODIGOASSINDI) AS BLOQUEIO "
            + " FROM INDIGENA I "
            + " LEFT JOIN ETNIA E "
            + "   ON I.ETNIA_IDETNIA = E.IDETNIA "
            + " LEFT JOIN TERRAINDIGENA TI "
            + "   ON I.TERRAINDIGENA_IDTERRAINDIGENA = TI.IDTERRAINDIGENA";

    //Select modigicado dia 08/08 Bruno Fiorentini/Thiago Marialva
    private final String SQLCOnsultaIndigenaPorId = "SELECT i.codigoassindi,  i.codigoSUS, "
            + " i.cpf, i.datanascimento, e.descricao, i.escolaridade,i.estadocivil, "
            + " i.genero, i.nome, i.telefone, ti.nometerra "
            + " FROM indigena i "
            + " LEFT JOIN etnia e "
            + "  ON i.etnia_idetnia = e.idetnia "
            + " LEFT JOIN terraindigena ti "
            + "  ON i.terraindigena_idterraindigena = ti.idterraindigena "
            + " WHERE i.codigoassindi = :idIndigena";

    @Override
    public void salvar(CriarIndigena aIndigena) {
        Indigena i = new Indigena(null, aIndigena.getNome(), aIndigena.getCpf(), null, aIndigena.getGenero(), aIndigena.getDataNascimento(), aIndigena.getConvenio(), aIndigena.getTelefone(), null, aIndigena.getEscolaridade(), aIndigena.getEstadoCivil(), aIndigena.getCodigoSUS(), aIndigena.getOcorrencias());
        i.setTerraIndigena((TerraIndigena) terraService.getObjeto(aIndigena.getTerraIndigena()));
        i.setEtnia((Etnia) etniaService.getObjeto(aIndigena.getEtnia()));
        repository.save(i);
        repository.flush();
        if (aIndigena.getImgSrc() != null && aIndigena.getImgSrc().startsWith("data:image/jpeg;base64")) {
            uploadService.uploadWebcam(aIndigena.getImgSrc(), i.getCodigoAssindi(), "indios");
        }
    }

    @Override
    public void alterar(CriarIndigena aIndigena) {
        Indigena i = new Indigena(aIndigena.getCodigoAssindi(), aIndigena.getNome(), aIndigena.getCpf(), null, aIndigena.getGenero(), aIndigena.getDataNascimento(), aIndigena.getConvenio(), aIndigena.getTelefone(), null, aIndigena.getEscolaridade(), aIndigena.getEstadoCivil(), aIndigena.getCodigoSUS(), repository.getOne(aIndigena.getCodigoAssindi()).getOcorrencia());
        i.setTerraIndigena((TerraIndigena) terraService.getObjeto(aIndigena.getTerraIndigena()));
        i.setEtnia((Etnia) etniaService.getObjeto(aIndigena.getEtnia()));
        if (aIndigena.getImgSrc() != null && aIndigena.getImgSrc().startsWith("data:image/jpeg;base64")) {
            uploadService.uploadWebcam(aIndigena.getImgSrc(), aIndigena.getCodigoAssindi(), "indios");
        }

        repository.save(i);
    }

    public void addOcorrencia(Ocorrencia aOcorrencia) {
        Indigena aIndigena = repository.findOne(aOcorrencia.getIdIndigena());
        aIndigena.setOcorrencia(aOcorrencia);
        repository.save(aIndigena);
    }

    public void excluirOcorrencia(Ocorrencia aOcorrencia, Long aId) {
        Indigena aIndigena = repository.findOne(aId);
        aIndigena.removerOcorrencia(aOcorrencia);
        repository.save(aIndigena);
    }

    @Override
    public List<Map<String, Object>> findByID(Long aCodigoAssindi) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idIndigena", aCodigoAssindi);

        List<Map<String, Object>> aIndigena = query.execute(SQLCOnsultaIndigenaPorId, params);

        return Collections.unmodifiableList(aIndigena);
    }

    @Override
    public RetornoConsultaPaginada listar(ParametrosConsulta parametrosConsulta) {
        return query.executeComPaginacao(SQLConsultaIndigena, "i.nome", parametrosConsulta);
    }

    @Override
    public RetornoConsultaPaginada listar() {
        return query.executeComPaginacao(SQLConsultaIndigena, "i.nome", new ParametrosConsulta());
    }

    @Override
    public List<Map<String, Object>> listarSemPaginacao() {
        return query.execute(SQLConsultaIndigena);
    }

    public Map<String, String> gerarRelatorio(formatoRelatorio formatoRelatorio, ParametrosRelatorioIndigena parametros) {
        ObjectMapper objMapper = new ObjectMapper();
        Map<String, Object> MapParametros = objMapper.convertValue(parametros, Map.class);
        return rel.gerarRelatorio("Indigena.jrxml", formatoRelatorio, MapParametros);
    }

}
