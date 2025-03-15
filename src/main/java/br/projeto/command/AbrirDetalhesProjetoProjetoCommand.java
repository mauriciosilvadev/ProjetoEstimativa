package br.projeto.command;

import javax.swing.JDesktopPane;

import br.projeto.presenter.DetalheProjetoPresenter;
import br.projeto.presenter.helpers.WindowManager;
import br.projeto.repository.ProjetoRepository;
import br.projeto.repository.UsuarioRepository;
import br.projeto.view.DetalheProjetoView;

public class AbrirDetalhesProjetoProjetoCommand implements ProjetoCommand {

    private final ProjetoRepository projetoRepository;
    private final UsuarioRepository usuarioRepository;
    private final JDesktopPane desktop;
    private String projetoNome;

    public AbrirDetalhesProjetoProjetoCommand(ProjetoRepository projetoRepository, JDesktopPane desktop, UsuarioRepository usuarioRepository) {
        this.projetoRepository = projetoRepository;
        this.usuarioRepository = usuarioRepository;
        this.desktop = desktop;
    }

    public void setProjetoNome(String projetoNome) {
        this.projetoNome = projetoNome;
    }

    @Override
    public void execute() {
        if (projetoNome == null || projetoNome.isEmpty()) {
            throw new IllegalStateException("O nome do projeto não foi definido para este comando.");
        }

        String tituloJanela = "Detalhes do Projeto: " + projetoNome;
        WindowManager windowManager = WindowManager.getInstance();

        if (windowManager.isFrameAberto(tituloJanela)) {
            windowManager.bringToFront(tituloJanela);
        } else {
            DetalheProjetoView detalheView = new DetalheProjetoView();
            detalheView.setTitle(tituloJanela);
            new DetalheProjetoPresenter(detalheView, projetoRepository, projetoNome, usuarioRepository);
            desktop.add(detalheView);
            detalheView.setVisible(true);
            try {
                detalheView.setMaximum(true);
            } catch (Exception ignored) {
            }
        }
    }
}
