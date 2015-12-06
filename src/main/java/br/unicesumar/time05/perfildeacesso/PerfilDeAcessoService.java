package br.unicesumar.time05.perfildeacesso;

import br.unicesumar.time05.consultapersonalizada.ConstrutorDeSQL;
import br.unicesumar.time05.itemacesso.ItemAcesso;
import br.unicesumar.time05.itemacesso.ItemAcessoRepository;
import classesbase.ServiceBase;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class PerfilDeAcessoService extends ServiceBase<PerfilDeAcesso, Long, PerfilDeAcessoRepository> {

    @Autowired
    private ItemAcessoRepository itemRepo;

    public PerfilDeAcessoService() {
        setConstrutorDeSQL(new ConstrutorDeSQL(PerfilDeAcesso.class));
    }

    public List<Map<String, Object>> getItensDeAcessoPorPerfilDeAcessoID(Long aId) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("aId", aId);

        String sql
                = "SELECT i.iditemacesso, "
                + "       i.nome, "
                + "       i.rota, "
                + "       i.icone, "
                + "       i.superior_id "
                + "  FROM perfildeacesso_itemacesso pi "
                + "  JOIN itemacesso i ON (pi.itemacesso_id = i.iditemacesso) "
                + " WHERE pi.perfildeacesso_id = :aId AND i.iditemacesso<>1 AND i.superior_id <> 1";

        List<Map<String, Object>> itensPerfilDeAcesso = super.query.execute(sql, params);
        return itensPerfilDeAcesso;
    }

    void salvarPefil(PerfilBuilder perfilBuilder) {
        PerfilDeAcesso perfil = new PerfilDeAcesso();
        List<ItemAcesso> itens = new ArrayList<>();
        itens.add(itemRepo.findOne(1l));
        
        for (ItemAcesso item : perfilBuilder.getItens()) {
            itens.add(itemRepo.findOne(item.getIditemacesso()));
            itens.add(itemRepo.findOne(item.getIditemacesso()).getSuperior());
        }

        if (perfilBuilder.getIdperfil()!= null) {
            perfil = repository.findOne(perfilBuilder.getIdperfil());
            perfil.setNome(perfilBuilder.getNome());
            perfil.setItens(new HashSet<>(itens));
        } else {
            perfil.setNome(perfilBuilder.getNome());
            perfil.addItens(new HashSet<>(itens));
        }
        repository.save(perfil);
    }
}
