package br.projeto;

import javax.swing.SwingUtilities;

import br.projeto.dbConnection.factory.ConnectionFactory;
import br.projeto.presenter.LoginPresenter;
import br.projeto.repository.UsuarioRepositoryImpl;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginPresenter presenter = new LoginPresenter(new UsuarioRepositoryImpl(ConnectionFactory.getInstance().getConnection()));
        });
    }
}
