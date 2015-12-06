package br.unicesumar.time05.estadiafamilia;

import java.util.Set;

public class ParametrosRelatorioEstadia {
    
    private Set<String> generos;
    private String dataini;
    private String datafim;
    private Set<Long> familias;
    private Set<Long> etnias;
    private Set<Long> representantes;
    private Set<Long> terrasindigena;

    public ParametrosRelatorioEstadia() {
    }

    public ParametrosRelatorioEstadia(Set<String> generos, String dataini, String datafim, Set<Long> familias, Set<Long> etnias, Set<Long> representantes, Set<Long> terrasindigena) {
        this.generos = generos;
        this.dataini = dataini;
        this.datafim = datafim;
        this.familias = familias;
        this.etnias = etnias;
        this.representantes = representantes;
        this.terrasindigena = terrasindigena;
    }

    public Set<String> getGeneros() {
        return generos;
    }

    public void setGeneros(Set<String> generos) {
        this.generos = generos;
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

    public Set<Long> getFamilias() {
        return familias;
    }

    public void setFamilias(Set<Long> familias) {
        this.familias = familias;
    }

    public Set<Long> getEtnias() {
        return etnias;
    }

    public void setEtnias(Set<Long> etnias) {
        this.etnias = etnias;
    }

    public Set<Long> getRepresentantes() {
        return representantes;
    }

    public void setRepresentantes(Set<Long> representantes) {
        this.representantes = representantes;
    }

    public Set<Long> getTerrasindigena() {
        return terrasindigena;
    }

    public void setTerrasindigena(Set<Long> terrasindigena) {
        this.terrasindigena = terrasindigena;
    }
        
}
