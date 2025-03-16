package br.projeto.command;

import javax.swing.JDesktopPane;

import br.projeto.model.ProjetoEstimativa;
import br.projeto.presenter.EditarProjetoPresenter;
import br.projeto.presenter.helpers.WindowManager;
import br.projeto.repository.PerfilRepository;
import br.projeto.repository.ProjetoRepository;
import br.projeto.view.ProjetoEstimativaView;

public class AbrirEditarProjetoCommand implements ProjetoCommand {

    private final PerfilRepository perfilRepository;
    private final ProjetoRepository projetoRepository;
    private final ProjetoEstimativa projeto;
    private final JDesktopPane desktop;

    public AbrirEditarProjetoCommand(PerfilRepository perfilRepository, ProjetoRepository projetoRepository, ProjetoEstimativa projeto, JDesktopPane desktop) {
        this.perfilRepository = perfilRepository;
        this.projetoRepository = projetoRepository;
        this.projeto = projeto;
        this.desktop = desktop;
    }

    @Override
    public void execute() {
        WindowManager windowManager = WindowManager.getInstance();

        String tituloJanela = "Editar " + projeto.getNome();

        if (windowManager.isFrameAberto(tituloJanela)) {
            windowManager.bringToFront(tituloJanela);
            return;
        }

        ProjetoEstimativaView projetoEstimativaView = new ProjetoEstimativaView();
        projetoEstimativaView.setTitle(tituloJanela);
        new EditarProjetoPresenter(projetoEstimativaView, perfilRepository, projetoRepository, projeto);
        desktop.add(projetoEstimativaView);
        projetoEstimativaView.setVisible(true);
        try {
            projetoEstimativaView.setMaximum(true);
        } catch (Exception ignored) {
        }
    }
}
