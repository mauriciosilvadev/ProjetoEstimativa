package br.projeto.command;

import javax.swing.JDesktopPane;

import br.projeto.model.Perfil;
import br.projeto.presenter.CriarProjetoPresenter;
import br.projeto.presenter.helpers.WindowManager;
import br.projeto.repository.PerfilRepository;
import br.projeto.view.CriarProjetoView;

public class AbrirCriarProjetoCommand implements ProjetoCommand {

    private final PerfilRepository repository;
    private final JDesktopPane desktop;

    public AbrirCriarProjetoCommand(PerfilRepository repository, JDesktopPane desktop) {
        this.repository = repository;
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

        Perfil perfil = repository.buscarPorId(1);

        CriarProjetoView criarProjetoView = new CriarProjetoView();
        criarProjetoView.setTitle(tituloJanela);
        new CriarProjetoPresenter(criarProjetoView, repository);
        desktop.add(criarProjetoView);
        criarProjetoView.setVisible(true);
        try {
            criarProjetoView.setMaximum(true);
        } catch (Exception ignored) {
        }
    }
}
