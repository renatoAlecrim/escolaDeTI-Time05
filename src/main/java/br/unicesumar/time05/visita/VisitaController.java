package br.unicesumar.time05.visita;

import br.unicesumar.time05.relatorios.formatoRelatorio;
import classesbase.ControllerBase;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/visita")
public class VisitaController extends ControllerBase<Visita, Long, VisitaService>{
    
    @RequestMapping(value = "/relatorio/{formatoRelatorio}", method = RequestMethod.POST)
    public Map<String, String> getRelatorio(@PathVariable formatoRelatorio formatoRelatorio, @RequestBody ParametrosRelatorioVisita parametros) {
        return service.gerarRelatorio(formatoRelatorio, parametros);
    }

}