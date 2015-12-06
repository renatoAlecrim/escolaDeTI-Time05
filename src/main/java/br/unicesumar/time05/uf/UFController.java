package br.unicesumar.time05.uf;

import classesbase.ControllerBase;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/uf")
public class UFController extends ControllerBase<UF, Long, UfService>{
    
}
