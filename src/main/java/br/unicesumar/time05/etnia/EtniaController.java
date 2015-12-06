package br.unicesumar.time05.etnia;

import classesbase.ControllerBase;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/etnia")
public class EtniaController extends ControllerBase<Etnia, Long, EtniaService>{

}
