package br.unicesumar.time05.estadiafamilia;

import br.unicesumar.time05.familia.Familia;
import br.unicesumar.time05.indigena.Indigena;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;
import java.util.Set;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Estadia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idestadia;
    private Date dataentrada;
    private Date datasaida;
    private String observacoesentrada;
    private String observacoessaida;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idfamilia")
    private Familia familia;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "estadia_indigena",
            joinColumns = {
                @JoinColumn(name = "idestadia", referencedColumnName = "idestadia")},
            inverseJoinColumns = {
                @JoinColumn(name = "codigoassindi", referencedColumnName = "codigoassindi")})
    private Set<Indigena> membros;

    @JoinColumn(name = "idrepresentante")
    @ManyToOne(fetch = FetchType.EAGER)
    private Indigena representante;

    public Estadia() {
    }

    public Estadia(Date dataentrada, Date datasaida, String observacoesentrada, String observacoessaida, Familia familia, Set<Indigena> membros, Indigena representante) {
        this.dataentrada = dataentrada;
        this.datasaida = datasaida;
        this.observacoesentrada = observacoesentrada;
        this.observacoessaida = observacoessaida;
        this.familia = familia;
        this.membros = membros;
        this.representante = representante;
    }

    public Long getIdestadia() {
        return idestadia;
    }

    public Date getDataentrada() {
        return dataentrada;
    }

    public Date getDatasaida() {
        return datasaida;
    }

    public String getObservacoesentrada() {
        return observacoesentrada;
    }

    public String getObservacoessaida() {
        return observacoessaida;
    }

    public Familia getFamilia() {
        return familia;
    }

    public Set<Indigena> getMembros() {
        return membros;
    }

    public void setDataentrada(Date dataentrada) {
        this.dataentrada = dataentrada;
    }

    public void setDatasaida(Date datasaida) {
        if (datasaida != null) {
            if (this.dataentrada.before(datasaida)) {
                this.datasaida = datasaida;
            } else {
                throw new RuntimeException("Data de saida não pode ser anterior à date de entrada.");
            }
        }
    }

    public void setObservacoesentrada(String observacoesentrada) {
        this.observacoesentrada = observacoesentrada;
    }

    public void setObservacoessaida(String observacoessaida) {
        this.observacoessaida = observacoessaida;
    }

    public void setFamilia(Familia familia) {
        this.familia = familia;
    }

    public void setMembros(Set<Indigena> membros) {
        this.membros = membros;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.idestadia);
        return hash;
    }

    public Indigena getRepresentante() {
        return representante;
    }

    public void setRepresentante(Indigena representante) {
        this.representante = representante;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Estadia other = (Estadia) obj;
        if (!Objects.equals(this.idestadia, other.idestadia)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Estadia{" + "idestadia=" + idestadia + ", dataentrada=" + dataentrada + ", datasaida=" + datasaida + ", observacoesentrada=" + observacoesentrada + ", observacoessaida=" + observacoessaida + ", familia=" + familia + ", membros=" + membros + ", representante=" + representante + '}';
    }

}
