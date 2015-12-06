package br.unicesumar.time05.usuario;

import br.unicesumar.time05.email.Email;
import br.unicesumar.time05.consultapersonalizada.ConstrutorDeSQL;
import br.unicesumar.time05.consultapersonalizada.ParametrosConsulta;
import br.unicesumar.time05.consultapersonalizada.RetornoConsultaPaginada;
import br.unicesumar.time05.cidade.CidadeRepository;
import br.unicesumar.time05.funcao.FuncaoRepository;
import br.unicesumar.time05.perfildeacesso.PerfilDeAcesso;
import br.unicesumar.time05.perfildeacesso.PerfilDeAcessoRepository;
import br.unicesumar.time05.pessoa.TipoPessoa;
import br.unicesumar.time05.pessoafisica.FisicaRepository;
import br.unicesumar.time05.pessoafisica.PessoaFisica;
import br.unicesumar.time05.upload.UploadService;
import classesbase.ServiceBase;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

@Component
public class UsuarioService extends ServiceBase<Usuario, Long, UsuarioRepository> {

    @Autowired
    private PerfilDeAcessoRepository perfilRepo;
    @Autowired
    private FuncaoRepository funcaoRepo;
    @Autowired
    private CidadeRepository cidadeRepo;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private FisicaRepository fisicaRepository;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    private final String SQLConsultaUsuarios
            = "SELECT u.idusuario, "
            + "       u.login, "
            + "       u.status, "
            + "       COALESCE(p.nome, u.nome) AS nome, "
            + "       COALESCE(p.email, u.email) AS email "
            + " FROM usuario u"
            + " LEFT JOIN pessoa p"
            + "        ON u.pessoa_idpessoa = p.idpessoa";
    
    private final String SQLConsultaUsuarioPorID
            = "SELECT u.idusuario, u.login, u.status, p.nome, p.email"
            + " FROM usuario u"
            + " LEFT JOIN pessoa p"
            + "        ON u.pessoa_idpessoa = p.idpessoa"
            + " WHERE u.idusuario = :idusuario";

    private final String ordenar = "p.nome";

    public UsuarioService() {
        setConstrutorDeSQL(new ConstrutorDeSQL(Usuario.class));
    }

    @Override
    public void salvar(Usuario aEntidade) {
        try {
            aEntidade.setLogin(aEntidade.getLogin().trim());
            if(aEntidade.getLogin().contains(" "))
                throw new RuntimeException("Login não deve ter espaço!");
            if(repository.count()<1){
                aEntidade.setPerfis(perfilRepo.findAll());
            }
            if(aEntidade.getPessoa() != null){
                aEntidade.setNome(aEntidade.getPessoa().getNome());
                aEntidade.setEmail(aEntidade.getPessoa().getEmail());
                PessoaFisica f = fisicaRepository.findOne(aEntidade.getPessoa().getIdpessoa());
                f.setTipoPessoa(TipoPessoa.USUÁRIO);
                repository.save(aEntidade);
                fisicaRepository.save(f);
            }else{
                repository.save(aEntidade);
            }
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void alterar(Usuario aEntidade) {
        this.salvar(aEntidade);
    }

    @Override
    public List<Map<String, Object>> findByID(Long aUsuarioId) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("aUsuarioId", aUsuarioId);
        List<Map<String, Object>> usuario = query.execute(this.SQLConsultaUsuarioPorID, params);
        return Collections.unmodifiableList(usuario);
    }

    @Override
    public RetornoConsultaPaginada listar(ParametrosConsulta parametrosConsulta) {
        return query.executeComPaginacao(this.SQLConsultaUsuarios, ordenar, parametrosConsulta);
    }

    @Override
    public RetornoConsultaPaginada listar() {
        return query.executeComPaginacao(this.SQLConsultaUsuarios, ordenar, new ParametrosConsulta());
    }

    @Override
    public List<Map<String, Object>> listarSemPaginacao() {
        List<Map<String, Object>> usuarios = query.execute(this.SQLConsultaUsuarios, new MapSqlParameterSource());
        return Collections.unmodifiableList(usuarios);
    }

    public boolean verificarLogin(String aLogin) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("aLogin", aLogin);
        List<Map<String, Object>> usuario = query.execute("SELECT login FROM usuario WHERE login = :aLogin", params);
        //se o array usuario estiver vazio retorna true, indicando que o login está disponível
        return usuario.isEmpty();
    }

    public boolean verificarSenha(Senha aSenha) {
        return aSenha.senhaValida();
    }

    public void trocarStatusUsuario(Long aUsuarioId) {
        try {
            Usuario usuario = super.repository.getOne(aUsuarioId);
            usuario.trocaStatus();
            super.repository.save(usuario);
        } catch (Exception e) {
            throw new RuntimeException("Usuario não encontrado!");
        }
    }

    public boolean verificarEmail(Email aEmail) {
        if (aEmail.verificarValido()) {
            final MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("aEmail", aEmail.getEmail());
            List<Map<String, Object>> usuario = query.execute("SELECT email FROM pessoa WHERE email = :aEmail", params);
            if (!usuario.isEmpty()) {
                return false;
            }
            return true;
        } else {
            throw new RuntimeException("Campo email vazio!");
        }
    }

    public boolean verificarLogin(String aLogin, Long aUsuarioId) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("aLogin", aLogin);
        params.addValue("aId", aUsuarioId);
        List<Map<String, Object>> usuario = query.execute("SELECT p.idpessoa, u.login FROM usuario u, pessoa p WHERE u.login = :aLogin AND p.idpessoa <> :aId", params);
        return usuario.isEmpty();
    }

    public void addPerfil(Long aUsuarioId, Long[] aPerfilId) {
        Usuario usuario = super.repository.findOne(aUsuarioId);
        List<PerfilDeAcesso> perfis = new ArrayList<>();
        for (Long aPerfil : aPerfilId) {
            perfis.add(perfilRepo.findOne(aPerfil));
        }
        usuario.setPerfis(perfis);
        repository.save(usuario);
        //this.salvarUsuario(usuario);
    }

    public List<Map<String, Object>> getPerfis(Long aUsuarioId) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("aId", aUsuarioId);
        String sql
                = "SELECT p.idperfildeacesso, "
                + "       p.nome "
                + "  FROM usuario_perfis up "
                + "  JOIN perfildeacesso p ON (up.perfis_idperfildeacesso = p.idperfildeacesso) "
                + " WHERE up.usuario_idusuario = :aId";
        List<Map<String, Object>> itensPerfilDeAcesso = query.execute(sql, params);
        return itensPerfilDeAcesso;
    }

    public void deletePerfis(Long aUsuarioId, Long[] perfis) {
        Usuario usuario = super.repository.findOne(aUsuarioId);
        for (Long perfil : perfis) {
            usuario.removerPerfil(perfilRepo.findOne(perfil));
        }
    }

    public List<Map<String, Object>> getColaboradores() {
        return query.execute("SELECT idpessoa, nome, email FROM pessoa WHERE tipo_pessoa = 'COLABORADOR'");
    }

}
