package br.unicesumar.time05.familia;

import br.unicesumar.time05.consultapersonalizada.CampoConsulta;
import br.unicesumar.time05.indigena.Indigena;
import br.unicesumar.time05.telefone.TelefoneEmbutido;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Familia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idfamilia;
    @CampoConsulta
    private String nomefamilia;
    @CampoConsulta
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idrepresentante")
    private Indigena representante;
    @Embedded
    private TelefoneEmbutido telefone;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "familia_indigena",
            joinColumns = {
                @JoinColumn(name = "idfamilia", referencedColumnName = "idfamilia")},
            inverseJoinColumns = {
                @JoinColumn(name = "codigoassindi", referencedColumnName = "codigoassindi")})
    private Set<Indigena> membros;

    public Familia() {
    }

    public Familia(String nomefamilia, Indigena representante, TelefoneEmbutido telefone, Set<Indigena> membros) {
        this.nomefamilia = nomefamilia;
        this.representante = representante;
        this.telefone = telefone;
        this.membros = membros;
    }

    public Long getIdfamilia() {
        return idfamilia;
    }

    public String getNomefamilia() {
        return nomefamilia;
    }

    public Indigena getRepresentante() {
        return representante;
    }

    public TelefoneEmbutido getTelefone() {
        return telefone;
    }

    public Set<Indigena> getMembros() {
        return membros;
    }

    public void setNomefamilia(String nomefamilia) {
        this.nomefamilia = nomefamilia;
    }

    public void setRepresentante(Indigena representante) {
        this.representante = representante;
    }

    public void setTelefone(TelefoneEmbutido telefone) {
        this.telefone = telefone;
    }

    public void setMembros(Set<Indigena> membros) {
        this.membros = membros;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + Objects.hashCode(this.idfamilia);
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
        final Familia other = (Familia) obj;
        if (!Objects.equals(this.idfamilia, other.idfamilia)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Familia{" + "idfamilia=" + idfamilia + ", nomefamilia=" + nomefamilia + ", representante=" + representante + ", telefone=" + telefone + ", membros=" + membros + '}';
    }
}
