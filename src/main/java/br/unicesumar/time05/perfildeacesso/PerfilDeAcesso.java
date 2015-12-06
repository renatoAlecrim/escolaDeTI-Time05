package br.unicesumar.time05.perfildeacesso;

import br.unicesumar.time05.consultapersonalizada.CampoConsulta;
import br.unicesumar.time05.itemacesso.ItemAcesso;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import org.hibernate.validator.constraints.NotBlank;

@Entity(name = "perfildeacesso")
public class PerfilDeAcesso implements Serializable {

    @CampoConsulta
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idperfildeacesso;
    @Column(nullable = false, unique = true)
    @NotBlank(message = "o nome não pode ser vazio!")
    @CampoConsulta(campoOrdenacaoPadrao = true)
    private String nome;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "perfildeacesso_itemacesso",
            joinColumns = {
                @JoinColumn(name = "perfildeacesso_id", referencedColumnName = "idperfildeacesso")},
            inverseJoinColumns = {
                @JoinColumn(name = "itemacesso_id", referencedColumnName = "iditemacesso")})
//    @CampoConsulta
    private Set<ItemAcesso> itens = new HashSet<>();

    public PerfilDeAcesso() {
    }

    public PerfilDeAcesso(String nome, Set<ItemAcesso> itens) {
        this.nome = nome;
        this.itens = itens;
    }

    public Long getIdperfildeacesso() {
        return idperfildeacesso;
    }

    public String getNome() {
        return nome;
    }

    public Set<ItemAcesso> getItens() {
        return itens;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setItens(Set<ItemAcesso> itens) {
        this.itens = itens;
    }

    public void addItens(Set<ItemAcesso> itens) {
        this.itens.addAll(itens);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.idperfildeacesso);
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
        final PerfilDeAcesso other = (PerfilDeAcesso) obj;
        if (!Objects.equals(this.idperfildeacesso, other.idperfildeacesso)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PerfilDeAcesso{" + "idperfildeacesso=" + idperfildeacesso + ", nome=" + nome + ", itens=" + itens + '}';
    }

}
