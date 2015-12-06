package br.unicesumar.time05.itemacesso;

import br.unicesumar.time05.consultapersonalizada.QueryPersonalizada;
import br.unicesumar.time05.cidade.CidadeRepository;
import br.unicesumar.time05.perfildeacesso.PerfilDeAcesso;
import br.unicesumar.time05.perfildeacesso.PerfilDeAcessoRepository;
import br.unicesumar.time05.uf.UFRepository;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class InicializadorItemAcesso {

    @Autowired
    private ItemAcessoRepository repo;

    @Autowired
    private CidadeRepository cidRepo;

    @Autowired
    private UFRepository UfRepo;
    @Autowired
    private PerfilDeAcessoRepository perfilRepo;

    @Autowired
    protected QueryPersonalizada query;

    private ItemAcesso getItemAcesso(List<ItemAcesso> lista, String nome, String rota) {

        for (ItemAcesso item : lista) {
            if (item.getNome().equals(nome) && item.getRota().equals(rota)) {
                return item;
            }
        }
        return null;
    }

    @PostConstruct
    public void inicializar() {
        carregarUF();
        carregarEstados();
        carregarConvenios();
        carregarTerraIndigena();
        carregarEtnias();
        carregarFuncao();
        carregarOutros();

        List<ItemAcesso> itensAcesso = new ArrayList<>();

        itensAcesso = repo.findAll();

        ItemAcesso menu;
        menu = this.getItemAcesso(itensAcesso, "Menu", "/");
        if (menu == null) {
            menu = new ItemAcesso("Menu", "/", "fa-bars");
            repo.save(menu);
            itensAcesso.add(menu);
        }

        //ETNIA
        ItemAcesso menuEtnia;
        menuEtnia = this.getItemAcesso(itensAcesso, "Gerenciar Etnia", "");
        if (menuEtnia == null) {
            menuEtnia = new ItemAcesso("Gerenciar Etnia", "", "fa-flag", menu);
            itensAcesso.add(menuEtnia);
        }

        ItemAcesso menuEtniaListar;
        menuEtniaListar = this.getItemAcesso(itensAcesso, " Listar Etnia", "#/Etnia/listar");
        if (menuEtniaListar == null) {
            menuEtniaListar = new ItemAcesso(" Listar Etnia", "#/Etnia/listar", "fa-list", menuEtnia);
            itensAcesso.add(menuEtniaListar);
        }

        ItemAcesso menuEtniaNovo;
        menuEtniaNovo = this.getItemAcesso(itensAcesso, "Cadastrar Etnia", "#/Etnia/nova");
        if (menuEtniaNovo == null) {
            menuEtniaNovo = new ItemAcesso("Cadastrar Etnia", "#/Etnia/nova", "fa-plus", menuEtnia);
            itensAcesso.add(menuEtniaNovo);
        }

        //TERRA INDIGENA
        ItemAcesso menuTerraIndigena;
        menuTerraIndigena = this.getItemAcesso(itensAcesso, "Gerenciar Terra Indígena", "");
        if (menuTerraIndigena == null) {
            menuTerraIndigena = new ItemAcesso("Gerenciar Terra Indígena", "", "fa-leaf", menu);
            itensAcesso.add(menuTerraIndigena);
        }

        ItemAcesso menuTerraIndigenaListar;
        menuTerraIndigenaListar = this.getItemAcesso(itensAcesso, " Listar Terra Indígena", "#/TerraIndigena/listar");
        if (menuTerraIndigenaListar == null) {
            menuTerraIndigenaListar = new ItemAcesso(" Listar Terra Indígena", "#/TerraIndigena/listar", "fa-list", menuTerraIndigena);
            itensAcesso.add(menuTerraIndigenaListar);
        }

        ItemAcesso menuTerraIndigenaNovo;
        menuTerraIndigenaNovo = this.getItemAcesso(itensAcesso, "Cadastrar Terra Indígena", "#/TerraIndigena/nova");
        if (menuTerraIndigenaNovo == null) {
            menuTerraIndigenaNovo = new ItemAcesso("Cadastrar Terra Indígena", "#/TerraIndigena/nova", "fa-plus", menuTerraIndigena);
            itensAcesso.add(menuTerraIndigenaNovo);
        }

        //INDIGENA
        ItemAcesso menuIndigena;
        menuIndigena = this.getItemAcesso(itensAcesso, "Gerenciar Indígena", "");
        if (menuIndigena == null) {
            menuIndigena = new ItemAcesso("Gerenciar Indígena", "", "fa-users", menu);
            itensAcesso.add(menuIndigena);
        }

        ItemAcesso menuIndigenaListar;
        menuIndigenaListar = this.getItemAcesso(itensAcesso, " Listar Indígena", "#/Indigena/listar");
        if (menuIndigenaListar == null) {
            menuIndigenaListar = new ItemAcesso(" Listar Indígena", "#/Indigena/listar", "fa-list", menuIndigena);
            itensAcesso.add(menuIndigenaListar);
        }

        ItemAcesso menuIndigenaNovo;
        menuIndigenaNovo = this.getItemAcesso(itensAcesso, "Cadastrar Indígena", "#/Indigena/novo");
        if (menuIndigenaNovo == null) {
            menuIndigenaNovo = new ItemAcesso("Cadastrar Indígena", "#/Indigena/novo", "fa-plus", menuIndigena);
            itensAcesso.add(menuIndigenaNovo);
        }
        
        //FAMILIA
        ItemAcesso menuFamilia;
        menuFamilia = this.getItemAcesso(itensAcesso, "Gerenciar Família", "");
        if (menuFamilia == null) {
            menuFamilia = new ItemAcesso("Gerenciar Família", "", "fa-sitemap", menu);
            itensAcesso.add(menuFamilia);
        }

        ItemAcesso menuFamiliaListar;
        menuFamiliaListar = this.getItemAcesso(itensAcesso, " Listar Família", "#/Familia/listar");
        if (menuFamiliaListar == null) {
            menuFamiliaListar = new ItemAcesso(" Listar Família", "#/Familia/listar", "fa-list", menuFamilia);
            itensAcesso.add(menuFamiliaListar);
        }

        ItemAcesso menuFamiliaNova;
        menuFamiliaNova = this.getItemAcesso(itensAcesso, "Cadastrar Família", "#/Familia/nova");
        if (menuFamiliaNova == null) {
            menuFamiliaNova = new ItemAcesso("Cadastrar Família", "#/Familia/nova", "fa-plus", menuFamilia);
            itensAcesso.add(menuFamiliaNova);
        }

        //CONVENIO
        ItemAcesso menuConvenio;
        menuConvenio = this.getItemAcesso(itensAcesso, "Gerenciar Convênio", "");
        if (menuConvenio == null) {
            menuConvenio = new ItemAcesso("Gerenciar Convênio", "", "fa-credit-card", menu);
            itensAcesso.add(menuConvenio);
        }

        ItemAcesso menuConvenioListar;
        menuConvenioListar = this.getItemAcesso(itensAcesso, " Listar Convênio", "#/Convenio/listar");
        if (menuConvenioListar == null) {
            menuConvenioListar = new ItemAcesso(" Listar Convênio", "#/Convenio/listar", "fa-list", menuConvenio);
            itensAcesso.add(menuConvenioListar);
        }

        ItemAcesso menuConvenioNovo;
        menuConvenioNovo = this.getItemAcesso(itensAcesso, "Cadastrar Convênio", "#/Convenio/novo");
        if (menuConvenioNovo == null) {
            menuConvenioNovo = new ItemAcesso("Cadastrar Convênio", "#/Convenio/novo", "fa-plus", menuConvenio);
            itensAcesso.add(menuConvenioNovo);
        }

        //USUARIO
        ItemAcesso menuUsuario;
        menuUsuario = this.getItemAcesso(itensAcesso, "Gerenciar Usuário", "");
        if (menuUsuario == null) {
            menuUsuario = new ItemAcesso("Gerenciar Usuário", "", "fa-user", menu);
            itensAcesso.add(menuUsuario);
        }

        ItemAcesso menuUsuarioListar;
        menuUsuarioListar = this.getItemAcesso(itensAcesso, " Listar Usuário", "#/Usuario/listar");
        if (menuUsuarioListar == null) {
            menuUsuarioListar = new ItemAcesso(" Listar Usuário", "#/Usuario/listar", "fa-list", menuUsuario);
            itensAcesso.add(menuUsuarioListar);
        }

        ItemAcesso menuUsuarioNovo;
        menuUsuarioNovo = this.getItemAcesso(itensAcesso, "Cadastrar Usuário", "#/Usuario/novo");
        if (menuUsuarioNovo == null) {
            menuUsuarioNovo = new ItemAcesso("Cadastrar Usuário", "#/Usuario/novo", "fa-plus", menuUsuario);
            itensAcesso.add(menuUsuarioNovo);
        }

        
        //PESSOA
        ItemAcesso menuPessoa;
        menuPessoa = this.getItemAcesso(itensAcesso, "Gerenciar Pessoa", "");
        if (menuPessoa == null) {
            menuPessoa = new ItemAcesso("Gerenciar Pessoa", "", "fa-user", menu);
            itensAcesso.add(menuPessoa);
        }
        
        ItemAcesso menuPessoaListar;
        menuPessoaListar = this.getItemAcesso(itensAcesso, " Listar Pessoa", "#/Pessoa/listar");
        if (menuPessoaListar == null) {
            menuPessoaListar = new ItemAcesso(" Listar Pessoa", "#/Pessoa/listar", "fa-list", menuPessoa);
            itensAcesso.add(menuPessoaListar);
        }
        
        // PESSOA FISICA
        ItemAcesso menuFisicaNovo;
        menuFisicaNovo = this.getItemAcesso(itensAcesso, "Cadastrar Pessoa Física", "#/Fisica/nova");
        if (menuFisicaNovo == null) {
            menuFisicaNovo = new ItemAcesso("Cadastrar Pessoa Física", "#/Fisica/nova", "fa-plus", menuPessoa);
            itensAcesso.add(menuFisicaNovo);
        }
        // PESSOA JURIDICA
        ItemAcesso menuJuridicaNovo;
        menuJuridicaNovo = this.getItemAcesso(itensAcesso, "Cadastrar Pessoa Jurídica", "#/Juridica/nova");
        if (menuJuridicaNovo == null) {
            menuJuridicaNovo = new ItemAcesso("Cadastrar Pessoa Jurídica", "#/Juridica/nova", "fa-plus", menuPessoa);
            itensAcesso.add(menuJuridicaNovo);
        }

        //PERFIL
        ItemAcesso menuPerfil;
        menuPerfil = this.getItemAcesso(itensAcesso, "Gerenciar Perfil", "");
        if (menuPerfil == null) {
            menuPerfil = new ItemAcesso("Gerenciar Perfil", "", "fa-pencil", menu);
            itensAcesso.add(menuPerfil);
        }

        ItemAcesso menuPerfilListar;
        menuPerfilListar = this.getItemAcesso(itensAcesso, " Listar Perfil", "#/Perfil/listar");
        if (menuPerfilListar == null) {
            menuPerfilListar = new ItemAcesso(" Listar Perfil", "#/Perfil/listar", "fa-list", menuPerfil);
            itensAcesso.add(menuPerfilListar);
        }

        ItemAcesso menuPerfilNovo;
        menuPerfilNovo = this.getItemAcesso(itensAcesso, "Cadastrar Perfil", "#/Perfil/novo");
        if (menuPerfilNovo == null) {
            menuPerfilNovo = new ItemAcesso("Cadastrar Perfil", "#/Perfil/novo", "fa-plus", menuPerfil);
            itensAcesso.add(menuPerfilNovo);
        }
        
        //EVENTOS
        ItemAcesso menuEventos;
        menuEventos = this.getItemAcesso(itensAcesso, "Gerenciar Eventos", "");
        if (menuEventos == null) {
            menuEventos = new ItemAcesso("Gerenciar Eventos", "", "fa-calendar", menu);
            itensAcesso.add(menuEventos);
        }

        ItemAcesso menuEventosListar;
        menuEventosListar = this.getItemAcesso(itensAcesso, " Listar Eventos", "#/Eventos/listar");
        if (menuEventosListar == null) {
            menuEventosListar = new ItemAcesso(" Listar Eventos", "#/Eventos/listar", "fa-list-alt", menuEventos);
            itensAcesso.add(menuEventosListar);
        }

        ItemAcesso menuEventosNovo;
        menuEventosNovo = this.getItemAcesso(itensAcesso, "Cadastrar Eventos", "#/Eventos/novo");
        if (menuEventosNovo == null) {
            menuEventosNovo = new ItemAcesso("Cadastrar Eventos", "#/Eventos/novo", "fa-plus", menuEventos);
            itensAcesso.add(menuEventosNovo);
        }

        //FUNCAO
        ItemAcesso menuFuncao;
        menuFuncao = this.getItemAcesso(itensAcesso, "Gerenciar Função", "");
        if (menuFuncao == null) {
            menuFuncao = new ItemAcesso("Gerenciar Função", "", "fa-puzzle-piece", menu);
            itensAcesso.add(menuFuncao);
        }

        ItemAcesso menuFuncaoListar;
        menuFuncaoListar = this.getItemAcesso(itensAcesso, " Listar Função", "#/Funcao/listar");
        if (menuFuncaoListar == null) {
            menuFuncaoListar = new ItemAcesso(" Listar Função", "#/Funcao/listar", "fa-list", menuFuncao);
            itensAcesso.add(menuFuncaoListar);
        }

        ItemAcesso menuFuncaoNovo;
        menuFuncaoNovo = this.getItemAcesso(itensAcesso, "Cadastrar Função", "#/Funcao/nova");
        if (menuFuncaoNovo == null) {
            menuFuncaoNovo = new ItemAcesso("Cadastrar Função", "#/Funcao/nova", "fa-plus", menuFuncao);
            itensAcesso.add(menuFuncaoNovo);
        }

        ItemAcesso menuEstadia;
        menuEstadia = this.getItemAcesso(itensAcesso, "Gerenciar Estadia", "");
        if (menuEstadia == null) {
            menuEstadia = new ItemAcesso("Gerenciar Estadia", "", "fa-sitemap", menu);
            itensAcesso.add(menuEstadia);
        }

        ItemAcesso menuEstadiaListar;
        menuEstadiaListar = this.getItemAcesso(itensAcesso, " Listar Estadia", "#/Estadia/listar");
        if (menuEstadiaListar == null) {
            menuEstadiaListar = new ItemAcesso(" Listar Estadia", "#/Estadia/listar", "fa-list-alt", menuEstadia);
            itensAcesso.add(menuEstadiaListar);
        }

        ItemAcesso menuEstadiaNovo;
        menuEstadiaNovo = this.getItemAcesso(itensAcesso, "Cadastrar Estadia", "#/Estadia/nova");
        if (menuEstadiaNovo == null) {
            menuEstadiaNovo = new ItemAcesso("Cadastrar Estadia", "#/Estadia/nova", "fa-plus", menuEstadia);
            itensAcesso.add(menuEstadiaNovo);
        }

        ItemAcesso menuVisita;
        menuVisita = this.getItemAcesso(itensAcesso, "Gerenciar Visita", "");
        if (menuVisita == null) {
            menuVisita = new ItemAcesso("Gerenciar Visita", "", "fa-bus", menu);
            itensAcesso.add(menuVisita);
        }

        ItemAcesso menuVisitaListar;
        menuVisitaListar = this.getItemAcesso(itensAcesso, " Listar Visita", "#/Visita/listar");
        if (menuVisitaListar == null) {
            menuVisitaListar = new ItemAcesso(" Listar Visita", "#/Visita/listar", "fa-list-alt", menuVisita);
            itensAcesso.add(menuVisitaListar);
        }

        ItemAcesso menuVisitaNova;
        menuVisitaNova = this.getItemAcesso(itensAcesso, "Cadastrar Visita", "#/Visita/nova");
        if (menuVisitaNova == null) {
            menuVisitaNova = new ItemAcesso("Cadastrar Visita", "#/Visita/nova", "fa-plus", menuVisita);
            itensAcesso.add(menuVisitaNova);
        }
        
        //RELATORIOS
        ItemAcesso menuRelatorios;
        menuRelatorios = this.getItemAcesso(itensAcesso, "Relatórios", "");
        if (menuRelatorios == null) {
            menuRelatorios = new ItemAcesso("Relatórios", "", "fa-list ", menu);
            itensAcesso.add(menuRelatorios);
        }

        ItemAcesso menuRelatoriosIndios;
        menuRelatoriosIndios = this.getItemAcesso(itensAcesso, " Fluxo de Indígenas", "#/Relatorios/indios");
        if (menuRelatoriosIndios == null) {
            menuRelatoriosIndios = new ItemAcesso(" Fluxo de Indígenas", "#/Relatorios/indios", "fa-list", menuRelatorios);
            itensAcesso.add(menuRelatoriosIndios);
        }
        ItemAcesso menuRelatoriosEstadias;
        menuRelatoriosEstadias = this.getItemAcesso(itensAcesso, " Fluxo de Estadias", "#/Relatorios/estadias");
        if (menuRelatoriosEstadias == null) {
            menuRelatoriosEstadias = new ItemAcesso(" Fluxo de Estadias", "#/Relatorios/estadias", "fa-list", menuRelatorios);
            itensAcesso.add(menuRelatoriosEstadias);
        }
        ItemAcesso menuRelatoriosColaboradores;
        menuRelatoriosColaboradores = this.getItemAcesso(itensAcesso, "Colaboradores", "#/Relatorios/colaboradores");
        if (menuRelatoriosColaboradores == null) {
            menuRelatoriosColaboradores = new ItemAcesso("Colaboradores", "#/Relatorios/colaboradores", "fa-list", menuRelatorios);
            itensAcesso.add(menuRelatoriosColaboradores);
        }
        ItemAcesso menuRelatoriosVisitas;
        menuRelatoriosVisitas = this.getItemAcesso(itensAcesso, "Visitas", "#/Relatorios/visitas");
        if (menuRelatoriosVisitas == null) {
            menuRelatoriosVisitas = new ItemAcesso("Visitas", "#/Relatorios/visitas", "fa-list", menuRelatorios);
            itensAcesso.add(menuRelatoriosVisitas);
        }
        
        for (ItemAcesso ia : itensAcesso) {
            repo.save(ia);
        }

        if (perfilRepo.count() == 0) {
            PerfilDeAcesso perfilAdm = new PerfilDeAcesso("Administrador", new HashSet<>(repo.findAll()));
            perfilRepo.save(perfilAdm);
        }

    }

    public void carregarUF() {
        final String FILE_NAME_UF = "src/main/java/SCRIPTS/uf.txt";
        carregarScript(new File(FILE_NAME_UF));
    }

    public void carregarEstados() {
        final String FILE_NAME_CIDADES = "src/main/java/SCRIPTS/cidades.txt";
        carregarScript(new File(FILE_NAME_CIDADES));
    }

    public void carregarConvenios() {
        final String FILE_NAME_CONVENIO = "src/main/java/SCRIPTS/convenio.txt";
        carregarScript(new File(FILE_NAME_CONVENIO));
    }

    public void carregarTerraIndigena() {
        final String FILE_NAME_TERRA = "src/main/java/SCRIPTS/terraindigena.txt";
        carregarScript(new File(FILE_NAME_TERRA));
    }
    
    public void carregarEtnias() {
        final String FILE_NAME_ETNIA = "src/main/java/SCRIPTS/Etnias.txt";
        carregarScript(new File(FILE_NAME_ETNIA));
    }
    
    public void carregarFuncao() {
        final String FILE_NAME_FUNCAO = "src/main/java/SCRIPTS/funcao.txt";
        carregarScript(new File(FILE_NAME_FUNCAO));
    }
    
    public void carregarOutros() {
        final String FILE_NAME_OUTROS = "src/main/java/SCRIPTS/outrosInserts.txt";
        carregarScript(new File(FILE_NAME_OUTROS));
    }

    public void carregarScript(File arquivo) {
        try {
            FileInputStream fileInputStream = new FileInputStream(arquivo);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                try {
                    query.execute(linha);
                } catch (Exception e) {
                }
            }

            bufferedReader.close();
            inputStreamReader.close();
            fileInputStream.close();
        } catch (Exception ex) {
            throw new RuntimeException("Falha ao carregar script");
        }
    }
}
