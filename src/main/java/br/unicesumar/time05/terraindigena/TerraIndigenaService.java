package br.unicesumar.time05.terraindigena;

import br.unicesumar.time05.consultapersonalizada.ConstrutorDeSQL;
import br.unicesumar.time05.consultapersonalizada.ParametrosConsulta;
import br.unicesumar.time05.consultapersonalizada.RetornoConsultaPaginada;
import classesbase.ServiceBase;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class TerraIndigenaService extends ServiceBase<TerraIndigena, Long, TerraIndigenaRepository>{

    
    public TerraIndigenaService() {
        setConstrutorDeSQL(new ConstrutorDeSQL(TerraIndigena.class));
    }
    
    private final String SQLConsultaPorId = 
              "SELECT t.idterraindigena, t.nometerra, c.descricao AS cidade, u.descricao, u.sigla"
            + " FROM terraindigena t"
            + " LEFT JOIN cidade c"
            + " ON t.cidade_codigoibge = c.codigoibge"
            + " LEFT JOIN uf u"
            + " ON c.estado_codigoestado = u.codigoestado"
            + " WHERE t.idterraindigena = :idTerraIndigena";
    
    private final String SQLConsultaTerraIndigena = 
              "SELECT t.idterraindigena, t.nometerra, c.descricao AS cidade, u.descricao, u.sigla"
            + " FROM terraindigena t"
            + " LEFT JOIN cidade c"
            + " ON t.cidade_codigoibge = c.codigoibge"
            + " LEFT JOIN uf u"
            + " ON c.estado_codigoestado = u.codigoestado";
    
    @Override
    public List<Map<String, Object>> findByID(Long id) {
        return query.executePorID(SQLConsultaPorId, id);
    }

    @Override
    public RetornoConsultaPaginada listar(ParametrosConsulta parametrosConsulta) {
        return query.executeComPaginacao(SQLConsultaTerraIndigena,"t.nometerra", parametrosConsulta);
    }

    @Override
    public RetornoConsultaPaginada listar() {
        return query.executeComPaginacao(SQLConsultaTerraIndigena,"t.nometerra", new ParametrosConsulta());
    }

    @Override
    public List<Map<String, Object>> listarSemPaginacao() {
        return query.execute(SQLConsultaTerraIndigena);
    }
}
