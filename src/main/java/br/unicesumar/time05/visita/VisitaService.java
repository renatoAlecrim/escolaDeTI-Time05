package br.unicesumar.time05.visita;

import br.unicesumar.time05.consultapersonalizada.ConstrutorDeSQL;
import br.unicesumar.time05.consultapersonalizada.ParametrosConsulta;
import br.unicesumar.time05.consultapersonalizada.RetornoConsultaPaginada;
import br.unicesumar.time05.relatorios.formatoRelatorio;
import classesbase.ServiceBase;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Time;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class VisitaService extends ServiceBase<Visita, Long, VisitaRepository> {

    private final String SQLConsultaVisita = ("SELECT v.idvisita, "
            + " v.datavisita, "
            + " v.horavisita, "
            + " v.quantidadedevisitantes, "
            + " v.observacao, "
            + " v.horaentrada, "
            + " v.horasaida, "
            + " v.seriecurso, "
            + " v.idpessoaresponsavel, "
            + " v.identidade, "
            + " v.visitarealizada,"
            + " pf.nome as nomePF, "
            + " pj.nome as nomePJ "
            + " FROM visita v "
            + " INNER JOIN pessoa pf ON pf.idpessoa = v.idpessoaresponsavel LEFT JOIN pessoa pj ON pj.idpessoa = v.identidade");

    public VisitaService() {
        setConstrutorDeSQL(new ConstrutorDeSQL(Visita.class));
    }

    @Override
    public void salvar(Visita aEntidade) {
        if(aEntidade.getEntidade() != null && aEntidade.getEntidade().getCnpj()==null){
            aEntidade.setEntidade(null);
        }
        super.salvar(aEntidade); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<Map<String, Object>> findByID(Long aVisitaId) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("aVisitaId", aVisitaId);
        List<Map<String, Object>> visita = query.execute(this.SQLConsultaVisita + "WHERE v.idvista =:aVisitaId", params);
        return Collections.unmodifiableList(visita);
    }

    @Override
    public RetornoConsultaPaginada listar(ParametrosConsulta parametrosConsulta) {
        return query.executeComPaginacao(this.SQLConsultaVisita, "", parametrosConsulta);
    }

    @Override
    public RetornoConsultaPaginada listar() {
        return query.executeComPaginacao(this.SQLConsultaVisita, "", new ParametrosConsulta());
    }

    @Override
    public List<Map<String, Object>> listarSemPaginacao() {
        List<Map<String, Object>> visitas = query.execute(this.SQLConsultaVisita, new MapSqlParameterSource());
        return Collections.unmodifiableList(visitas);
    }

    public Map<String, String> gerarRelatorio(formatoRelatorio formatoRelatorio, ParametrosRelatorioVisita parametros) {
        ObjectMapper objMapper = new ObjectMapper();
        Map<String, Object> MapParametros = objMapper.convertValue(parametros, Map.class);
        return rel.gerarRelatorio("Visita.jrxml", formatoRelatorio, MapParametros);
    }
}
