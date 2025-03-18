package br.projeto.dbConnection.factory;

import java.sql.SQLException;

import br.projeto.dbConnection.connections.H2Connection;
import br.projeto.dbConnection.connections.IDatabaseConnection;

public class H2ConnectionFactory implements IDatabaseConnectionFactory {

    @Override
    public IDatabaseConnection getConnection() throws SQLException {
        return new H2Connection();
    }
}
