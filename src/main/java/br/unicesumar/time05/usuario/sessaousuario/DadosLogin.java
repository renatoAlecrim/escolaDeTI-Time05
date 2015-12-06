package br.unicesumar.time05.usuario.sessaousuario;

import java.io.Serializable;

public class DadosLogin implements Serializable{
    private String login;
    private String senha;

    public DadosLogin(){
        
    }
    
    public DadosLogin(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }
}
