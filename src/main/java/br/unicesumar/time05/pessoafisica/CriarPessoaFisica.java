package br.unicesumar.time05.pessoafisica;

import br.unicesumar.time05.usuario.*;
import br.unicesumar.time05.cpf.CPF;
import br.unicesumar.time05.email.Email;
import br.unicesumar.time05.genero.Genero;
import br.unicesumar.time05.pessoa.TipoPessoa;
import br.unicesumar.time05.telefone.Telefone;
import java.io.Serializable;
import java.sql.Date;

public class CriarPessoaFisica implements Serializable{

    private Long idpessoa;
    private String nome;
    private Telefone telefone;
    private Telefone telefonesecundario;
    private Email email;
    private String logradouro;
    private String numero;
    private String bairro;
    private String complemento;
    private String cep;
    private Long codigoibge;
    private Long codigouf;
    private Long idfuncao;
    private Date datanasc;
    private TipoPessoa tipo;
    private CPF cpf;
    private Genero genero;
    private String imgSrc;

    public CriarPessoaFisica() {
    }
    
    public CriarPessoaFisica(Long idpessoa, String nome, Telefone telefone, Telefone telefonesecundario, Email email, String logradouro, String numero, String bairro, String complemento, String cep, Long codigoIBGE, Long codigoUF, Long idfuncao, Date datanasc, TipoPessoa tipo, CPF cpf, Genero genero, String login, Senha senha, String imgSrc) {
        this.idpessoa = idpessoa;
        this.nome = nome;
        this.telefone = telefone;
        this.telefonesecundario = telefonesecundario;
        this.email = email;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.complemento = complemento;
        this.cep = cep;
        this.codigoibge = codigoIBGE;
//        this.codigoUF = codigoUF;
        this.idfuncao = idfuncao;
        this.datanasc = datanasc;
        this.tipo = tipo;
        this.cpf = cpf;
        this.genero = genero;
        this.imgSrc = imgSrc;
    }
    
    public Long getIdpessoa() {
        return idpessoa;
    }

    public void setIdpessoa(Long idpessoa) {
        this.idpessoa = idpessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Telefone getTelefone() {
        return telefone;
    }

    public void setTelefone(Telefone telefone) {
        this.telefone = telefone;
    }

    public Telefone getTelefonesecundario() {
        return telefonesecundario;
    }

    public void setTelefonesecundario(Telefone telefonesecundario) {
        this.telefonesecundario = telefonesecundario;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
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

    public Long getCodigoibge() {
        return codigoibge;
    }

    public void setCodigoibge(Long codigoibge) {
        this.codigoibge = codigoibge;
    }

    public Long getIdfuncao() {
        return idfuncao;
    }

    public void setIdfuncao(Long idfuncao) {
        this.idfuncao = idfuncao;
    }

    public Date getDatanasc() {
        return datanasc;
    }

    public void setDatanasc(Date datanasc) {
        this.datanasc = datanasc;
    }

    public TipoPessoa getTipoPessoa() {
        return tipo;
    }

    public void setTipoPessoa(TipoPessoa tipoPessoa) {
        this.tipo = tipoPessoa;
    }

    public CPF getCpf() {
        return cpf;
    }

    public void setCpf(CPF cpf) {
        this.cpf = cpf;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public TipoPessoa getTipo() {
        return tipo;
    }

    public void setTipo(TipoPessoa tipo) {
        this.tipo = tipo;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }
    
}
