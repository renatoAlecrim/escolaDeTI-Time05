package br.unicesumar.time05.perfildeacesso;

import br.unicesumar.time05.itemacesso.ItemAcesso;
import java.util.List;

public class PerfilBuilder {
    private Long idperfil;
    private String nome;
    private List<ItemAcesso> itens;

    public PerfilBuilder() {
    }

    public PerfilBuilder(String nome, List<ItemAcesso> itens) {
        this.nome = nome;
        this.itens = itens;
    }

    public PerfilBuilder(Long idperfil, String nome, List<ItemAcesso> itens) {
        this.idperfil = idperfil;
        this.nome = nome;
        this.itens = itens;
    }
    public Long getIdperfil() {
        return idperfil; 
   }

    public void setIdperfil(Long idperfil) {
        this.idperfil = idperfil;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<ItemAcesso> getItens() {
        return itens;
    }

    public void setItens(List<ItemAcesso> itens) {
        this.itens = itens;
    }
    
}
