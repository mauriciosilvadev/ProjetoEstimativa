package br.projeto.dbConnection.factory;

import java.sql.SQLException;

import br.projeto.dbConnection.connections.IDatabaseConnection;
import io.github.cdimascio.dotenv.Dotenv;

public class ConnectionFactory {

    private static ConnectionFactory instance;
    private IDatabaseConnection connection;

    private ConnectionFactory() {
        try {
            Dotenv env = Dotenv.load();

            if (env.get("DB_TYPE").equals("h2")) {
                this.connection = (new H2ConnectionFactory()).getConnection();
            } else if (env.get("DB_TYPE").equals("sqlite")) {
                this.connection = (new SqliteConnectionFactory()).getConnection();
            }
            // this.connection = (new SqliteConnectionFactory()).getConnection();
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
