package br.unicesumar.time05.usuario;

import java.util.regex.Matcher;
import javax.persistence.Embeddable;

@Embeddable
public class Senha {
    
    private String senha;

    public Senha() {
    }

    public Senha(String senha) {
        this.senha = senha;
    }
    
    public String getSenha(){
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public boolean senhaValida(){
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%.]).{6,10})");
        Matcher matcher = pattern.matcher(this.senha);
        boolean valido = matcher.matches();
        return valido;
    }
}
