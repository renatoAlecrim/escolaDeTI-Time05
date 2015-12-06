package br.unicesumar.time05.pessoafisica;

import java.util.Set;

class ParametrosRelatorioColaborador {
    
    private Set<String> generos;
    private Set<Long> funcoes;

    public ParametrosRelatorioColaborador() {
    }

    public ParametrosRelatorioColaborador(Set<String> generos, Set<Long> funcoes) {
        this.generos = generos;
        this.funcoes = funcoes;
    }

    public Set<String> getGeneros() {
        return generos;
    }

    public void setGeneros(Set<String> generos) {
        this.generos = generos;
    }

    public Set<Long> getFuncoes() {
        return funcoes;
    }

    public void setFuncoes(Set<Long> funcoes) {
        this.funcoes = funcoes;
    }
        
}
