package br.unicesumar.time05.uf;

import br.unicesumar.time05.consultapersonalizada.CampoConsulta;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UF {
    
    @CampoConsulta
    @Id
    private Long codigoestado;
    
    @CampoConsulta(campoOrdenacaoPadrao = true)
    private String descricao;
    
    @CampoConsulta
    private String sigla;

    public UF() {
    }

    public Long getCodigoestado() {
        return codigoestado;
    }

    public void setCodigoestado(Long codigoestado) {
        this.codigoestado = codigoestado;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public UF(Long codigoestado, String descricao, String sigla) {
        this.codigoestado = codigoestado;
        this.descricao = descricao;
        this.sigla = sigla;
    }

    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.sigla);
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
        final UF other = (UF) obj;
        if (!Objects.equals(this.sigla, other.sigla)) {
            return false;
        }
        return true;
    }
    
}
