package br.unicesumar.time05.consultapersonalizada;

import java.io.Serializable;

public class ParametrosConsulta implements Serializable {

    private final int pagina;
    private final int registrosPorPagina;
    private final String ordenarPor;
    private final String sentidoOrdenacao;
    private String palavraChave;

    public ParametrosConsulta() {
        this.registrosPorPagina = 0;
        this.pagina = 0;
        this.ordenarPor = "";
        this.sentidoOrdenacao = "";
        this.palavraChave = "";
    }

    public ParametrosConsulta(int registrosPorPagina, int pagina) {
        this.pagina = pagina;
        this.registrosPorPagina = registrosPorPagina;
        this.ordenarPor = "";
        this.sentidoOrdenacao = "";
        this.palavraChave = "";
    }

    public ParametrosConsulta(int aRegistrosPorPagina, int aPagina, String aOrdenarPor, String aSentidoOrdenacao, String aPalavraChave) {
        this.registrosPorPagina = aRegistrosPorPagina;
        this.pagina = aPagina;
        this.ordenarPor = aOrdenarPor;
        this.sentidoOrdenacao = aSentidoOrdenacao;
        this.palavraChave = aPalavraChave;
    }

    public ParametrosConsulta(int aRegistrosPorPagina, int aPagina, String aOrdenarPor, String aSentidoOrdenacao) {
        this.registrosPorPagina = aRegistrosPorPagina;
        this.pagina = aPagina;
        this.ordenarPor = aOrdenarPor;
        this.sentidoOrdenacao = aSentidoOrdenacao;
        this.palavraChave = "";
    }

    public int getRegistrosPorPagina() {
        return registrosPorPagina;
    }

    public int getPagina() {
        return pagina;
    }

    public String getOrdenarPor() {
        return ordenarPor;
    }

    public String getSentidoOrdenacao() {
        return sentidoOrdenacao;
    }

    public String getPalavraChave() {
        return palavraChave;
    }

    public void setPalavraChave(String palavraChave) {
        this.palavraChave = palavraChave;
    }
    
}
