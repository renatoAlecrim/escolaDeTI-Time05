package br.unicesumar.time05.pessoajuridica;

import br.unicesumar.time05.cidade.Cidade;
import br.unicesumar.time05.cidade.CidadeRepository;
import br.unicesumar.time05.cnpj.Cnpj;
import br.unicesumar.time05.consultapersonalizada.ConstrutorDeSQL;
import br.unicesumar.time05.consultapersonalizada.ParametrosConsulta;
import br.unicesumar.time05.consultapersonalizada.RetornoConsultaPaginada;
import br.unicesumar.time05.email.Email;
import br.unicesumar.time05.endereco.Endereco;
import br.unicesumar.time05.pessoa.TipoPessoa;
import classesbase.ServiceBase;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class JuridicaService extends ServiceBase<CriarPessoaJuridica, Long, JuridicaRepository> {

    @Autowired
    private CidadeRepository cidadeRepo;

    final String SQLConsultaJuridica = "SELECT p.idpessoa, p.nome, p.email, p.tipo_pessoa, pj.cnpj as documento, p.telefone,"
            + " p.telefonesecundario, ende.bairro, ende.cep, ende.complemento, ende.logradouro, ende.numero, c.descricao, u.sigla "
            + "FROM pessoa p"
            + " LEFT JOIN pessoa_juridica pj "
            + "    ON pj.idpessoa = p.idpessoa"
            + " LEFT JOIN endereco ende "
            + "    ON p.endereco_id = ende.idendereco"
            + " LEFT JOIN endereco_cidade ec "
            + "    ON ende.idendereco = ec.endereco_id"
            + " LEFT JOIN cidade c"
            + "    ON ec.cidade_id = c.codigoibge"
            + " LEFT JOIN uf u"
            + "    ON c.estado_codigoestado = u.codigoestado"
            + " WHERE p.tipo_pessoa = 'JURÍDICA'";

    @Override
    public void salvar(CriarPessoaJuridica aPessoaJuridica) {
        PessoaJuridica pessoaJuridica;
        Cidade cidade = cidadeRepo.findOne(aPessoaJuridica.getCodigoibge());
        Endereco end = new Endereco(aPessoaJuridica.getLogradouro(), aPessoaJuridica.getNumero(), aPessoaJuridica.getBairro(), aPessoaJuridica.getComplemento(), aPessoaJuridica.getCep(), cidade);
        pessoaJuridica = new PessoaJuridica(aPessoaJuridica, end);
        pessoaJuridica.setTipoPessoa(TipoPessoa.JURÍDICA);

        try {
            repository.saveAndFlush(pessoaJuridica);
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void alterar(CriarPessoaJuridica aJuridica) {
        PessoaJuridica juridica = repository.findOne(aJuridica.getIdpessoa());
        juridica.alterar(aJuridica);
        juridica.getEndereco().setCidade(cidadeRepo.findOne(aJuridica.getCodigoibge()));
        repository.save(juridica);
    }

    @Override
    public List<Map<String, Object>> listarSemPaginacao() {
        return query.execute(SQLConsultaJuridica);
    }

    @Override
    public RetornoConsultaPaginada listar() {
        return query.executeComPaginacao(SQLConsultaJuridica, "p.nome", new ParametrosConsulta()); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getObjeto(Long aId) {
        return repository.findOne(aId); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RetornoConsultaPaginada listar(ParametrosConsulta aParametrosConsulta) {
        return query.executeComPaginacao(SQLConsultaJuridica, "p.nome", aParametrosConsulta); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void setConstrutorDeSQL(br.unicesumar.time05.consultapersonalizada.ConstrutorDeSQL aConstrutorDeSQL) {
        super.setConstrutorDeSQL(new ConstrutorDeSQL(PessoaJuridica.class)); //To change body of generated methods, choose Tools | Templates.
    }

    public Map<String, String> verificarCnpj(Cnpj aCnpj, Long aUsuarioId) {
        aCnpj.setCnpj(aCnpj.getCnpj().replace("p", "/"));
        Map<String, String> retorno = new HashMap<>();
        if (aCnpj.valido()) {
            final MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("aCnpj", aCnpj.getCnpj());
            params.addValue("aId", aUsuarioId);
            List<Map<String, Object>> usuario = query.execute("SELECT idpessoa, cnpj FROM pessoa_juridica WHERE cnpj = :aCnpj AND idpessoa <> :aId", params);
            if (!usuario.isEmpty()) {
                retorno.put("retorno", "existe");
            } else {
                retorno.put("retorno", "valido");
            }
        } else {
            retorno.put("retorno", "invalido");
        }
        return retorno;
    }

    public Map<String, String> verificarCnpj(Cnpj aCnpj) {
        aCnpj.setCnpj(aCnpj.getCnpj().replace("p", "/"));
        Map<String, String> retorno = new HashMap<>();
        if (aCnpj.valido()) {
            final MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("aCnpj", aCnpj.getCnpj());
            List<Map<String, Object>> usuario = query.execute("SELECT cnpj FROM pessoa_juridica WHERE cnpj = :aCnpj", params);
            if (!usuario.isEmpty()) {
                retorno.put("retorno", "existe");
            } else {
                retorno.put("retorno", "valido");
            }
        } else {
            retorno.put("retorno", "invalido");
        }
        return retorno;
    }

    public boolean verificarEmail(Email aEmail) {
        if (aEmail != null && aEmail.verificarValido()) {
            final MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("aEmail", aEmail.getEmail());
            List<Map<String, Object>> pessoa = query.execute("SELECT email FROM pessoa WHERE email = :aEmail AND tipo_pessoa = 'JURÍDICA'", params);
            if (!pessoa.isEmpty()) {
                return false;
            }
            return true;
        } else {
            throw new RuntimeException("Campo email vazio!");
        }
    }

    boolean verificarEmail(Email aEmail, Long aPessoaId) {
        if (aEmail != null && aEmail.verificarValido()) {
            final MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("aEmail", aEmail.getEmail());
            params.addValue("aId", aPessoaId);
            List<Map<String, Object>> pessoa = query.execute("SELECT id, email FROM pessoa WHERE email = :aEmail AND id <> :aId AND tipo_pessoa = 'JURÍDICA'", params);
            if (!pessoa.isEmpty()) {
                return false;
            }
            return true;
        } else {
            throw new RuntimeException("Campo email vazio");
        }
    }

}
