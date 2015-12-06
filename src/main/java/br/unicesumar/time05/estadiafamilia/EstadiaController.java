package br.unicesumar.time05.estadiafamilia;

import br.unicesumar.time05.relatorios.formatoRelatorio;
import br.unicesumar.time05.relatorios.relEstadiaBase;
import classesbase.ControllerBase;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/estadia")
public class EstadiaController extends ControllerBase<Estadia, Long, EstadiaService> {

    @RequestMapping(value = "/getMembros/{aIdEstadia}", method = RequestMethod.GET)
    public List<Map<String, Object>> getMembros(@PathVariable Long aIdEstadia) {
        return service.getMembros(aIdEstadia);
    }

    @RequestMapping(value = "/getRepresentante/{aIdRepresentante}", method = RequestMethod.GET)
    public Map<String, Object> getRepresentante(@PathVariable Long aIdRepresentante) {
        return service.getRepresentante(aIdRepresentante);
    }

    @RequestMapping(value = "/saida", method = RequestMethod.POST)
    public void addDataSaida(@RequestBody SaidaEstadia saida) {
        service.addDataSaida(saida);
    }

    @RequestMapping(value = "/relatorio/{formatoRelatorio}", method = RequestMethod.POST)
    public Map<String, String> getRelatorio(@PathVariable formatoRelatorio formatoRelatorio, @RequestBody ParametrosRelatorioEstadia parametros) {
        return service.gerarRelatorio(formatoRelatorio, parametros);
    }        
}
