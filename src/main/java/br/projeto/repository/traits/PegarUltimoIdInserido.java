package br.projeto.repository.traits;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.projeto.dbConnection.connections.H2Connection;
import br.projeto.dbConnection.connections.IDatabaseConnection;
import br.projeto.dbConnection.connections.SqliteConnection;

public class PegarUltimoIdInserido {

    public PegarUltimoIdInserido() {
    }

    public int obterUltimoIdInserido(IDatabaseConnection conn, PreparedStatement ps) throws SQLException {
        if (conn instanceof SqliteConnection) {
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()")) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new SQLException("Falha ao obter o ID gerado no SQLite.");
                }
            }
        } else if (conn instanceof H2Connection) {
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new SQLException("Falha ao obter o ID gerado no H2.");
                }
            }
        } else {
            throw new SQLException("Tipo de conexão não suportado para obter o ID gerado.");
        }
    }
}
