package br.unicesumar.time05.cnpj;

import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Matcher;
import javax.persistence.Embeddable;
import org.hibernate.validator.constraints.br.CNPJ;

@Embeddable
public class Cnpj implements Serializable {

    @CNPJ
    private String cnpj;

    public Cnpj() {
    }

    public Cnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.cnpj);
        return hash;
    }

    public boolean valido() {
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("^\\d{2}\\.\\d{3}\\.\\d{3}\\/\\d{4}\\-\\d{2}$");
        Matcher matcher = pattern.matcher(this.cnpj);
        String replace;
        if (!matcher.matches()) {
            return false;
        } else {
            replace = this.cnpj.replace(".", "");
            replace = replace.replace("-", "");
            replace = replace.replace("/", "");
        }
        if (replace.equals("00000000000000") || replace.equals("11111111111111")
                || replace.equals("22222222222222") || replace.equals("33333333333333")
                || replace.equals("44444444444444") || replace.equals("55555555555555")
                || replace.equals("66666666666666") || replace.equals("77777777777777")
                || replace.equals("88888888888888") || replace.equals("99999999999999")
                || (replace.length() != 14)) {
            return (false);
        }
        char dig13, dig14;
        int sm, i, r, num, peso;
        try {
            sm = 0;
            peso = 2;
            for (i = 11; i >= 0; i--) {
                num = (int) (replace.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10) {
                    peso = 2;
                }
            }
            r = sm % 11;
            if ((r == 0) || (r == 1)) {
                dig13 = '0';
            } else {
                dig13 = (char) ((11 - r) + 48);
            }
            sm = 0;
            peso = 2;
            for (i = 12; i >= 0; i--) {
                num = (int) (replace.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10) {
                    peso = 2;
                }
            }
            r = sm % 11;
            if ((r == 0) || (r == 1)) {
                dig14 = '0';
            } else {
                dig14 = (char) ((11 - r) + 48);
            }
            if ((dig13 == replace.charAt(12)) && (dig14 == replace.charAt(13))) {
                return (true);
            } else {
                return (false);
            }
        } catch (Exception e) {
            return (false);
        }
    }

    @Override
    public boolean equals(Object obj
    ) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cnpj other = (Cnpj) obj;
        if (!Objects.equals(this.cnpj, other.cnpj)) {
            return false;
        }
        return true;
    }

}
