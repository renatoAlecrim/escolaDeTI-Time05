package br.unicesumar.time05.endereco;

import br.unicesumar.time05.consultapersonalizada.CampoConsulta;
import br.unicesumar.time05.cidade.Cidade;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;

@Entity
public class Endereco implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @CampoConsulta
    private Long idendereco;
    @CampoConsulta
    private String logradouro;
    @CampoConsulta
    private String numero;
    @CampoConsulta
    private String bairro;
    @CampoConsulta
    private String complemento;
    @CampoConsulta
    private String cep;
    @CampoConsulta
    @ManyToOne()
    @JoinTable(name = "endereco_cidade",
            joinColumns = {
                @JoinColumn(name = "endereco_id", referencedColumnName = "idendereco")},
            inverseJoinColumns = {
                @JoinColumn(name = "cidade_id", referencedColumnName = "codigoIBGE")})
    
    private Cidade cidade;

//    @ManyToOne()
//    @JoinTable(name = "endereco_estado",
//            joinColumns = {
//                @JoinColumn(name = "endereco_id", referencedColumnName = "idendereco")},
//            inverseJoinColumns = {
//                @JoinColumn(name = "estado_id", referencedColumnName = "sigla")})
//    private UF uf;

    public Endereco() {
    }

    public Endereco(String logradouro, String numero, String bairro, String cep, Cidade cidade) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cep = cep;
        this.cidade = cidade;
//        this.uf = uf;
    }

    public Endereco(String logradouro, String numero, String bairro, String complemento, String cep, Cidade cidade) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.complemento = complemento;
        this.cep = cep;
        this.cidade = cidade;
//        this.uf = uf;
    }

    public Long getIdendereco() {
        return idendereco;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

//    public UF getUf() {
//        return uf;
//    }
//
//    public void setUf(UF uf) {
//        this.uf = uf;
//    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.logradouro);
        hash = 97 * hash + Objects.hashCode(this.numero);
        hash = 97 * hash + Objects.hashCode(this.bairro);
        hash = 97 * hash + Objects.hashCode(this.complemento);
        hash = 97 * hash + Objects.hashCode(this.cep);
        hash = 97 * hash + Objects.hashCode(this.cidade);
//        hash = 97 * hash + Objects.hashCode(this.uf);
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
        final Endereco other = (Endereco) obj;
        if (!Objects.equals(this.logradouro, other.logradouro)) {
            return false;
        }
        if (!Objects.equals(this.numero, other.numero)) {
            return false;
        }
        if (!Objects.equals(this.bairro, other.bairro)) {
            return false;
        }
        if (!Objects.equals(this.complemento, other.complemento)) {
            return false;
        }
        if (!Objects.equals(this.cep, other.cep)) {
            return false;
        }
        if (!Objects.equals(this.cidade, other.cidade)) {
            return false;
        }
//        if (!Objects.equals(this.uf, other.uf)) {
//            return false;
//        }
        return true;
    }

    @Override
    public String toString() {
        return "Endereco{" + "logradouro=" + logradouro + ", numero=" + numero + ", bairro=" + bairro + ", complemento=" + complemento + ", cep=" + cep + ", cidade=" + cidade + '}';
    }

}
