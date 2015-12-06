package br.unicesumar.time05.pessoafisica;

import br.unicesumar.time05.cpf.CPF;
import br.unicesumar.time05.email.Email;
import br.unicesumar.time05.relatorios.formatoRelatorio;
import classesbase.ControllerBase;
import java.io.File;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/pessoa/fisica")
public class FisicaController extends ControllerBase<CriarPessoaFisica, Long, FisicaService> {

    @Override
    public Object getObjeto(@PathVariable Long aId) {
                PessoaFisica p = (PessoaFisica) service.getObjeto(aId);
        if (new File("src/main/webapp/fotos/users/" + p.getIdpessoa()+ ".jpg").exists()) {
            p.setImgSrc("/fotos/users/" + p.getIdpessoa()+ ".jpg");
        }else{
            p.setImgSrc("/fotos/default.png");
        }
        return p;
    }
    
    
    
    @RequestMapping(value = "/getFisicas", method = RequestMethod.GET)
    public List<Map<String, Object>> getPessoas() {
        return service.listarFisicas();
    }

    @RequestMapping(value = "/verificarEmail/{aEmail:.+}/{aUsuarioId}", method = RequestMethod.GET)
    public boolean verifcarEmail(@PathVariable String aEmail, @PathVariable Long aUsuarioId) {
        if (aUsuarioId == -1)
            return service.verificarEmail(new Email(aEmail));
        else
            return service.verificarEmail(new Email(aEmail), aUsuarioId);
    }

    @RequestMapping(value = "/trocarTipo/{aPessoaId}", method = RequestMethod.POST)
    public void alterarTipoPessoa(@PathVariable Long aPessoaId, @RequestBody String tipo) {
        service.trocarTipoPessoa(aPessoaId, tipo);
    }

    @RequestMapping(value = "/verificarCpf/{aCpf:.+}/{aUsuarioId}", method = RequestMethod.GET)
    public Map<String, String> verifcarCpf(@PathVariable CPF aCpf, @PathVariable Long aUsuarioId) {
//        Map<String, String> map = new HashMap<>();
//        map.put("retorno", "valido");
//        return map;
        if (aUsuarioId != -1) {
            return service.verificarCpf(aCpf, aUsuarioId);
        } else {
            return service.verificarCpf(aCpf);
        }
    }
    
    @RequestMapping(value = "/relatorio/{formatoRelatorio}", method = RequestMethod.POST)
    public Map<String, String> getRelatorio(@PathVariable formatoRelatorio formatoRelatorio, @RequestBody ParametrosRelatorioColaborador parametros) {
        return service.gerarRelatorio(formatoRelatorio, parametros);
    }       
}
