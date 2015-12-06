package br.unicesumar.time05.ocorrencia;

import br.unicesumar.time05.consultapersonalizada.CampoConsulta;
import br.unicesumar.time05.consultapersonalizada.TipoComparacao;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity(name = "ocorrencia")
public class Ocorrencia implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idocorrencia;
    
    @CampoConsulta(tipoComparacao = TipoComparacao.IGUAL)
    private Date dataocorrencia;    
    
    @CampoConsulta(tipoComparacao = TipoComparacao.IGUAL)
    private Date databloqueio;    
    
    @CampoConsulta(tipoComparacao = TipoComparacao.CONTEM)
    private String descricao;
    
    @Transient
    private Long idIndigena;

    public Ocorrencia() {
    }    
    
    public Ocorrencia(Date dataocorrencia, Date databloqueio, String descricao) {
        this.dataocorrencia = dataocorrencia;
        this.databloqueio = databloqueio;
        this.descricao = descricao;
    }

    public Long getIdocorrencia() {
        return idocorrencia;
    }
    
    public Date getDataocorrencia() {
        return dataocorrencia;
    }

    public void setDataocorrencia(Date dataocorrencia) {
        this.dataocorrencia = dataocorrencia;
    }

    public Date getDatabloqueio() {
        return databloqueio;
    }

    public void setDatabloqueio(Date databloqueio) {
        this.databloqueio = databloqueio;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getIdIndigena() {
        return idIndigena;
    }

    public void setIdIndigena(Long idIndigena) {
        this.idIndigena = idIndigena;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.idocorrencia);
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
        final Ocorrencia other = (Ocorrencia) obj;
        if (!Objects.equals(this.idocorrencia, other.idocorrencia)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Ocorrencia{" + "idocorrencia=" + idocorrencia + ", dataocorrencia=" + dataocorrencia + ", databloqueio=" + databloqueio + ", descricao=" + descricao + '}';
    }

}
