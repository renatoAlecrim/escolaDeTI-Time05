package br.unicesumar.time05.estadiafamilia;

import br.unicesumar.time05.consultapersonalizada.ConstrutorDeSQL;
import br.unicesumar.time05.relatorios.formatoRelatorio;
import br.unicesumar.time05.relatorios.relEstadiaBase;
import classesbase.ServiceBase;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

@Component
public class EstadiaService extends ServiceBase<Estadia, Long, EstadiaRepository> {

    String sqlPadrao
            = "  SELECT es.idestadia, "
            + "         es.dataentrada, "
            + "         es.datasaida, "
            + "         es.idestadia, "
            + "         f.nomefamilia, "
            + "         i.nome, "
            + "         i.telefone, "
            + "         es.observacoesentrada, "
            + "         es.observacoessaida, "
            + "         COUNT(ei.codigoassindi) as quantidademembros "
            + "    FROM estadia es "
            + "    LEFT JOIN estadia_indigena ei ON es.idestadia = ei.idestadia "
            + "    LEFT JOIN familia f ON es.idfamilia = f.idfamilia "
            + "    LEFT JOIN indigena i ON es.idrepresentante = i.codigoassindi "
            + "GROUP BY es.idestadia , f.idfamilia, i.codigoassindi";

    String sqlPadraoComWhere
            = "  SELECT es.idestadia, "
            + "         es.dataentrada, "
            + "         es.datasaida, "
            + "         es.idestadia, "
            + "         f.nomefamilia, "
            + "         i.nome as representante, "
            + "         i.telefone as telefonerepresentante, "
            + "         es.observacoesentrada, "
            + "         es.observacoessaida, "
            + "         COUNT(ei.codigoassindi) as quantidademembros "
            + "    FROM estadia es "
            + "    LEFT JOIN estadia_indigena ei ON es.idestadia = ei.idestadia "
            + "    LEFT JOIN familia f ON es.idfamilia = f.idfamilia "
            + "    LEFT JOIN indigena i ON es.idrepresentante = i.codigoassindi "
            + "   WHERE es.idestadia = :idestadia "
            + " GROUP BY es.idestadia , f.idfamilia, i.codigoassindi";

    public EstadiaService() {
        setConstrutorDeSQL(new ConstrutorDeSQL(Estadia.class));
        setSqlPadrao(sqlPadrao, "es.dataentrada");
        setSqlPadraoPorID(sqlPadraoComWhere, "es.dataentrada", "idestadia");
    }

    public Map<String, Object> getRepresentante(Long aIdEstadia) {
        String sqlRepresentante
                = "SELECT i.codigoassindi,  "
                + "       i.codigoSUS, "
                + "       i.cpf, "
                + "       i.datanascimento, "
                + "       e.descricao, "
                + "       i.escolaridade, "
                + "       i.estadocivil, "
                + "       i.genero, "
                + "       i.nome, "
                + "       i.telefone, "
                + "       ti.nometerra "
                + " FROM estadia es "
                + " JOIN indigena i ON es.idrepresentante = i.codigoassindi "
                + " LEFT JOIN etnia e ON i.etnia_idetnia = e.idetnia "
                + " LEFT JOIN terraindigena ti ON i.terraindigena_idterraindigena = ti.idterraindigena "
                + " WHERE es.idestadia = :aIdEstadia";

        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("aIdEstadia", aIdEstadia);

        List<Map<String, Object>> representante = query.execute(sqlRepresentante, params);

        try {
            return representante.get(0);
        } catch (Exception e) {
            throw new RuntimeException("Nenhum resultado encontrado!");
        }
    }

    public List<Map<String, Object>> getMembros(Long aIdEstadia) {
        String sqlRepresentante
                = "SELECT i.codigoassindi,  "
                + "       i.codigoSUS, "
                + "       i.cpf, "
                + "       i.datanascimento, "
                + "       e.descricao, "
                + "       i.escolaridade, "
                + "       i.estadocivil, "
                + "       i.genero, "
                + "       i.nome, "
                + "       i.telefone, "
                + "       ti.nometerra "
                + " FROM estadia_indigena ei "
                + " JOIN indigena i ON ei.codigoassindi = i.codigoassindi "
                + " LEFT JOIN etnia e ON i.etnia_idetnia = e.idetnia "
                + " LEFT JOIN terraindigena ti ON i.terraindigena_idterraindigena = ti.idterraindigena "
                + " WHERE ei.idestadia = :aIdEstadia";

        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("aIdEstadia", aIdEstadia);

        List<Map<String, Object>> estadia = query.execute(sqlRepresentante, params);

        try {
            return estadia;
        } catch (Exception e) {
            throw new RuntimeException("Nenhum resultado encontrado!");
        }
    }

    void addDataSaida(SaidaEstadia saida) {
        Estadia estadia = (Estadia) this.getObjeto(saida.getIdestadia());
        estadia.setDatasaida(saida.getDatasaida());
        estadia.setObservacoessaida(saida.getObservacoessaida());
        this.alterar(estadia);
    }
    
    public Map<String, String> gerarRelatorio(formatoRelatorio formatoRelatorio, ParametrosRelatorioEstadia parametros) {
        ObjectMapper objMapper = new ObjectMapper();
        Map<String, Object> MapParametros = objMapper.convertValue(parametros, Map.class);
        return rel.gerarRelatorio("Estadia.jrxml", formatoRelatorio, MapParametros);
    }
}
