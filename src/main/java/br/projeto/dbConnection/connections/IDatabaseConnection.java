package br.projeto.dbConnection.connections;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public interface IDatabaseConnection {

    boolean disconnect() throws SQLException;

    Statement createStatement() throws SQLException;

    PreparedStatement prepareStatement(String sql) throws SQLException;
}
