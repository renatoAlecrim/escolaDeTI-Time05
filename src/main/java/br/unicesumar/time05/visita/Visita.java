package br.unicesumar.time05.visita;

import br.unicesumar.time05.consultapersonalizada.CampoConsulta;
import br.unicesumar.time05.pessoafisica.PessoaFisica;
import br.unicesumar.time05.pessoajuridica.PessoaJuridica;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "visita")
public class Visita implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @CampoConsulta
    private Long idvisita;
    @Column(nullable = false, name = "datavisita")
    @CampoConsulta
    private Date datavisita;
    @Column(nullable = false, name = "horavisita")
    @CampoConsulta
    private Time horavisita;
    @Column(nullable = false, name = "quantidadedevisitantes")
    @CampoConsulta
    private Integer quantidadedevisitantes;
    @CampoConsulta
    private String seriecurso;
    @CampoConsulta
    private Time horaentrada;
    @CampoConsulta
    private Time horasaida;
    @CampoConsulta
    private String observacao;
    @CampoConsulta
    private boolean visitarealizada;
    @CampoConsulta
    private String telefonevisita;
    
    
    @CampoConsulta
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idpessoaresponsavel")
    private PessoaFisica pessoaresponsavel;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "identidade")
    private PessoaJuridica entidade;

    public Visita(Long idvisita, Date datavisita, Time horavisita, Integer quantidadedevisitantes, String seriecurso, Time horaentrada, Time horasaida, String observacao, boolean visitarealizada, String telefonevisita, PessoaFisica pessoaresponsavel, PessoaJuridica entidade) {
        this.idvisita = idvisita;
        this.datavisita = datavisita;
        this.horavisita = horavisita;
        this.quantidadedevisitantes = quantidadedevisitantes;
        this.seriecurso = seriecurso;
        this.horaentrada = horaentrada;
        this.horasaida = horasaida;
        this.observacao = observacao;
        this.visitarealizada = visitarealizada;
        this.telefonevisita = telefonevisita;
        this.pessoaresponsavel = pessoaresponsavel;
        this.entidade = entidade;
    }
    
    public Visita() {

    }

    public Long getIdvisita() {
        return idvisita;
    }

    public PessoaJuridica getEntidade() {
        return entidade;
    }

    public void setEntidade(PessoaJuridica entidade) {
        this.entidade = entidade;
    }
    
    public Integer getQuantidadedevisitantes() {
        return quantidadedevisitantes;
    }

    public void setQuantidadedevisitantes(Integer quantidadedevisitantes) {
        this.quantidadedevisitantes = quantidadedevisitantes;
    }

    public String getSeriecurso() {
        return seriecurso;
    }

    public void setSeriecurso(String seriecurso) {
        this.seriecurso = seriecurso;
    }

    public Time getHoraentrada() {
        return horaentrada;
    }

    public void setHoraentrada(Time horaentrada) {
        this.horaentrada = horaentrada;
    }

    public Time getHorasaida() {
        return horasaida;
    }

    public void setHorasaida(Time horasaida) {
        this.horasaida = horasaida;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getTelefonevisita() {
        return telefonevisita;
    }

    public void setTelefonevisita(String telefonevisita) {
        this.telefonevisita = telefonevisita;
    }
  
    public PessoaFisica getPessoaresponsavel() {
        return pessoaresponsavel;
    }

    public void setPessoaresponsavel(PessoaFisica pessoaResponsavel) {
        this.pessoaresponsavel = pessoaResponsavel;
    }

    public boolean isVisitarealizada() {
        return visitarealizada;
    }

    public void setVisitarealizada(boolean visitarealizada) {
        this.visitarealizada = visitarealizada;
    }

    public Date getDatavisita() {
        return datavisita;
    }

    public void setDatavisita(Date datavisita) {
        this.datavisita = datavisita;
    }

    public Time getHoravisita() {
        return horavisita;
    }

    public void setHoravisita(Time horavisita) {
        this.horavisita = horavisita;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.idvisita);
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
        final Visita other = (Visita) obj;
        if (!Objects.equals(this.idvisita, other.idvisita)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Visita{" + "idvisita=" + idvisita + ", datavisita=" + datavisita + ", horavisita=" + horavisita + ", quantidadedevisitantes=" + quantidadedevisitantes + ", seriecurso=" + seriecurso + ", horaentrada=" + horaentrada + ", horasaida=" + horasaida + ", observacao=" + observacao + ", visitarealizada=" + visitarealizada + ", telefonevisita=" + telefonevisita + ", pessoaresponsavel=" + pessoaresponsavel + ", entidade=" + entidade + '}';
    }
}
