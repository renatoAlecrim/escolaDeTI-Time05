package br.unicesumar.time05.usuario;

import br.unicesumar.time05.cpf.CPF;
import br.unicesumar.time05.email.Email;
import br.unicesumar.time05.endereco.Endereco;
import br.unicesumar.time05.genero.Genero;
import br.unicesumar.time05.perfildeacesso.PerfilDeAcesso;
import br.unicesumar.time05.pessoa.TipoPessoa;
import br.unicesumar.time05.pessoafisica.PessoaFisica;
import br.unicesumar.time05.telefone.Telefone;
import java.io.Serializable;
import java.util.Date;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long idusuario;

    private String nome;
    
    private String login;

    @Embedded
    private Senha senha;

    @Embedded
    private Email email;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ATIVO;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<PerfilDeAcesso> perfis = new HashSet<>();

    @OneToOne(optional = true)
    private PessoaFisica pessoa;
    
    @Transient
    private String imgSrc;

    public Usuario() {
    }

    public Usuario(String nome, String login, Senha senha, Email email, PessoaFisica pessoa) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.email = email;
        this.pessoa = pessoa;
    }

    public Usuario(String login, Senha senha, Email email, PessoaFisica pessoa) {
        this.login = login;
        this.senha = senha;
        this.email = email;
        this.pessoa = pessoa;
    }

    public Usuario(String login, Senha senha, Email email) {
        this.login = login;
        this.senha = senha;
        this.email = email;
    }

    public long getIdusuario() {
        return idusuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Senha getSenha() {
        return senha;
    }

    public void setSenha(Senha senha) {
        this.senha = senha;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Status getStatus() {
        return status;
    }

    public void trocaStatus() {
        if(this.status == Status.ATIVO)
            this.status = Status.INATIVO;
        else
            this.status = Status.ATIVO;
    }

    public Set<PerfilDeAcesso> getPerfis() {
        return perfis;
    }

    public void setPerfis(List<PerfilDeAcesso> perfis) {
        this.perfis = new HashSet<>(perfis);
    }
    
    public void removerPerfil(PerfilDeAcesso perfil){
        this.perfis.remove(perfil);
    }
    
    public void addPerfis(PerfilDeAcesso perfil) {
        this.perfis.add(perfil);
    }

    public PessoaFisica getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaFisica pessoa) {
        this.pessoa = pessoa;
    }

    public boolean verificaSenha(String senha) {
        return this.senha.equals(senha);
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }
    
}
