package br.projeto.command;

import br.projeto.presenter.DashBoardProjetoPresenter;
import br.projeto.repository.ProjetoRepositoryMock;
import br.projeto.view.DashBoardProjetoView;

import javax.swing.*;

public class AbrirDashboardProjetoCommand implements ProjetoCommand {
    private final JDesktopPane desktop;
    private final ProjetoRepositoryMock repository;

    public AbrirDashboardProjetoCommand(JDesktopPane desktop, ProjetoRepositoryMock repository) {
        this.desktop = desktop;
        this.repository = repository;
    }

    @Override
    public void execute() {
        DashBoardProjetoView dashboardView = new DashBoardProjetoView();
        new DashBoardProjetoPresenter(dashboardView, repository);
        desktop.add(dashboardView);
        dashboardView.setVisible(true);
        try {
            dashboardView.setMaximum(true);
        } catch (Exception ignored) {
        }
    }
}
