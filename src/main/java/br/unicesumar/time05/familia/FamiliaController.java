package br.unicesumar.time05.familia;

import classesbase.ControllerBase;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/familia")
public class FamiliaController extends ControllerBase<Familia, Long, FamiliaService>{
    
    @RequestMapping(value = "/quantidadeintegrantes/{aIdFamilia}", method = RequestMethod.GET)
    public Map<String, Object> getQuantidadeIntegrantes(@PathVariable Long aIdFamilia) {
        return service.getQuantidadeIntegrantesFamilia(aIdFamilia);
    }
    
    @RequestMapping(value = "/membrosPorFamilia/{aIdFamilia}")
    public List<Map<String, Object>> getMembros(@PathVariable Long aIdFamilia){
        return service.getMembros(aIdFamilia);
    }
    
    @RequestMapping(value = "/familiasporindigena/{aCodigoAssindi}")
    public List<Map<String, Object>> getFamiliasPorIndigena(@PathVariable Long aCodigoAssindi){
        return service.getFamiliasPorIndigena(aCodigoAssindi);
    }
    
    @RequestMapping(value = "/indios")
    public List<Map<String, Object>> getIndios(){
        return service.getIndios();
    }
    
    @RequestMapping(value = "/relatorio", method = RequestMethod.GET)
    public Map getRelatorio(){
        return service.gerarRelatorioSimples();
    }
}
