package br.unicesumar.time05.telefone;

import java.io.Serializable;
import javax.persistence.Embeddable;

@Embeddable
public class TelefoneEmbutido implements Serializable {
    
    private String telefone;

    public TelefoneEmbutido() {
    }

    public TelefoneEmbutido(String telefone) {
        this.telefone = telefone;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
