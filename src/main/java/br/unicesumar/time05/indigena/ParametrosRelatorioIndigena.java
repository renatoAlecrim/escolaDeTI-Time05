package br.unicesumar.time05.indigena;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

public class ParametrosRelatorioIndigena implements Serializable{

    private Date dataini;
    private Date datafim;
    private int idadefim;
    private int idadeini;
    private Set<Long> etnias;
    private Set<Long> familias;
    private Set<String> escolaridades;
    private Set<String> estadoscivis;
    private Set<String> generos;
    private Set<Long> terrasindigena;
    private Set<Long> convenios;

    public ParametrosRelatorioIndigena() {
    }

    public ParametrosRelatorioIndigena(Date dataini, Date datafim, int idadefim, int idadeini, Set<Long> etnias, Set<Long> familias, Set<String> escolaridades, Set<String> estadoscivis, Set<String> generos, Set<Long> terrasindigena, Set<Long> convenios) {
        this.dataini = dataini;
        this.datafim = datafim;
        this.idadefim = idadefim;
        this.idadeini = idadeini;
        this.etnias = etnias;
        this.familias = familias;
        this.escolaridades = escolaridades;
        this.estadoscivis = estadoscivis;
        this.generos = generos;
        this.terrasindigena = terrasindigena;
        this.convenios = convenios;
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

    public int getIdadefim() {
        return idadefim;
    }

    public void setIdadefim(int idadefim) {
        this.idadefim = idadefim;
    }

    public int getIdadeini() {
        return idadeini;
    }

    public void setIdadeini(int idadeini) {
        this.idadeini = idadeini;
    }

    public Set<Long> getEtnias() {
        return etnias;
    }

    public void setEtnias(Set<Long> etnias) {
        this.etnias = etnias;
    }

    public Set<Long> getFamilias() {
        return familias;
    }

    public void setFamilias(Set<Long> familias) {
        this.familias = familias;
    }

    public Set<String> getEscolaridades() {
        return escolaridades;
    }

    public void setEscolaridades(Set<String> escolaridades) {
        this.escolaridades = escolaridades;
    }

    public Set<String> getEstadoscivis() {
        return estadoscivis;
    }

    public void setEstadoscivis(Set<String> estadoscivis) {
        this.estadoscivis = estadoscivis;
    }

    public Set<String> getGeneros() {
        return generos;
    }

    public void setGeneros(Set<String> generos) {
        this.generos = generos;
    }

    public Set<Long> getTerrasindigena() {
        return terrasindigena;
    }

    public void setTerrasindigena(Set<Long> terrasindigena) {
        this.terrasindigena = terrasindigena;
    }

    public Set<Long> getConvenios() {
        return convenios;
    }

    public void setConvenios(Set<Long> convenios) {
        this.convenios = convenios;
    }

    
       
}
