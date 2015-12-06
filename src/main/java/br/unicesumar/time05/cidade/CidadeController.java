package br.unicesumar.time05.cidade;

import classesbase.ControllerBase;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/cidade")
public class CidadeController extends ControllerBase<Cidade, Long, CidadeService>{
    
    @RequestMapping(value = "/listarPorCodigoEstado/{aCodigoEstado}", method = RequestMethod.GET)
    public List<Map<String, Object>> getCidadesPorCodigoEstado(@PathVariable int aCodigoEstado) {
        return service.listarCidadesPorUF(aCodigoEstado);
    }
}
