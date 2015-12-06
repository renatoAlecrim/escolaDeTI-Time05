package br.unicesumar.time05.estadiafamilia;

import java.sql.Date;

public class SaidaEstadia {
    private Long idestadia;
    private Date datasaida;
    private String observacoessaida;

    public SaidaEstadia() {
    }

    public SaidaEstadia(Long idestadia, Date  datasaida, String observacoessaida) {
        this.idestadia = idestadia;
        this.datasaida = datasaida;
        this.observacoessaida = observacoessaida;
    }

    public Long getIdestadia() {
        return idestadia;
    }

    public void setIdestadia(Long idestadia) {
        this.idestadia = idestadia;
    }

    public Date getDatasaida() {
        return datasaida;
    }

    public void setDatasaida(Date  datasaida) {
        this.datasaida = datasaida;
    }

    public String getObservacoessaida() {
        return observacoessaida;
    }

    public void setObservacoessaida(String observacoessaida) {
        this.observacoessaida = observacoessaida;
    }
    
}
