package br.unicesumar.time05.terraindigena;

import br.unicesumar.time05.consultapersonalizada.CampoConsulta;
import br.unicesumar.time05.cidade.Cidade;
import br.unicesumar.time05.uf.UF;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity(name = "terraindigena")
public class TerraIndigena implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @CampoConsulta
    private Long idterraindigena;
    @CampoConsulta(campoOrdenacaoPadrao = true)
    @Column(unique = true, nullable = false)
    private String nometerra;
    @ManyToOne
    private Cidade cidade;

    public TerraIndigena() {
    }

    public TerraIndigena(String nometerra, UF estado, Cidade cidade) {
        this.nometerra = nometerra;
        this.cidade = cidade;
    }

    public Long getIdterraindigena() {
        return idterraindigena;
    }

    public String getNometerra() {
        return nometerra;
    }

    public void setNometerra(String nometerra) {
        this.nometerra = nometerra;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.idterraindigena);
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
        final TerraIndigena other = (TerraIndigena) obj;
        if (!Objects.equals(this.idterraindigena, other.idterraindigena)) {
            return false;
        }
        return true;
    }

}
