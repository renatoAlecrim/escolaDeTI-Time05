package br.unicesumar.time05.visita;

import java.util.Set;

class ParametrosRelatorioVisita {
    
    private String dataini;
    private String datafim;
    private Set<Long> entidades;
    private Set<Long> responsaveis;
    private String visitarealizada;

    public ParametrosRelatorioVisita() {
    }

    public ParametrosRelatorioVisita(String dataini, String datafim, Set<Long> entidades, Set<Long> responsaveis, String visitarealizada) {
        this.dataini = dataini;
        this.datafim = datafim;
        this.entidades = entidades;
        this.responsaveis = responsaveis;
        this.visitarealizada = visitarealizada;
    }

    public String getDataini() {
        return dataini;
    }

    public void setDataini(String dataini) {
        this.dataini = dataini;
    }

    public String getDatafim() {
        return datafim;
    }

    public void setDatafim(String datafim) {
        this.datafim = datafim;
    }

    public Set<Long> getEntidades() {
        return entidades;
    }

    public void setEntidades(Set<Long> entidades) {
        this.entidades = entidades;
    }

    public Set<Long> getResponsaveis() {
        return responsaveis;
    }

    public void setResponsaveis(Set<Long> responsaveis) {
        this.responsaveis = responsaveis;
    }

    public String getVisitarealizada() {
        return visitarealizada;
    }

    public void setVisitarealizada(String visitarealizada) {
        this.visitarealizada = visitarealizada;
    }
        
}
