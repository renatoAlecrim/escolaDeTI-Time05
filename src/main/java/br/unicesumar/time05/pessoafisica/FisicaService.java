package br.unicesumar.time05.pessoafisica;

import br.unicesumar.time05.cidade.Cidade;
import br.unicesumar.time05.cidade.CidadeRepository;
import br.unicesumar.time05.consultapersonalizada.ConstrutorDeSQL;
import br.unicesumar.time05.consultapersonalizada.ParametrosConsulta;
import br.unicesumar.time05.consultapersonalizada.RetornoConsultaPaginada;
import br.unicesumar.time05.cpf.CPF;
import br.unicesumar.time05.email.Email;
import br.unicesumar.time05.endereco.Endereco;
import br.unicesumar.time05.estadiafamilia.ParametrosRelatorioEstadia;
import br.unicesumar.time05.funcao.FuncaoRepository;
import br.unicesumar.time05.pessoa.TipoPessoa;
import br.unicesumar.time05.relatorios.formatoRelatorio;
import br.unicesumar.time05.upload.UploadService;
import classesbase.ServiceBase;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

@Transactional
@Component
public class FisicaService extends ServiceBase<CriarPessoaFisica, Long, FisicaRepository> {

    @Autowired
    private CidadeRepository cidadeRepo;
    @Autowired
    private FuncaoRepository funcaoRepo;
    @Autowired
    private UploadService uploadService;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    final String SQLConsultaFisicaCompleta = "SELECT p.idpessoa, p.nome, p.email, p.tipo_pessoa, pf.genero, pf.cpf as documento, p.telefone,"
            + " p.telefonesecundario, ende.bairro, ende.cep, ende.complemento, ende.logradouro, ende.numero, c.descricao, u.sigla "
            + " FROM pessoa p"
            + " LEFT JOIN pessoa_fisica pf "
            + "    ON pf.idpessoa = p.idpessoa "
            + " LEFT JOIN endereco ende "
            + "    ON p.endereco_id = ende.idendereco "
            + " LEFT JOIN endereco_cidade ec "
            + "    ON ende.idendereco = ec.endereco_id "
            + " LEFT JOIN cidade c "
            + "    ON ec.cidade_id = c.codigoibge "
            + " LEFT JOIN uf u "
            + "    ON c.estado_codigoestado = u.codigoestado "
            + " WHERE p.tipo_pessoa<>'JURÍDICA'";

    final String SQLConsultaFisica = "SELECT p.idpessoa, p.nome, p.email, p.tipo_pessoa, pf.genero, COALESCE(pf.cpf, pj.cnpj) as documento, p.telefone,"
            + " c.descricao, u.sigla "
            + "FROM pessoa p"
            + " LEFT JOIN pessoa_fisica pf "
            + "    ON pf.idpessoa = p.idpessoa"
            + " LEFT JOIN pessoa_juridica pj "
            + "    ON pj.idpessoa = p.idpessoa"
            + " LEFT JOIN endereco ende "
            + "    ON p.endereco_id = ende.idendereco"
            + " LEFT JOIN endereco_cidade ec "
            + "    ON ende.idendereco = ec.endereco_id"
            + " LEFT JOIN cidade c"
            + "    ON ec.cidade_id = c.codigoibge"
            + " LEFT JOIN uf u"
            + "    ON c.estado_codigoestado = u.codigoestado";
   /*         + " WHERE p.tipo_pessoa <> 'JURÍDICA'"
            + " UNION"
            + " SELECT p.idpessoa, p.nome, p.email, p.tipo_pessoa, pj.cnpj as documento, p.telefone,"
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
            + " WHERE p.tipo_pessoa = 'JURÍDICA'";*/

