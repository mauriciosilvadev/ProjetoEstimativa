package br.projeto;

import javax.swing.SwingUtilities;

import br.projeto.db.DatabaseInicializador;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DatabaseInicializador.iniciarBancoDeDados();
            // LoginPresenter presenter = new LoginPresenter(new UsuarioRepositoryImpl());
        });
    }
}
