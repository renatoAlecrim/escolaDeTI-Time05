package br.unicesumar.time05.upload;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/foto")
public class UploadController {
    
    @Autowired
    UploadService service;
    
    @RequestMapping(value = "/user/{idUsuario}", method = RequestMethod.GET)
    public Map<String, String> getUrlFotoUsuario(@PathVariable Long idUsuario){
        return service.getUrlFoto(idUsuario, "users");
    }
    
    @RequestMapping(value = "/indio/{idIndio}", method = RequestMethod.GET)
    public Map<String, String> getUrlFotoIndio(@PathVariable Long idIndio){
        return service.getUrlFoto(idIndio, "indios");
    }
}
