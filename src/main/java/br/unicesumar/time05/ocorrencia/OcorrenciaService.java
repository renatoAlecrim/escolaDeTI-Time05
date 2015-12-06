package br.unicesumar.time05.ocorrencia;

import br.unicesumar.time05.consultapersonalizada.ConstrutorDeSQL;
import br.unicesumar.time05.indigena.Indigena;
import br.unicesumar.time05.indigena.IndigenaService;
import classesbase.ServiceBase;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class OcorrenciaService extends ServiceBase<Ocorrencia, Long, OcorrenciaRepository> {
    @Autowired
    private IndigenaService indigenaServicy;
    
    private final String SQLConsultaOcorrencias = "SELECT o.idocorrencia, o.databloqueio, o.dataocorrencia, o.descricao "
            + "FROM ocorrencia o "
            + "LEFT JOIN indigena_ocorrencias io "
            + "  ON io.idocorrencia = o.idocorrencia "
            + "WHERE io.codigoassindi = :idIndigena";
    
    public OcorrenciaService() {
        setConstrutorDeSQL(new ConstrutorDeSQL(Ocorrencia.class));
    }

    @Override
    public void salvar(Ocorrencia aEntidade) {
        if(aEntidade.getDatabloqueio()!=null && aEntidade.getDatabloqueio().before(new Date(31557600000l)))
            aEntidade.setDatabloqueio(null);
        if(aEntidade.getDatabloqueio()!=null && aEntidade.getDatabloqueio().before(aEntidade.getDataocorrencia()))
            throw new RuntimeException("Data ocorrencia deve ser anterior a data bloqueio");
        repository.save(aEntidade);
        indigenaServicy.addOcorrencia(aEntidade);
    }

    public void remover(Long aID, Long aIdIndigena) {
        Ocorrencia ocorrencia = repository.findOne(aID);
        indigenaServicy.excluirOcorrencia(ocorrencia,aIdIndigena);
        repository.delete(aID);
    }
    
    
    public List<Map<String, Object>> listarSemPaginacao(Long idIndigena) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idIndigena", idIndigena);
        return query.execute(SQLConsultaOcorrencias,params);
    }
   
}
