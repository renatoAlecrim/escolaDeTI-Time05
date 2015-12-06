package br.unicesumar.time05.cpf;

import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Matcher;
import javax.persistence.Embeddable;

@Embeddable
public class CPF implements Serializable {

    @org.hibernate.validator.constraints.br.CPF
    private String cpf;

    public CPF() {
    }

    public CPF(String cpf) {
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public boolean valido() {
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$");
        Matcher matcher = pattern.matcher(this.cpf);
        String replace;
        if(!matcher.matches()){
            return false;
        }else{
            replace = this.cpf.replace(".", "");
            replace = replace.replace("-", "");
        }
        if (replace.equals("00000000000") || replace.equals("11111111111")
                || replace.equals("22222222222") || replace.equals("33333333333")
                || replace.equals("44444444444") || replace.equals("55555555555")
                || replace.equals("66666666666") || replace.equals("77777777777")
                || replace.equals("88888888888") || replace.equals("99999999999")
                || (replace.length() != 11)) {
            return (false);
        }
        char dig10, dig11;
        int sm, i, r, num, peso;
        try {
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                num = (int) (replace.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }
            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)) {
                dig10 = '0';
            } else {
                dig10 = (char) (r + 48);
            }
            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (int) (replace.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }
            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)) {
                dig11 = '0';
            } else {
                dig11 = (char) (r + 48);
            }
            if ((dig10 == replace.charAt(9)) && (dig11 == replace.charAt(10))) {
                return (true);
            } else {
                return (false);
            }
        } catch (Exception e) {
            return (false);
        }

    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.cpf);
        return hash;
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
        final CPF other = (CPF) obj;
        if (!Objects.equals(this.cpf, other.cpf)) {
            return false;
        }
        return true;
    }

}
