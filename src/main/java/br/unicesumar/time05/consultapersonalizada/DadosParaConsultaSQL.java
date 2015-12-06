package br.unicesumar.time05.consultapersonalizada;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DadosParaConsultaSQL {

    String nomeTabela = "";
    String idTabela = "";
    String campoOrdenacaoPadrao = "";

    List<CampoParaScriptSQL> campos;

    public DadosParaConsultaSQL() {
        campos = new ArrayList<>();
    }
    
    public String getIdTabela() {
        return idTabela;
    }

    public void setIdTabela(String aIdTabela) {
        this.idTabela = aIdTabela;
    }

    public void setNomeTabela(String aNomeTabela) {
        this.nomeTabela = aNomeTabela;
    }

    public void setCampoOrdenacaoPadrao(String aCampoOrdenacaoPadrao) {
        this.campoOrdenacaoPadrao = aCampoOrdenacaoPadrao;
    }

    public void addCampo(CampoParaScriptSQL aCampo) {
        this.campos.add(aCampo);
    }

    public String getNomeTabela() {
        return nomeTabela;
    }

    public List<CampoParaScriptSQL> getCampos() {
        return Collections.unmodifiableList(campos);
    }

    public String getCampoOrdenacaoPadrao() {
        return campoOrdenacaoPadrao;
    }
}
