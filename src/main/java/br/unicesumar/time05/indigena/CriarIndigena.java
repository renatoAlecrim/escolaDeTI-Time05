package br.unicesumar.time05.indigena;

import br.unicesumar.time05.convenio.Convenio;
import br.unicesumar.time05.cpf.CPF;
import br.unicesumar.time05.genero.Genero;
import br.unicesumar.time05.ocorrencia.Ocorrencia;
import br.unicesumar.time05.telefone.Telefone;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CriarIndigena implements Serializable {

    private Long codigoAssindi;
    private String nome;
    private CPF cpf;
    private Long etnia;
    private Genero genero;
    private Date dataNascimento;
    private Set<Convenio> convenio;
    private Telefone telefone;
    private Long terraIndigena;
    private Escolaridade escolaridade;
    private EstadoCivil estadoCivil;
    private Long codigoSUS;
    private String imgSrc;
    private List<Ocorrencia> ocorrencias = new ArrayList<Ocorrencia>();

    public CriarIndigena() {
    }

    public CriarIndigena(Long codigoAssindi, String nome, CPF cpf, Long etnia, Genero genero, Date dataNascimento, Set<Convenio> convenio, Telefone telefone, Long terraIndigena, Escolaridade escolaridade, EstadoCivil estadoCivil, Long codigoSUS, String imgSrc, List<Ocorrencia> ocorrencia) {
        this.codigoAssindi = codigoAssindi;
        this.nome = nome;
        this.cpf = cpf;
        this.etnia = etnia;
        this.genero = genero;
        this.dataNascimento = dataNascimento;
        this.convenio = convenio;
        this.telefone = telefone;
        this.terraIndigena = terraIndigena;
        this.escolaridade = escolaridade;
        this.estadoCivil = estadoCivil;
        this.codigoSUS = codigoSUS;
        this.imgSrc = imgSrc;
        this.ocorrencias = ocorrencia;
    }

    public Long getCodigoAssindi() {
        return codigoAssindi;
    }

    public void setCodigoAssindi(Long codigoAssindi) {
        this.codigoAssindi = codigoAssindi;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public CPF getCpf() {
        return cpf;
    }

    public void setCpf(CPF cpf) {
        this.cpf = cpf;
    }

    public Long getEtnia() {
        return etnia;
    }

    public void setEtnia(Long etnia) {
        this.etnia = etnia;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Set<Convenio> getConvenio() {
        return convenio;
    }

    public void setConvenio(Set<Convenio> convenio) {
        this.convenio = convenio;
    }

    public Telefone getTelefone() {
        return telefone;
    }

    public void setTelefone(Telefone telefone) {
        this.telefone = telefone;
    }

    public Long getTerraIndigena() {
        return terraIndigena;
    }

    public void setTerraIndigena(Long terraIndigena) {
        this.terraIndigena = terraIndigena;
    }

    public Escolaridade getEscolaridade() {
        return escolaridade;
    }

    public void setEscolaridade(Escolaridade escolaridade) {
        this.escolaridade = escolaridade;
    }

    public EstadoCivil getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(EstadoCivil estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public Long getCodigoSUS() {
        return codigoSUS;
    }

    public void setCodigoSUS(Long codigoSUS) {
        this.codigoSUS = codigoSUS;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public List<Ocorrencia> getOcorrencias() {
        return ocorrencias;
    }

    public void setOcorrencias(List<Ocorrencia> ocorrencias) {
        this.ocorrencias.addAll(ocorrencias);// = ocorrencias;
    }

}
