package br.unicesumar.time05.cidade;

import br.unicesumar.time05.consultapersonalizada.ConstrutorDeSQL;
import br.unicesumar.time05.consultapersonalizada.ParametrosConsulta;
import br.unicesumar.time05.consultapersonalizada.RetornoConsultaPaginada;
import classesbase.ServiceBase;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

@Component
public class CidadeService extends ServiceBase<Cidade, Long, CidadeRepository> {

    private final String sqlCidade
            = "SELECT c.codigoibge,"
            + "       c.descricao,"
            + "       uf.codigoestado,"
            + "       uf.descricao as estados,"
            + "       uf.sigla"
            + "  FROM cidade c"
            + "  JOIN uf on c.estado_codigoestado = uf.codigoestado";

    public CidadeService() {
        setConstrutorDeSQL(new ConstrutorDeSQL(Cidade.class));
    }
    
    @Override
    public List<Map<String, Object>> findByID(Long id) {
        return query.execute(sqlCidade+" WHERE c.codigoibge = "+ id+"  ORDER BY c.descricao ASC ");
    }

    @Override
    public Cidade getObjeto(Long aId) {
        return (Cidade)repository.findOne(aId);
    }

    @Override
    public RetornoConsultaPaginada listar(ParametrosConsulta parametrosConsulta) {
        return query.executeComPaginacao(sqlCidade,"c.descricao", parametrosConsulta);
    }

    @Override
    public RetornoConsultaPaginada listar() {
        return query.executeComPaginacao(sqlCidade,"c.descricao", new ParametrosConsulta());
    }

    @Override
    public List<Map<String, Object>> listarSemPaginacao() {
        return query.execute(sqlCidade);
    }
    
    public List<Map<String, Object>> listarCidadesPorUF(int aCodigoEstado){
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("codigoestado", aCodigoEstado);
        return query.execute(sqlCidade + " WHERE uf.codigoestado = :codigoestado ORDER BY c.descricao ASC ", params);
    }
}
