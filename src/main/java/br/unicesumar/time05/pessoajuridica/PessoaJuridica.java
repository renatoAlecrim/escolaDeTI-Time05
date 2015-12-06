package br.unicesumar.time05.pessoajuridica;

import br.unicesumar.time05.consultapersonalizada.CampoConsulta;
import br.unicesumar.time05.cnpj.Cnpj;
import br.unicesumar.time05.email.Email;
import br.unicesumar.time05.endereco.Endereco;
import br.unicesumar.time05.pessoa.Pessoa;
import br.unicesumar.time05.pessoa.TipoPessoa;
import br.unicesumar.time05.telefone.Telefone;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"cnpj"}, name = "uk_cnpj")})
@Inheritance(strategy = InheritanceType.JOINED)
public class PessoaJuridica extends Pessoa implements Serializable {

    @CampoConsulta
    @Embedded
    @Column(unique = true, nullable = false)
    private Cnpj cnpj;

    public PessoaJuridica() {
    }

    public PessoaJuridica(Cnpj cnpj, String nome, Telefone telefoneP, Telefone telefoneS, Email email, Endereco endereco, TipoPessoa tipoPessoa) {
        super(nome, telefoneP, telefoneS, email, endereco, tipoPessoa);
        this.cnpj = cnpj;
    }

    public PessoaJuridica(Cnpj cnpj, String nome, Telefone telefoneP, Telefone telefoneS, Email email) {
        super(nome, telefoneP, telefoneS, email);
        this.cnpj = cnpj;
    }

    public PessoaJuridica(CriarPessoaJuridica p, Endereco end) {
        this(p.getCnpj(), p.getNome(), p.getTelefone(), p.getTelefonesecundario(), p.getEmail(), end, p.getTipo());
    }

    public void alterar(CriarPessoaJuridica aJuridica) {
        this.setNome(aJuridica.getNome());
        this.setTelefone(aJuridica.getTelefone());
        this.setTelefonesecundario(aJuridica.getTelefonesecundario());
        this.setEmail(aJuridica.getEmail());
        this.setTipoPessoa(TipoPessoa.JURÍDICA);
        this.setCnpj(aJuridica.getCnpj());
        this.getEndereco().setLogradouro(aJuridica.getLogradouro());
        this.getEndereco().setNumero(aJuridica.getNumero());
        this.getEndereco().setBairro(aJuridica.getBairro());
        this.getEndereco().setComplemento(aJuridica.getComplemento());
        this.getEndereco().setCep(aJuridica.getCep());
    }

    public Cnpj getCnpj() {
        return cnpj;
    }

    public void setCnpj(Cnpj cnpj) {
//        cnpj.setCnpj(String.format("/^d{2}.d{3}.d{3}/d{4}-d{2}$/", cnpj));
        this.cnpj = cnpj;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.cnpj);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PessoaJuridica other = (PessoaJuridica) obj;
        if (!Objects.equals(this.cnpj, other.cnpj)) {
            return false;
        }
        return true;
    }

}
