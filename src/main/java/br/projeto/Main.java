package br.projeto;

import br.projeto.presenter.PrincipalPresenter;
import br.projeto.presenter.helpers.WindowManager;
import br.projeto.repository.ProjetoRepositoryMock;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PrincipalPresenter presenter = new PrincipalPresenter(new ProjetoRepositoryMock());
            WindowManager.getInstance().initialize(presenter);
        });
    }
}


