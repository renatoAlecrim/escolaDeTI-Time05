package br.unicesumar.time05.consultapersonalizada;

import br.unicesumar.time05.rowmapper.MapRowMapper;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class QueryPersonalizada {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private MapRowMapper rowMapper;
    @Autowired
    private RetornoConsultaPaginada retornoConsulta;

    public List<Map<String, Object>> execute(String aSQL) {
        return this.execute(aSQL, new MapSqlParameterSource());
    }

    public List<Map<String, Object>> execute(String aSQL, MapSqlParameterSource aParams) {
        return Collections.unmodifiableList(jdbcTemplate.query(aSQL, aParams, rowMapper));
    }

    public List<Map<String, Object>> executePorID(String aSQL, Object aID) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(OperadoresSQL.NOME_PARAMETRO_PARA_IGUAL, aID);
        return Collections.unmodifiableList(jdbcTemplate.query(aSQL, params, rowMapper));
    }

    public RetornoConsultaPaginada executeComPaginacao(ConstrutorDeSQL aConstrutorDeSQL, ParametrosConsulta aParametrosConsulta) {
        String SQL;
        SQL = aConstrutorDeSQL.getSQL(aParametrosConsulta);
        return executeComPaginacao(SQL, aConstrutorDeSQL.getCampoOrdenacaoPadrao(), aParametrosConsulta);
    }

    public RetornoConsultaPaginada executeComPaginacao(String aSQL, String aCampoOrdenacaoPadrao, ParametrosConsulta aParametrosConsulta) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        if ((aParametrosConsulta != null) && (aParametrosConsulta.getPalavraChave() != null) && (!aParametrosConsulta.getPalavraChave().isEmpty())) {
            if (!aSQL.contains(OperadoresSQL.WHERE.trim())) {

                if (aSQL.contains(OperadoresSQL.GROUP_BY)) {
                    StringBuilder strBuilder = new StringBuilder(aSQL);
                    strBuilder.insert(aSQL.indexOf(OperadoresSQL.GROUP_BY), this.adicionaWhereEmSQL(aSQL));
                    aSQL = strBuilder.toString();
                } else {
                    aSQL += this.adicionaWhereEmSQL(aSQL);
                }
            }
            params.addValue(OperadoresSQL.NOME_PARAMETRO_PARA_LIKE, "%" + aParametrosConsulta.getPalavraChave() + "%");
            params.addValue(OperadoresSQL.NOME_PARAMETRO_PARA_IGUAL, aParametrosConsulta.getPalavraChave());
        }

        List<Map<String, Object>> result = jdbcTemplate.query(aSQL, params, rowMapper);
        retornoConsulta.setTotalDeRegistros(result.size());

        Double paginas = (double) result.size() / aParametrosConsulta.getRegistrosPorPagina();
        retornoConsulta.setQuantidadeDePaginas((int) Math.ceil(paginas));
        retornoConsulta.setPaginaAtual(aParametrosConsulta.getPagina());

        if ((aParametrosConsulta != null) && (aParametrosConsulta.getOrdenarPor() != null) && (!aParametrosConsulta.getOrdenarPor().isEmpty())) {
            aSQL += (OperadoresSQL.ORDER_BY + aParametrosConsulta.getOrdenarPor());
            if ((!aParametrosConsulta.getSentidoOrdenacao().isEmpty()) && (aParametrosConsulta.getSentidoOrdenacao().equalsIgnoreCase(OperadoresSQL.DESC.trim()))) {
                aSQL += OperadoresSQL.DESC;
            }
        } else {
            if (aCampoOrdenacaoPadrao.isEmpty()) {
                aCampoOrdenacaoPadrao = "1";
            }
            aSQL += OperadoresSQL.ORDER_BY + aCampoOrdenacaoPadrao;
        }

        if ((aParametrosConsulta != null) && (aParametrosConsulta.getPagina() > 0)) {
            aSQL += OperadoresSQL.LIMIT + aParametrosConsulta.getRegistrosPorPagina() + OperadoresSQL.OFFSET + ((aParametrosConsulta.getPagina() * aParametrosConsulta.getRegistrosPorPagina()) - aParametrosConsulta.getRegistrosPorPagina());
        }

//        System.out.println(aSQL);
        retornoConsulta.setListaDeRegistros(Collections.unmodifiableList(jdbcTemplate.query(aSQL, params, rowMapper)));
        return retornoConsulta;
    }

    private String adicionaWhereEmSQL(String aSQL) {

        String StringComOsCampos = aSQL.substring(aSQL.indexOf(OperadoresSQL.SELECT.trim()) + OperadoresSQL.SELECT.trim().length(), aSQL.indexOf(OperadoresSQL.FROM.trim()));
        String[] campos = StringComOsCampos.split(",");
        String campoSemFormatacao;
        for (int i = 0; i < campos.length; i++) {
            campoSemFormatacao = campos[i];
            campoSemFormatacao = campoSemFormatacao.trim();
            if (campoSemFormatacao.contains(" ")) {
                campoSemFormatacao = campoSemFormatacao.substring(0, campoSemFormatacao.indexOf(" "));
            }
            campos[i] = campoSemFormatacao;
        }

        String camposDoWhere = "";
        for (String campo : campos) {
            if (!campoPossuiAgregacao(campo)) {
                
                if (camposDoWhere.isEmpty()) {
                    camposDoWhere += "((" + campo.trim() + "::varchar " + OperadoresSQL.ILIKE + OperadoresSQL.PARAMETRO_PARA_LIKE + ")";
                } else {
                    camposDoWhere += OperadoresSQL.OR + "(" + campo.trim() + "::varchar " + OperadoresSQL.ILIKE + OperadoresSQL.PARAMETRO_PARA_LIKE + ")";
                }
            }
        }
        camposDoWhere += ")";

        return OperadoresSQL.WHERE + camposDoWhere;
    }

    private boolean campoPossuiAgregacao(String campo) {
        return campo.contains(OperadoresSQL.SUM.trim())
                || campo.toUpperCase().contains(OperadoresSQL.COUNT.trim())
                || campo.toUpperCase().contains(OperadoresSQL.AVG.trim())
                || campo.toUpperCase().contains(OperadoresSQL.MAX.trim())
                || campo.toUpperCase().contains(OperadoresSQL.MIN.trim());
    }
}
