package br.unicesumar.time05.perfildeacesso;

import classesbase.ControllerBase;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/perfildeacesso")
public class PerfilDeAcessoController extends ControllerBase<PerfilDeAcesso, Long, PerfilDeAcessoService>{
    
    @RequestMapping(value = "/salvar", method = RequestMethod.POST)
    public void salvar(@RequestBody PerfilBuilder perfilBuilder){
        service.salvarPefil(perfilBuilder);
    }
    
    @RequestMapping(value = "/alterar", method = RequestMethod.PUT)
    public void alterar(@RequestBody PerfilBuilder perfilBuilder){
        service.salvarPefil(perfilBuilder);
    }
    
    @RequestMapping(value = "/itensdeacesso/{aIdPerfilDeAcesso}", method = RequestMethod.GET)
    public List<Map<String, Object>> getItensDeAcessoPorPerfilDeAcessoID(@PathVariable Long aIdPerfilDeAcesso){
        return service.getItensDeAcessoPorPerfilDeAcessoID(aIdPerfilDeAcesso);
    }
}
