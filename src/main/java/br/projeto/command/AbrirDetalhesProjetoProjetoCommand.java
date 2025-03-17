package br.projeto.command;

import javax.swing.JDesktopPane;

import br.projeto.model.ProjetoEstimativa;
import br.projeto.presenter.DetalheProjetoPresenter;
import br.projeto.presenter.helpers.WindowManager;
import br.projeto.repository.ProjetoRepository;
import br.projeto.repository.UsuarioRepository;
import br.projeto.view.DetalheProjetoView;

public class AbrirDetalhesProjetoProjetoCommand implements ProjetoCommand {

    private final ProjetoRepository projetoRepository;
    private final UsuarioRepository usuarioRepository;
    private final JDesktopPane desktop;
    private final ProjetoEstimativa projeto;
    private final boolean isCompartilhado;

    public AbrirDetalhesProjetoProjetoCommand(ProjetoRepository projetoRepository, JDesktopPane desktop, UsuarioRepository usuarioRepository, ProjetoEstimativa projeto, boolean isCompartilhado) {
        this.projetoRepository = projetoRepository;
        this.usuarioRepository = usuarioRepository;
        this.isCompartilhado = isCompartilhado;
        this.projeto = projeto;
        this.desktop = desktop;
    }

    @Override
    public void execute() {
        String tituloJanela = "Detalhes do Projeto: " + projeto.getNome();
        WindowManager windowManager = WindowManager.getInstance();

        if (windowManager.isFrameAberto(tituloJanela)) {
            windowManager.bringToFront(tituloJanela);
        } else {
            DetalheProjetoView detalheView = new DetalheProjetoView();
            detalheView.setTitle(tituloJanela);
            new DetalheProjetoPresenter(detalheView, projetoRepository, projeto.getNome(), usuarioRepository, isCompartilhado);
            desktop.add(detalheView);
            detalheView.setVisible(true);
            try {
                detalheView.setMaximum(true);
            } catch (Exception ignored) {
            }
        }
    }
}
