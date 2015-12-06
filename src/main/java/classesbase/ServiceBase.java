package classesbase;

import br.unicesumar.time05.consultapersonalizada.ConstrutorDeSQL;
import br.unicesumar.time05.consultapersonalizada.ParametrosConsulta;
import br.unicesumar.time05.consultapersonalizada.QueryPersonalizada;
import br.unicesumar.time05.consultapersonalizada.RetornoConsultaPaginada;
import br.unicesumar.time05.relatorios.Relatorio;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class ServiceBase<Entidade extends Object, ID extends Serializable, Reposity extends JpaRepository> {

    @Autowired
    public Reposity repository;

    @Autowired
    protected QueryPersonalizada query;

    @Autowired
    protected Relatorio rel;
    
    private ConstrutorDeSQL ConstrutorDeSQL;

    private String sqlPadrao;
    private String sqlPadraoPorID;
    private String nomeParametroPadrao;
    private String campoOrdenacaoPadrao;

    protected void setSqlPadrao(String sqlPadrao, String campoOrdenacaoPadrao) {
        this.sqlPadrao = sqlPadrao;
        this.campoOrdenacaoPadrao = campoOrdenacaoPadrao;
    }

    protected void setSqlPadraoPorID(String sqlPadraoPorID, String campoOrdenacaoPadrao, String nomeParametroPadrao) {
        this.sqlPadraoPorID = sqlPadraoPorID;
        this.campoOrdenacaoPadrao = campoOrdenacaoPadrao;
        this.nomeParametroPadrao = nomeParametroPadrao;
    }

    protected void setNomeParametroPadrao(String nomeParametroPadrao) {
        this.nomeParametroPadrao = nomeParametroPadrao;
    }

    protected void setConstrutorDeSQL(ConstrutorDeSQL aConstrutorDeSQL) {
        this.ConstrutorDeSQL = aConstrutorDeSQL;
    }

    public void salvar(Entidade aEntidade) {
        repository.save(aEntidade);
    }

    public void remover(ID aID) {
        repository.delete(aID);
    }

    public void alterar(Entidade aEntidade) {
        repository.save(aEntidade);
    }

    public List<Map<String, Object>> findByID(ID aID) {
        if (this.temSqlPadraoPorIdSetado()) {
            return query.execute(sqlPadraoPorID, new MapSqlParameterSource(nomeParametroPadrao, aID));
        } else {
            return query.executePorID(ConstrutorDeSQL.getSQLComWherePorID(), aID);
        }
    }

    public RetornoConsultaPaginada listar(ParametrosConsulta aParametrosConsulta) {
        if (aParametrosConsulta.getPalavraChave().contains("-")) {
            String data = aParametrosConsulta.getPalavraChave();
            String[] sub = data.split("-");
            if (sub.length == 1) {
                aParametrosConsulta.setPalavraChave(sub[0]);
            } else {
                if (sub.length == 2) {
                    aParametrosConsulta.setPalavraChave(sub[1] + "-" + sub[0]);
                } else {
                    aParametrosConsulta.setPalavraChave(sub[2] + "-" + sub[1] + "-" + sub[0]);
                }
            }
        }
//        System.out.println(aParametrosConsulta.getPalavraChave());
        if (this.temSqlPadraoSetado()) {
            return query.executeComPaginacao(sqlPadrao, campoOrdenacaoPadrao, aParametrosConsulta);
        } else {
            return query.executeComPaginacao(ConstrutorDeSQL, aParametrosConsulta);
        }
    }

    public Object getObjeto(ID aId) {
        return repository.findOne(aId);
    }

    public RetornoConsultaPaginada listar() {
        if (this.temSqlPadraoSetado()) {
            return query.executeComPaginacao(sqlPadrao, campoOrdenacaoPadrao, new ParametrosConsulta());
        } else {
            return query.executeComPaginacao(ConstrutorDeSQL, new ParametrosConsulta());
        }
    }

    public List<Map<String, Object>> listarSemPaginacao() {
        if (this.temSqlPadraoSetado()) {
            return query.execute(sqlPadrao);
        } else {
            return query.execute(ConstrutorDeSQL.getSQL(new ParametrosConsulta()));
        }
    }

    private boolean temSqlPadraoSetado() {
        return this.sqlPadrao != null && !sqlPadrao.isEmpty()
                && this.campoOrdenacaoPadrao != null && !this.campoOrdenacaoPadrao.isEmpty();
    }

    private boolean temSqlPadraoPorIdSetado() {
        return this.sqlPadraoPorID != null && !this.sqlPadraoPorID.isEmpty()
                && this.nomeParametroPadrao != null && !this.nomeParametroPadrao.isEmpty()
                && this.campoOrdenacaoPadrao != null && !this.campoOrdenacaoPadrao.isEmpty();
    }
}
