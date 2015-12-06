package br.unicesumar.time05.uf;

import br.unicesumar.time05.consultapersonalizada.ConstrutorDeSQL;
import classesbase.ServiceBase;
import javax.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class UfService extends ServiceBase<UF, Long, UFRepository>{

    public UfService() {
        setConstrutorDeSQL(new ConstrutorDeSQL(UF.class));
    }

}
