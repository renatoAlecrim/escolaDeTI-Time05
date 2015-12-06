package br.unicesumar.time05.relatorios;

import java.sql.Date;
import java.util.List;

public class relEstadiaBase {

	private List<Boolean> generos;
        private Date dataini;
        private Date datafim;
        private List<Long> familias;
    	private List<Long> etnias;
        private List<Long> representantes;
        private List<Long> terrasindigena;

    public relEstadiaBase() {
    }

    public relEstadiaBase(List<Boolean> generos, Date dataini, Date datafim, List<Long> familias, List<Long> etnias, List<Long> representantes, List<Long> terrasindigena) {
        this.generos = generos;
        this.dataini = dataini;
        this.datafim = datafim;
        this.familias = familias;
        this.etnias = etnias;
        this.representantes = representantes;
        this.terrasindigena = terrasindigena;
    }

    public List<Boolean> getGeneros() {
        return generos;
    }

    public void setGeneros(List<Boolean> generos) {
        this.generos = generos;
    }

    public Date getDataini() {
        return dataini;
    }

    public void setDataini(Date dataini) {
        this.dataini = dataini;
    }

    public Date getDatafim() {
        return datafim;
    }

    public void setDatafim(Date datafim) {
        this.datafim = datafim;
    }

    public List<Long> getFamilias() {
        return familias;
    }

    public void setFamilias(List<Long> familias) {
        this.familias = familias;
    }

    public List<Long> getEtnias() {
        return etnias;
    }

    public void setEtnias(List<Long> etnias) {
        this.etnias = etnias;
    }

    public List<Long> getRepresentantes() {
        return representantes;
    }

    public void setRepresentantes(List<Long> representantes) {
        this.representantes = representantes;
    }

    public List<Long> getTerrasindigena() {
        return terrasindigena;
    }

    public void setTerrasindigena(List<Long> terrasindigena) {
        this.terrasindigena = terrasindigena;
    }
    
}
