package br.unicesumar.time05.eventos;

import br.unicesumar.time05.consultapersonalizada.CampoConsulta;
import java.sql.Date;
import java.sql.Time;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Eventos {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @CampoConsulta
    private Long idevento;
    @CampoConsulta
    private String descricao;
    @CampoConsulta
    private Date datainicial;
    @CampoConsulta
    private Date datafinal;
    @CampoConsulta
    private Time horainicial;
    @CampoConsulta
    private Time horafinal;
    @CampoConsulta
    private Boolean visualizarnocalendario;

    public Time getHorainicial() {
        return horainicial;
    }

    public void setHorainicial(Time horainicial) {
        this.horainicial = horainicial;
    }

    public Time getHorafinal() {
        return horafinal;
    }

    public void setHorafinal(Time horafinal) {
        this.horafinal = horafinal;
    }

    public Eventos() {
    }

    public Eventos(String descricao, Date datainicial, Date datafinal, Boolean visualizarnocalendario) {
        this.descricao = descricao;
        this.datainicial = datainicial;
        this.datafinal = datafinal;
        this.visualizarnocalendario = visualizarnocalendario;
    }

    public Long getIdevento() {
        return idevento;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.idevento);
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
        final Eventos other = (Eventos) obj;
        if (!Objects.equals(this.idevento, other.idevento)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Eventos{" + "idevento=" + idevento + ", descricao=" + descricao + ", datainicial=" + datainicial + ", datafinal=" + datafinal + ", visualizarnocalendario=" + visualizarnocalendario + '}';
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDatainicial() {
        return datainicial;
    }

    public void setDatainicial(Date datainicial) {
        this.datainicial = datainicial;
    }

    public Date getDatafinal() {
        return datafinal;
    }

    public void setDatafinal(Date datafinal) {
        this.datafinal = datafinal;
    }

    public Boolean getVisualizarnocalendario() {
        return visualizarnocalendario;
    }

    public void setVisualizarnocalendario(Boolean visualizarnocalendario) {
        this.visualizarnocalendario = visualizarnocalendario;
    }
    
         
}
