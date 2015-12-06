package br.unicesumar.time05.cidade;

import br.unicesumar.time05.consultapersonalizada.CampoConsulta;
import br.unicesumar.time05.uf.UF;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Cidade {
    @CampoConsulta
    @Id
    private Long codigoIBGE;
    
    @CampoConsulta(campoOrdenacaoPadrao = true)
    private String descricao;
    
    @CampoConsulta
    @ManyToOne
    private UF estado;

    public Cidade() {

    }

    public Cidade(Long codigoIBGE, String descricao, UF estado) {
        this.codigoIBGE = codigoIBGE;
        this.descricao = descricao;
        this.estado = estado;
    }

    public Long getCodigoIBGE() {
        return codigoIBGE;
    }

    public void setCodigoIBGE(Long codigoIBGE) {
        this.codigoIBGE = codigoIBGE;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public UF getEstado() {
        return estado;
    }

    public void setEstado(UF estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.codigoIBGE);
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
        final Cidade other = (Cidade) obj;
        if (!Objects.equals(this.codigoIBGE, other.codigoIBGE)) {
            return false;
        }
        return true;
    }

}
