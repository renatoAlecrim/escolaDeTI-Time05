package br.unicesumar.time05.ocorrencia;


import classesbase.ControllerBase;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/ocorrencia")
public class OcorrenciaController extends ControllerBase<Ocorrencia, Long, OcorrenciaService>{
    @RequestMapping(value = "/ocorrencias/{idIndigena}", method = RequestMethod.GET)
    public List<Map<String, Object>> getOcorrencias(@PathVariable Long idIndigena){
        return service.listarSemPaginacao(idIndigena);
    }
    
    @RequestMapping(value = "/{aId}/{aIdIndigena}", method = RequestMethod.DELETE)
    public void removerOcorrencia(@PathVariable Long aId, @PathVariable Long aIdIndigena){
        service.remover(aId, aIdIndigena);
    }
}
