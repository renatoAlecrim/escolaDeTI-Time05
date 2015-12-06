package br.unicesumar.time05.funcao;

import br.unicesumar.time05.consultapersonalizada.CampoConsulta;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Funcao implements Serializable {

    @CampoConsulta
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long idfuncao;
    
    @CampoConsulta(campoOrdenacaoPadrao = true)
    @NotBlank(message = "Campo descrição não pode estar vazio")
    @Column(unique = true, nullable = false)
    String descricao;

    public Funcao() {
    }

    public Funcao(String descricao) {
        this.descricao = descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getIdfuncao() {
        return idfuncao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return "Funcao{" + "idfuncao=" + idfuncao + ", descricao=" + descricao + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Objects.hashCode(this.idfuncao);
        return hash;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Funcao other = (Funcao) obj;
        if (!Objects.equals(this.idfuncao, other.idfuncao)) {
            return false;
        }
        return true;
    }
}