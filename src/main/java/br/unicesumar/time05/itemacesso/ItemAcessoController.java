package br.unicesumar.time05.itemacesso;

import classesbase.ControllerBase;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/itemacesso")
public class ItemAcessoController extends ControllerBase<ItemAcesso, Long, ItemAcessoService>{
    
    @Override
    public List<Map<String,Object>> getEntidadesListagem(){
        return service.getItensAcesso();
    }
    
    @RequestMapping(value = "/retornaItensPorSuperior/{aSuperiorId}",method = RequestMethod.GET)
    public List<Map<String, Object>> getItensAcessoPorSuperior(@PathVariable Long aSuperiorId ){
        return service.getItensAcessoPorSuperior(aSuperiorId);
    }
    
}
