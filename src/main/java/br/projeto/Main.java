package br.projeto;

import br.projeto.presenter.LoginPresenter;
import br.projeto.presenter.PrincipalPresenter;
import br.projeto.presenter.helpers.WindowManager;
import br.projeto.repository.ProjetoRepositoryImpl;
import br.projeto.repository.UsuarioRepositoryImpl;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginPresenter presenter = new LoginPresenter(new UsuarioRepositoryImpl());

        });
    }
}


