package br.unicesumar.time05.email;

public class EnviaEmail {
    //email contatoassindi@gmail.com
    //senha assindi123
    private String endereco;
    private String titulo;
    private String conteudo;

    public EnviaEmail() {
    }

    public EnviaEmail(String endereco, String titulo, String conteudo) {
        this.endereco = endereco;
        this.titulo = titulo;
        this.conteudo = conteudo;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

}
