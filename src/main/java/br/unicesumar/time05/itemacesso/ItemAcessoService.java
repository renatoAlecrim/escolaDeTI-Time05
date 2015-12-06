package br.unicesumar.time05.itemacesso;

import br.unicesumar.time05.consultapersonalizada.ConstrutorDeSQL;
import classesbase.ServiceBase;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class ItemAcessoService extends ServiceBase<ItemAcesso, Long, ItemAcessoRepository> {

    public ItemAcessoService() {
        setConstrutorDeSQL(new ConstrutorDeSQL(ItemAcesso.class));
    }

    public List<Map<String, Object>> getItensAcesso() {
        String vSql = " SELECT iditemacesso, nome, rota, icone, superior_id "
                + "   FROM itemacesso "
                + "  WHERE iditemacesso <> superior_id "
                + "    AND superior_id <> 1 ";

        List<Map<String, Object>> itensDeAcesso = super.query.execute(vSql, new MapSqlParameterSource());
        return Collections.unmodifiableList(itensDeAcesso);
    }

    public List<Map<String, Object>> getItensAcessoPorSuperior(Long aSuperiorId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("aSuperiorId", aSuperiorId);

        String vSql = " SELECT iditemacesso, nome, rota, icone, superior_id "
                + "   FROM itemacesso "
                + "  WHERE superior_id = :aSuperiorId ";

        List<Map<String, Object>> itensDeAcesso = super.query.execute(vSql, params);
        return Collections.unmodifiableList(itensDeAcesso);
    }

}
