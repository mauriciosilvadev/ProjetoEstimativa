package br.projeto.presenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;

import br.projeto.command.AbrirCompartilharProjetoEstimativaCommand;
import br.projeto.command.AbrirCriarProjetoCommand;
import br.projeto.command.AbrirDashboardProjetoCommand;
import br.projeto.command.AbrirDetalhesProjetoProjetoCommand;
import br.projeto.command.AbrirInternalFrameGenericoProjetoCommand;
import br.projeto.command.ExcluirProjetoProjetoCommand;
import br.projeto.command.MostrarMensagemProjetoCommand;
import br.projeto.command.ProjetoCommand;
import br.projeto.command.SairCommand;
import br.projeto.dbConnection.connections.IDatabaseConnection;
import br.projeto.model.ProjetoEstimativa;
import br.projeto.presenter.helpers.WindowManager;
import br.projeto.presenter.window_command.ConfigurarMenuJanelaCommand;
import br.projeto.presenter.window_command.ConfigurarViewCommand;
import br.projeto.presenter.window_command.FecharJanelasRelacionadasCommand;
import br.projeto.presenter.window_command.SetLookAndFeelCommand;
import br.projeto.presenter.window_command.WindowCommand;
import br.projeto.repository.PerfilRepository;
import br.projeto.repository.PerfilRepositoryImpl;
import br.projeto.repository.ProjetoRepository;
import br.projeto.repository.ProjetoRepositoryImpl;
import br.projeto.repository.UsuarioRepository;
import br.projeto.repository.UsuarioRepositoryImpl;
import br.projeto.service.ConstrutorDeArvoreNavegacaoService;
import br.projeto.service.NoArvoreComposite;
import br.projeto.session.UsuarioSession;
import br.projeto.view.GlobalWindowManager;
import br.projeto.view.PrincipalView;

public final class PrincipalPresenter implements Observer {

    private final LoginPresenter presenter;
    private final PrincipalView view;
    private final ProjetoRepository projetoRepository;
    private final PerfilRepository perfilRepository;
    private final UsuarioRepository usuarioRepository;
    private final ConstrutorDeArvoreNavegacaoService construtorDeArvoreNavegacaoService;
    private final Map<String, ProjetoCommand> comandos;
    private final List<WindowCommand> windowCommands = new ArrayList<>();

    public PrincipalPresenter(IDatabaseConnection connection, LoginPresenter presenter) {
        this.presenter = presenter;
        this.view = new PrincipalView();

        this.projetoRepository = ProjetoRepositoryImpl.getInstance(connection);
        this.projetoRepository.addObserver(this);

        this.usuarioRepository = new UsuarioRepositoryImpl(connection);

        this.perfilRepository = PerfilRepositoryImpl.getInstance(connection);

        this.construtorDeArvoreNavegacaoService = new ConstrutorDeArvoreNavegacaoService();

        GlobalWindowManager.initialize(view);

        this.comandos = inicializarComandos();

        inicializarEExecutarWindowCommands();
        view.setVisible(true);
    }

    private void inicializarEExecutarWindowCommands() {
        Arrays.asList(
                new ConfigurarViewCommand(this),
                new ConfigurarMenuJanelaCommand(this),
                new SetLookAndFeelCommand()
        ).forEach(WindowCommand::execute);
    }

    private Map<String, ProjetoCommand> inicializarComandos() {
        Map<String, ProjetoCommand> comandos = new HashMap<>();
        comandos.put("Principal", new AbrirDashboardProjetoCommand(view.getDesktop(), projetoRepository));
        comandos.put("Usuário", new AbrirInternalFrameGenericoProjetoCommand(view.getDesktop(), "Usuário"));
        comandos.put("Ver perfis de projeto", new AbrirInternalFrameGenericoProjetoCommand(view.getDesktop(), "Ver Perfis de Projetos"));
        comandos.put("Elaborar estimativa", new MostrarMensagemProjetoCommand("Elaborar estimativa ainda não implementada"));
        comandos.put("Exportar projeto de estimativa", new MostrarMensagemProjetoCommand("Exportar ainda não implementado"));
        comandos.put("Novo projeto", new AbrirCriarProjetoCommand(perfilRepository, projetoRepository, view.getDesktop()));
        comandos.put("Excluir projeto", new ExcluirProjetoProjetoCommand(projetoRepository));
        comandos.put("Sair", new SairCommand(this, presenter));
        
        return comandos;
    }

