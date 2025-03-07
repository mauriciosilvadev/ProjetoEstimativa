package br.projeto.dbConnection.factory;

import java.sql.SQLException;

import br.projeto.dbConnection.connections.IDatabaseConnection;

public class ConnectionFactory {

    private static ConnectionFactory instance;
    private IDatabaseConnection connection;

    private ConnectionFactory() {
        try {
            this.connection = (new SqliteConnectionFactory()).getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized ConnectionFactory getInstance() {
        if (instance == null) {
            instance = new ConnectionFactory();
        }
        return instance;
    }

    public IDatabaseConnection getConnection() {
        return connection;
    }
}
