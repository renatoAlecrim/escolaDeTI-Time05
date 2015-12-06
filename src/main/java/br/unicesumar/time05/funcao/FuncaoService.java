package br.unicesumar.time05.funcao;

import br.unicesumar.time05.consultapersonalizada.ConstrutorDeSQL;
import classesbase.ServiceBase;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
class FuncaoService extends ServiceBase<Funcao, Long, FuncaoRepository> {

    public FuncaoService() {
        setConstrutorDeSQL(new ConstrutorDeSQL(Funcao.class));
    }

    public boolean verificarDescricao(String aDescricao) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("aDescricao", aDescricao);
        List<Map<String, Object>> funcao = super.query.execute("SELECT descricao FROM FUNCAO WHERE descricao = :aDescricao", params);
        return funcao.isEmpty();
    }

}