    public void configurarArvore() {
        NoArvoreComposite raiz = construtorDeArvoreNavegacaoService.criarNo("Principal", "principal", comandos.get("Principal"));
        NoArvoreComposite noUsuario = construtorDeArvoreNavegacaoService.criarNo(UsuarioSession.getInstance().getUsuarioLogado().getNome(), "usuario", comandos.get("Usuário"));
        NoArvoreComposite noPerfis = construtorDeArvoreNavegacaoService.criarNo("Ver perfis de projeto", "perfil", comandos.get("Ver perfis de projeto"));
        NoArvoreComposite noProjetos = construtorDeArvoreNavegacaoService.criarNo("Projetos", "projeto", null);
        NoArvoreComposite noProjetosCompartilhados = construtorDeArvoreNavegacaoService.criarNo("Projetos Compartilhados", "projeto", null);

        noProjetos.setMenuContextual(() -> {
            JPopupMenu menu = new JPopupMenu();
            JMenuItem novoProjetoItem = new JMenuItem("Novo Projeto");
            novoProjetoItem.addActionListener(e -> {
                ProjetoCommand cmd = comandos.get("Novo projeto");
                if (cmd != null) {
                    cmd.execute();
                }
            });
            menu.add(novoProjetoItem);
            return menu;
        });

        // raiz.adicionarFilho(noUsuario);
        // raiz.adicionarFilho(noPerfis);
        raiz.adicionarFilho(noProjetos);
        raiz.adicionarFilho(noProjetosCompartilhados);

        List<ProjetoEstimativa> projetosCompartilhados = projetoRepository.getProjetosCompartilhados();
        for (final ProjetoEstimativa projeto : projetosCompartilhados) {
            AbrirDetalhesProjetoProjetoCommand cmdDetalhes = new AbrirDetalhesProjetoProjetoCommand(projetoRepository, view.getDesktop(), usuarioRepository, projeto, true) {
                @Override
                public void execute() {
                    String tituloJanela = "Detalhes do Projeto: " + projeto.getNome();
                    WindowManager windowManager = WindowManager.getInstance();

                    if (!windowManager.isFrameAberto(tituloJanela)) {
                        super.execute();
                        bloquearMinimizacao(tituloJanela);
                    } else {
                        windowManager.bringToFront(tituloJanela);
                    }
                }
            };

            NoArvoreComposite noProjetoCompartilhado = construtorDeArvoreNavegacaoService.criarNo(projeto.getNome(), "projeto", cmdDetalhes);

            noProjetoCompartilhado.adicionarFilho(construtorDeArvoreNavegacaoService.criarNo("Visualizar Detalhes", "action", cmdDetalhes));
            noProjetosCompartilhados.adicionarFilho(noProjetoCompartilhado);
        }

        List<ProjetoEstimativa> projetos = projetoRepository.getProjetos();
        for (final ProjetoEstimativa projeto : projetos) {
            AbrirDetalhesProjetoProjetoCommand cmdDetalhes = new AbrirDetalhesProjetoProjetoCommand(projetoRepository, view.getDesktop(), usuarioRepository, projeto, false) {
                @Override
                public void execute() {
                    String tituloJanela = "Detalhes do Projeto: " + projeto.getNome();
                    WindowManager windowManager = WindowManager.getInstance();

                    if (!windowManager.isFrameAberto(tituloJanela)) {
                        super.execute();
                        bloquearMinimizacao(tituloJanela);
                    } else {
                        windowManager.bringToFront(tituloJanela);
                    }
                }
            };

            NoArvoreComposite noProjeto = construtorDeArvoreNavegacaoService.criarNo(projeto.getNome(), "projeto", cmdDetalhes);
            AbrirCompartilharProjetoEstimativaCommand cmdCompartilhar = new AbrirCompartilharProjetoEstimativaCommand(usuarioRepository, projetoRepository, projeto, view.getDesktop()) {
                @Override
                public void execute() {
                    String tituloJanela = "Compartilhar Projeto: " + projeto.getNome();
                    WindowManager windowManager = WindowManager.getInstance();

                    if (!windowManager.isFrameAberto(tituloJanela)) {
                        super.execute();
                        bloquearMinimizacao(tituloJanela);
                    } else {
                        windowManager.bringToFront(tituloJanela);
                    }
                }
            };

            noProjeto.adicionarFilho(construtorDeArvoreNavegacaoService.criarNo("Visualizar Detalhes", "action", cmdDetalhes));
            noProjeto.adicionarFilho(construtorDeArvoreNavegacaoService.criarNo("Compartilhar projeto de estimativa", "action", cmdCompartilhar));
            noProjeto.adicionarFilho(construtorDeArvoreNavegacaoService.criarNo("Exportar projeto de estimativa", "action", comandos.get("Exportar projeto de estimativa")));
            noProjetos.adicionarFilho(noProjeto);
        }

        DefaultMutableTreeNode modeloArvore = construtorDeArvoreNavegacaoService.converterParaNoMutavel(raiz);
        JTree arvore = construtorDeArvoreNavegacaoService.criarJTreeDoModelo(modeloArvore);
        view.setTree(arvore);
    }

    @Override
    public void update(final List<ProjetoEstimativa> listaProjetos) {
        SwingUtilities.invokeLater(() -> {
            WindowCommand fecharJanelasCommand = new FecharJanelasRelacionadasCommand(view.getDesktop(), listaProjetos);
            fecharJanelasCommand.execute();
            configurarArvore();
        });
    }

    private void bloquearMinimizacao(String titulo) {
        JInternalFrame[] frames = view.getDesktop().getAllFrames();
        for (JInternalFrame frame : frames) {
            if (frame.getTitle().equals(titulo)) {
                frame.setIconifiable(false);
            }
        }
    }

    public void restaurarJanelas() {
        DesktopMemento memento = Zelador.getInstance().restaurarEstado();
        if (memento != null) {
            memento.restore(getView().getDesktop());
            getView().revalidate();
            getView().repaint();
        } else {
            new MostrarMensagemProjetoCommand("Nenhum estado anterior salvo para restaurar.").execute();
        }
    }

    public Map<String, ProjetoCommand> getComandos() {
        return comandos;
    }

    public ProjetoRepository getRepository() {
        return projetoRepository;
    }

    public PrincipalView getView() {
        return view;
    }
}
