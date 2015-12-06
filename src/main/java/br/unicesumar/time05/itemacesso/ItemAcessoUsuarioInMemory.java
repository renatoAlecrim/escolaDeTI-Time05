package br.unicesumar.time05.itemacesso;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class ItemAcessoUsuarioInMemory implements Serializable {

    private Long iditemacesso;
    private String nome;
    private String rota;
    private String icone;
    private List<ItemAcessoUsuarioInMemory> itens;

    public ItemAcessoUsuarioInMemory(Long iditemacesso, String nome, String rota, String icone, List<ItemAcessoUsuarioInMemory> itens) {
        this.iditemacesso = iditemacesso;
        this.nome = nome;
        this.rota = rota;
        this.icone = icone;
        this.itens = itens;
    }

    public void setItens(List<ItemAcessoUsuarioInMemory> itens) {
        this.itens = itens;
    }

    public ItemAcessoUsuarioInMemory() {
    }

    public Long getiditemacesso() {
        return iditemacesso;
    }

    public String getNome() {
        return nome;
    }

    public String getRota() {
        return rota;
    }

    public List<ItemAcessoUsuarioInMemory> getItens() {
        return itens;
    }

    @Override
    public String toString() {
        return "ItemAcessoUsuarioInMemory{" + "iditemacesso=" + iditemacesso + ", nome=" + nome + ", rota=" + rota + ", itens=" + itens + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.iditemacesso);
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
        final ItemAcessoUsuarioInMemory other = (ItemAcessoUsuarioInMemory) obj;
        if (!Objects.equals(this.iditemacesso, other.iditemacesso)) {
            return false;
        }
        return true;
    }

    public String getIcone() {
        return icone;
    }
}
