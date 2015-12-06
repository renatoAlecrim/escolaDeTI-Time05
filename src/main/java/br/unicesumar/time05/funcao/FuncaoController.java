package br.unicesumar.time05.funcao;

import classesbase.ControllerBase;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/funcao")
public class FuncaoController extends ControllerBase<Funcao, Long, FuncaoService>{
    
    @RequestMapping(value = "/verificarDescricao/{aDescricao:.+}", method = RequestMethod.GET)
    public boolean verifcarDescricao(@PathVariable String aDescricao) {
        return service.verificarDescricao(aDescricao);
    }
}