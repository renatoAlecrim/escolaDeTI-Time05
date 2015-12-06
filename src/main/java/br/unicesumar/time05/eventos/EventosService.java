package br.unicesumar.time05.eventos;

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
public class EventosService extends ServiceBase<Eventos, Long, EventosRepository> {

    private final String sql
            = "      select e.descricao, "
            + " 	    e.horainicial, "
            + " 	    e.horafinal, "
            + " 	    e.datainicial, "
            + " 	    e.datafinal, "
            + " 	    e.visualizarnocalendario, "
            + "             'EVENTO' as tipo, "
            + "             '' as idade, "
            + "             'event-notif' as tipoevento "
            + "  from eventos e "
            + " union all "
            + "select 'Aniversário '||p.nome as descricao, "
            + "       '00:00:00' as horainicial, "
            + "       '23:59:00' as horafinal, "
            + "       date(to_char(pf.datanascimento,'DD/MM')||'/'||to_char(CURRENT_DATE,'YYYY')) as datainicial,  "
            + "       date(to_char(pf.datanascimento,'DD/MM')||'/'||to_char(CURRENT_DATE,'YYYY')) as datafinal, "
            + "       true as visualizarnocalendario, "
            + "       'ANIVERSARIO' as tipo, "
            + "       to_char(age(to_date(to_char(CURRENT_DATE,'DD/MM/YYYY'),'DD/MM/YYYY'),pf.datanascimento),'YY') as idade, "
            + "       'event-aniver' as tipoevento "
            + "  from pessoa p "
            + " inner join pessoa_fisica pf "
            + "    on pf.idpessoa = p.idpessoa "
            + " union all "
            + " select 'Aniversário '||i.nome as descricao, "
            + "        '00:00:00' as horainicial, "
            + "        '23:59:00' as horafinal, "
            + "        date(to_char(i.datanascimento,'DD/MM')||'/'||to_char(CURRENT_DATE,'YYYY')) as datainicial,  "
            + "        date(to_char(i.datanascimento,'DD/MM')||'/'||to_char(CURRENT_DATE,'YYYY')) as datafinal, "
            + "        true as visualizarnocalendario, "
            + "        'ANIVERSARIO' as tipo, "
            + "        to_char(age(to_date(to_char(CURRENT_DATE,'DD/MM/YYYY'),'DD/MM/YYYY'),i.datanascimento),'YY') as idade, "
            + "        'event-aniver' as tipoevento "
            + "   from indigena i "
            + "  union all "
            + "  select 'Visita '||coalesce(p.nome,ps.nome) as descricao, "
            + "  	coalesce(v.horaentrada,'00:00:00') as horainicial, "
            + "         coalesce(v.horasaida,'00:00:00') as horafinal, "
            + "         v.datavisita as datainicial, "
            + "         v.datavisita as datafinal, "
            + "         true as visualizarnocalendario, "
            + "         'VISITA' as tipo, "
            + "         '' as idade, "
            + "         'event-visita' as tipoevento "
            + "    from visita v "
            + "    left join pessoa p "
            + "      on p.idpessoa = v.identidade "
            + "    left join pessoa ps "
            + "      on ps.idpessoa = v.idpessoaresponsavel ";

    public EventosService() {
        setConstrutorDeSQL(new ConstrutorDeSQL(Eventos.class));
    }

    @Override
    public RetornoConsultaPaginada listar(ParametrosConsulta aParametrosConsulta) {
        String palavraChave = aParametrosConsulta.getPalavraChave();
        if(palavraChave.equalsIgnoreCase("sim"))
            aParametrosConsulta.setPalavraChave("TRUE");
        if(palavraChave.equalsIgnoreCase("não"))
            aParametrosConsulta.setPalavraChave("FALSE");
        
        return super.listar(aParametrosConsulta); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<Map<String, Object>> carregaCalendario() {
        return super.query.execute(sql);
    }

}
