package br.unicesumar.time05.pessoajuridica;

import br.unicesumar.time05.cnpj.Cnpj;
import br.unicesumar.time05.email.Email;
import br.unicesumar.time05.pessoa.TipoPessoa;
import br.unicesumar.time05.telefone.Telefone;

public class CriarPessoaJuridica {
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
    private TipoPessoa tipo;
    private Cnpj cnpj;

    public CriarPessoaJuridica() {
    }

    public CriarPessoaJuridica(Long idpessoa, String nome, Telefone telefone, Telefone telefonesecundario, Email email, String logradouro, String numero, String bairro, String complemento, String cep, Long codigoibge, Long codigouf, TipoPessoa tipo, Cnpj cnpj) {
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
        this.codigoibge = codigoibge;
        this.tipo = tipo;
        this.cnpj = cnpj;
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

    public TipoPessoa getTipo() {
        return tipo;
    }

    public void setTipo(TipoPessoa tipo) {
        this.tipo = tipo;
    }

    public Cnpj getCnpj() {
        return cnpj;
    }

    public void setCnpj(Cnpj cnpj) {
        this.cnpj = cnpj;
    }
    
}
