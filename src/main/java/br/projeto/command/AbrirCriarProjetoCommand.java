package br.projeto.command;

import javax.swing.JDesktopPane;

import br.projeto.presenter.CriarProjetoPresenter;
import br.projeto.presenter.helpers.WindowManager;
import br.projeto.repository.PerfilRepository;
import br.projeto.repository.ProjetoRepository;
import br.projeto.view.ProjetoEstimativaView;

public class AbrirCriarProjetoCommand implements ProjetoCommand {

    private final PerfilRepository perfilRepository;
    private final ProjetoRepository projetoRepository;
    private final JDesktopPane desktop;

    public AbrirCriarProjetoCommand(PerfilRepository perfilRepository, ProjetoRepository projetoRepository,
            JDesktopPane desktop) {
        this.perfilRepository = perfilRepository;
        this.projetoRepository = projetoRepository;
        this.desktop = desktop;
    }

    @Override
    public void execute() {
        WindowManager windowManager = WindowManager.getInstance();

        String tituloJanela = "Criar Projeto";

        if (windowManager.isFrameAberto(tituloJanela)) {
            windowManager.bringToFront(tituloJanela);
            return;
        }

        ProjetoEstimativaView projetoEstimativaView = new ProjetoEstimativaView();
        projetoEstimativaView.setTitle(tituloJanela);
        new CriarProjetoPresenter(projetoEstimativaView, perfilRepository, projetoRepository);
        desktop.add(projetoEstimativaView);
        projetoEstimativaView.setVisible(true);
        try {
            projetoEstimativaView.setMaximum(true);
        } catch (Exception ignored) {
        }
    }
}
