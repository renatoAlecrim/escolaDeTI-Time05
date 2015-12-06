package br.unicesumar.time05.consultapersonalizada;

public class CampoParaScriptSQL {

    String campo;
    TipoComparacao comparacao;

    public CampoParaScriptSQL(String aCampo, TipoComparacao aComparacao) {
        this.campo = aCampo;
        this.comparacao = aComparacao;
    }

    public String getCampo() {
        return campo;
    }

    public TipoComparacao getComparacao() {
        return comparacao;
    }
}
