package br.projeto.dbConnection.factory;

import java.sql.SQLException;

import br.projeto.dbConnection.connections.IDatabaseConnection;

public interface IDatabaseConnectionFactory {

    IDatabaseConnection getConnection() throws SQLException;
}
