package br.projeto.command;

import javax.swing.JDesktopPane;

import br.projeto.presenter.DashBoardProjetoPresenter;
import br.projeto.presenter.helpers.WindowManager;
import br.projeto.repository.ProjetoRepository;
import br.projeto.view.DashBoardProjetoView;

public class AbrirDashboardProjetoCommand implements ProjetoCommand {

    private final JDesktopPane desktop;
    private final ProjetoRepository projetoRepository;

    public AbrirDashboardProjetoCommand(JDesktopPane desktop, ProjetoRepository repository) {
        this.desktop = desktop;
        this.projetoRepository = repository;
    }

    @Override
    public void execute() {
        String tituloJanela = "Dashboard de Projetos";
        WindowManager windowManager = WindowManager.getInstance();

        if (windowManager.isFrameAberto(tituloJanela)) {
            windowManager.bringToFront(tituloJanela);
        } else {
            DashBoardProjetoView dashboardView = new DashBoardProjetoView();
            new DashBoardProjetoPresenter(dashboardView, projetoRepository);
            dashboardView.setTitle(tituloJanela);
            desktop.add(dashboardView);
            dashboardView.setVisible(true);
            try {
                dashboardView.setMaximum(true);
            } catch (Exception ignored) {
            }
        }
    }
}
