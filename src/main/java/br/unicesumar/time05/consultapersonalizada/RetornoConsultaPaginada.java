package br.unicesumar.time05.consultapersonalizada;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class RetornoConsultaPaginada implements Serializable{

    private int quantidadeDePaginas;
    private int paginaAtual;

    private int totalDeRegistros;
    private List<Map<String, Object>> listaDeRegistros;

    public RetornoConsultaPaginada() {
    }

    public int getQuantidadeDePaginas() {
        return quantidadeDePaginas;
    }

    public int getPaginaAtual() {
        return paginaAtual;
    }

    public void setPaginaAtual(int aPaginaAtual) {
        this.paginaAtual = aPaginaAtual;
    }
    
    public void setQuantidadeDePaginas(int aPaginas) {
        this.quantidadeDePaginas = aPaginas;
    }

    public int getTotalDeRegistros() {
        return totalDeRegistros;
    }

    public void setTotalDeRegistros(int aTotalDeRegistros) {
        this.totalDeRegistros = aTotalDeRegistros;
    }

    public List<Map<String, Object>> getListaDeRegistros() {
        return listaDeRegistros;
    }

    public void setListaDeRegistros(List<Map<String, Object>> aListaDeRegistros) {
        this.listaDeRegistros = aListaDeRegistros;
    }    
}
