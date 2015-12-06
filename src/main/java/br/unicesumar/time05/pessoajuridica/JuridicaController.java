package br.unicesumar.time05.pessoajuridica;

import br.unicesumar.time05.cnpj.Cnpj;
import br.unicesumar.time05.email.Email;
import classesbase.ControllerBase;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/pessoa/juridica")
public class JuridicaController extends ControllerBase<CriarPessoaJuridica, Long, JuridicaService> {

    @Override
    public Object getObjeto(@PathVariable Long aId) {
        return (PessoaJuridica)service.getObjeto(aId);
    }

    @RequestMapping(value = "/verificarEmail/{aEmail:.+}/{aUsuarioId}", method = RequestMethod.GET)
    public boolean verifcarEmail(@PathVariable String aEmail, @PathVariable Long aUsuarioId) {
        if (aUsuarioId == -1) {
            return service.verificarEmail(new Email(aEmail));
        } else {
            return service.verificarEmail(new Email(aEmail), aUsuarioId);
        }
    }

    @RequestMapping(value = "/verificarCnpj/{aCnpj:.+}/{aUsuarioId}", method = RequestMethod.GET)
    public Map<String, String> verifcarCnpj(@PathVariable Cnpj aCnpj, @PathVariable Long aUsuarioId) {
//        Map<String, String> map = new HashMap<>();
//        map.put("retorno", "valido");
//        return map;
        if (aUsuarioId != -1) {
            return service.verificarCnpj(aCnpj, aUsuarioId);
        } else {
            return service.verificarCnpj(aCnpj);
        }
    }
}
