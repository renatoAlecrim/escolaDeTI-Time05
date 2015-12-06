package classesbase;

import br.unicesumar.time05.consultapersonalizada.ParametrosConsulta;
import br.unicesumar.time05.consultapersonalizada.RetornoConsultaPaginada;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class ControllerBase<Entidade extends Object, ID extends Serializable, Service extends ServiceBase> {

    @Autowired
    public Service service;

    @RequestMapping(method = RequestMethod.POST)
    public void salvar(@RequestBody Entidade aEntidade) {
        try {
            service.salvar(aEntidade);
        } catch (Exception e) {
            System.out.println(e);
            String mensagem;
            if(e.getMessage().contains("constraint")){
                mensagem = "Já existe um registro igual no sistema.";
            }else{
                mensagem = "Algo deu errado. Tente novamente.";
            }
            throw new RuntimeException(mensagem);
        }
    }

    @RequestMapping(value = "/{aEntidadeID}", method = RequestMethod.DELETE)
    public void remover(@PathVariable ID aEntidadeID) {
        service.remover(aEntidadeID);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void editar(@RequestBody Entidade aEntidade) {
        service.alterar(aEntidade);
    }

    @RequestMapping(value = "/{aId}", method = RequestMethod.GET)
    public Map<String, Object> getEntidadePorId(@PathVariable ID aId) {
        return (Map<String, Object>) service.findByID(aId).get(0);
    }

    @RequestMapping(value = "/obj/{aId}", method = RequestMethod.GET)
    public Object getObjeto(@PathVariable ID aId) {
        return service.getObjeto(aId);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Map<String, Object>> getEntidadesListagem() {
        return service.listarSemPaginacao();
    }

    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public RetornoConsultaPaginada getEntidadesPaginadaSimples() {
        return service.listar();
    }

    @RequestMapping(value = "/listar/{aRegistrosPorPagina}/{aPagina}", method = RequestMethod.GET)
    public RetornoConsultaPaginada getEntidadesPaginada(@PathVariable int aRegistrosPorPagina, @PathVariable int aPagina) {
        ParametrosConsulta parametros = new ParametrosConsulta(aRegistrosPorPagina, aPagina);
        return service.listar(parametros);
    }

    @RequestMapping(value = "/listar/{aRegistrosPorPagina}/{aPagina}/{aOrdenarPor}/{aSentidoOrdenacao}", method = RequestMethod.GET)
    public RetornoConsultaPaginada getEntidadesOrdenadas(@PathVariable int aRegistrosPorPagina, @PathVariable int aPagina, @PathVariable String aOrdenarPor, @PathVariable String aSentidoOrdenacao) {
        ParametrosConsulta parametros = new ParametrosConsulta(aRegistrosPorPagina, aPagina, aOrdenarPor, aSentidoOrdenacao);
        return service.listar(parametros);
    }

    @RequestMapping(value = "/listar/{aRegistrosPorPagina}/{aPagina}/{aOrdenarPor}/{aSentidoOrdenacao}/{aPalavraChave}", method = RequestMethod.GET)
    public RetornoConsultaPaginada getEntidadesOrdenadasEComBusca(@PathVariable int aRegistrosPorPagina, @PathVariable int aPagina, @PathVariable String aOrdenarPor, @PathVariable String aSentidoOrdenacao, @PathVariable String aPalavraChave) {
        ParametrosConsulta parametros = new ParametrosConsulta(aRegistrosPorPagina, aPagina, aOrdenarPor, aSentidoOrdenacao, aPalavraChave);
        return service.listar(parametros);
    }
}
