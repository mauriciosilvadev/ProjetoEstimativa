package br.projeto.dbConnection.factory;

import java.sql.SQLException;

import br.projeto.dbConnection.connections.IDatabaseConnection;
import br.projeto.dbConnection.connections.SqliteConnection;

public class SqliteConnectionFactory implements IDatabaseConnectionFactory {

    @Override
    public IDatabaseConnection getConnection() throws SQLException {
        return new SqliteConnection();
    }

}
