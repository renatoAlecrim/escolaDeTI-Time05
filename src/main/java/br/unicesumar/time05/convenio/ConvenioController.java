package br.unicesumar.time05.convenio;

import classesbase.ControllerBase;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/convenio")
public class ConvenioController extends ControllerBase<Convenio, Long, ConvenioService> {

}
