package br.unicesumar.time05.convenio;

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
public class Convenio implements Serializable {

    @CampoConsulta
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long idconvenio;
    
    @CampoConsulta(campoOrdenacaoPadrao = true)
    @NotBlank(message = "Campo descrição não pode ser vazio!")
    @Column(unique = true, nullable = false)        
    String descricao;

    public Convenio() {
    }

    public Convenio(String descricao) {
        this.descricao = descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getIdconvenio() {
        return idconvenio;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return "Convenio{" + "idConvenio=" + idconvenio + ", descricao=" + descricao + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.idconvenio);
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
        final Convenio other = (Convenio) obj;
        if (!Objects.equals(this.idconvenio, other.idconvenio)) {
            return false;
        }
        return true;
    }
}