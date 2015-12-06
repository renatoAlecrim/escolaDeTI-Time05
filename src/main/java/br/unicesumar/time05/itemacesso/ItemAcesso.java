package br.unicesumar.time05.itemacesso;

import br.unicesumar.time05.consultapersonalizada.CampoConsulta;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "itemacesso")
public class ItemAcesso implements Serializable {
    @CampoConsulta
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long iditemacesso;
    @CampoConsulta(campoOrdenacaoPadrao = true)
    private String nome;
    @CampoConsulta
    private String rota;
    @CampoConsulta
    private String icone;
    @CampoConsulta
    @ManyToOne
    @JoinColumn(name = "superior_id")
    private ItemAcesso superior;

    public ItemAcesso() {
    }

    public ItemAcesso(String nome, String rota, String icone) {
        this.nome = nome;
        this.rota = rota;
        this.icone = icone;
    }
    
    public ItemAcesso(Long iditemacesso, String nome, String rota, String icone) {
        this.iditemacesso = iditemacesso;
        this.nome = nome;
        this.rota = rota;
        this.icone = icone;
    }
    
    public ItemAcesso(String nome, String rota, String icone, ItemAcesso superior) {
        this.nome = nome;
        this.rota = rota;
        this.icone = icone;
        this.superior = superior;
    }

    public ItemAcesso(Long iditemacesso, String nome, String rota, String icone, ItemAcesso superior) {
        this.iditemacesso = iditemacesso;
        this.nome = nome;
        this.rota = rota;
        this.icone = icone;
        this.superior = superior;
    }

    public Long getIditemacesso() {
        return iditemacesso;
    }

    public String getNome() {
        return nome;
    }

    public String getRota() {
        return rota;
    }

    public ItemAcesso getSuperior() {
        return superior;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setRota(String rota) {
        this.rota = rota;
    }

    public void setSuperior(ItemAcesso superior) {
        this.superior = superior;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.nome);
        hash = 97 * hash + Objects.hashCode(this.rota);
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
        final ItemAcesso other = (ItemAcesso) obj;
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.rota, other.rota)) {
            return false;
        }
        if (!Objects.equals(this.superior, other.superior)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ItemAcesso{" + "iditemacesso=" + iditemacesso + ", nome=" + nome + ", rota=" + rota + ", icone=" + icone + ", superior=" + superior + '}';
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public String getIcone() {
        return icone;
    }

}
