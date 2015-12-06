package br.unicesumar.time05.indigena;

import br.unicesumar.time05.relatorios.formatoRelatorio;
import classesbase.ControllerBase;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/indigena")
public class IndigenaController extends ControllerBase<CriarIndigena, Long, IndigenaService> {

    @Override
    public Indigena getObjeto(@PathVariable Long aId) {
        Indigena i = (Indigena) service.getObjeto(aId);
        if (new File("src/main/webapp/fotos/indios/" + i.getCodigoAssindi() + ".jpg").exists()) {
            i.setImgSrc("/fotos/indios/" + i.getCodigoAssindi() + ".jpg");
        } else {
            i.setImgSrc("/fotos/default.png");
        }
        return i;
    }

    @RequestMapping(value = "/relatorio/{formatoRelatorio}", method = RequestMethod.POST)
    public Map<String, String> getRelatorio(@PathVariable formatoRelatorio formatoRelatorio, @RequestBody ParametrosRelatorioIndigena parametros) {
        return service.gerarRelatorio(formatoRelatorio, parametros);
    }

}