    @Override
    protected void setConstrutorDeSQL(br.unicesumar.time05.consultapersonalizada.ConstrutorDeSQL aConstrutorDeSQL) {
        super.setConstrutorDeSQL(new ConstrutorDeSQL(PessoaFisica.class)); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void salvar(CriarPessoaFisica aPessoaFisica) {
        PessoaFisica pessoaFisica;
        Cidade cidade = cidadeRepo.findOne(aPessoaFisica.getCodigoibge());
        Endereco end = new Endereco(aPessoaFisica.getLogradouro(), aPessoaFisica.getNumero(), aPessoaFisica.getBairro(), aPessoaFisica.getComplemento(), aPessoaFisica.getCep(), cidade);
        pessoaFisica = new PessoaFisica(aPessoaFisica, end, funcaoRepo.findOne(aPessoaFisica.getIdfuncao()));
        pessoaFisica.setTipoPessoa(aPessoaFisica.getTipo());

        try {
            repository.saveAndFlush(pessoaFisica);
            if (aPessoaFisica.getImgSrc() != null && aPessoaFisica.getImgSrc().startsWith("data:image/jpeg;base64")) {
                uploadService.uploadWebcam(aPessoaFisica.getImgSrc(), pessoaFisica.getIdpessoa(), "users");
            }
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void alterar(CriarPessoaFisica aFisica) {
        PessoaFisica fisica = repository.findOne(aFisica.getIdpessoa());
        fisica.alterar(aFisica);
        fisica.getEndereco().setCidade(cidadeRepo.findOne(aFisica.getCodigoibge()));
        fisica.setFuncao(funcaoRepo.findOne(aFisica.getIdfuncao()));
        if (aFisica.getImgSrc() != null && aFisica.getImgSrc().startsWith("data:image/jpeg;base64")) {
            uploadService.uploadWebcam(aFisica.getImgSrc(), aFisica.getIdpessoa(), "users");
        }
        repository.save(fisica);
    }

    @Override
    public List<Map<String, Object>> listarSemPaginacao() {
        return query.execute(SQLConsultaFisica);
    }
    
    public List<Map<String, Object>> listarFisicas() {
        return query.execute(SQLConsultaFisicaCompleta);
    }

    @Override
    public RetornoConsultaPaginada listar() {
        return query.executeComPaginacao(SQLConsultaFisica, "p.nome", new ParametrosConsulta());
    }

    @Override
    public RetornoConsultaPaginada listar(ParametrosConsulta aParametrosConsulta) {
        return query.executeComPaginacao(SQLConsultaFisica, "p.nome", aParametrosConsulta); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getObjeto(Long aId) {
        return repository.findOne(aId);
    }

    public void trocarTipoPessoa(Long aPessoaId, String tipo) {
        PessoaFisica pessoa = repository.getOne(aPessoaId);
        switch (tipo) {
            case "USUÁRIO":
                pessoa.setTipoPessoa(TipoPessoa.USUÁRIO);
                break;
            case "VISITANTE":
                pessoa.setTipoPessoa(TipoPessoa.VISITANTE);
                break;
            case "FÍSICA":
                pessoa.setTipoPessoa(TipoPessoa.FÍSICA);
                break;
            case "JURÍDICA":
                pessoa.setTipoPessoa(TipoPessoa.JURÍDICA);
                break;
        }
        repository.save(pessoa);
    }

    public Map<String, String> verificarCpf(CPF aCpf, Long aUsuarioId) {
        Map<String, String> retorno = new HashMap<>();
        if (aCpf.valido()) {
            final MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("aCpf", aCpf.getCpf());
            params.addValue("aId", aUsuarioId);
            List<Map<String, Object>> usuario = query.execute("SELECT idpessoa, cpf FROM pessoa_fisica WHERE cpf = :aCpf AND idpessoa <> :aId", params);
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

    public Map<String, String> verificarCpf(CPF aCpf) {
        Map<String, String> retorno = new HashMap<>();
        if (aCpf.valido()) {
            final MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("aCpf", aCpf.getCpf());
            List<Map<String, Object>> usuario = query.execute("SELECT cpf FROM pessoa_fisica WHERE cpf = :aCpf", params);
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
            List<Map<String, Object>> usuario = query.execute("SELECT email FROM pessoa WHERE email = :aEmail AND tipo_pessoa = 'JURÍDICA'", params);
            if (!usuario.isEmpty()) {
                return false;
            }
            return true;
        } else {
            throw new RuntimeException("Campo email vazio!");
        }
    }

    public boolean verificarEmail(Email aEmail, Long aPessoaId) {
        if (aEmail != null && aEmail.verificarValido()) {
            final MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("aEmail", aEmail);
            params.addValue("aId", aPessoaId);
            List<Map<String, Object>> fisica = query.execute("SELECT id, email FROM pessoa WHERE email = :aEmail AND id <> :aId AND tipo_pessoa <> 'JURÍDICA'", params);
            if (!fisica.isEmpty()) {
                return false;
            }
            return true;
        } else {
            throw new RuntimeException("Campo email vazio!");
        }
    }   
    
    public Map<String, String> gerarRelatorio(formatoRelatorio formatoRelatorio, ParametrosRelatorioColaborador parametros) {
        ObjectMapper objMapper = new ObjectMapper();
        Map<String, Object> MapParametros = objMapper.convertValue(parametros, Map.class);
        return rel.gerarRelatorio("Colaborador.jrxml", formatoRelatorio, MapParametros);
    }

}
