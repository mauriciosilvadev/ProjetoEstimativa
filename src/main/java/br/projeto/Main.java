package br.projeto;

import javax.swing.SwingUtilities;
import br.projeto.adapter.LoggerAdapterImpl;
import br.projeto.dbConnection.factory.ConnectionFactory;
import br.projeto.presenter.LoginPresenter;
import br.projeto.repository.UsuarioRepositoryImpl;

public class Main {

    public static void main(String[] args) {
        // Inicializa o logger automaticamente, sem precisar passar caminho do arquivo
        LoggerAdapterImpl logger = new LoggerAdapterImpl.Builder()
                .logType("csv")  // ou "json"
                .build();

       
        SwingUtilities.invokeLater(() -> {
            LoginPresenter presenter = new LoginPresenter(new UsuarioRepositoryImpl(ConnectionFactory.getInstance().getConnection()));
        });
    }
}
