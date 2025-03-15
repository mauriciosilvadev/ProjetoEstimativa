/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.projeto.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.projeto.dbConnection.connections.IDatabaseConnection;
import br.projeto.model.Plataforma;

/**
 *
 * @author Maurício Silva <mauricio.s.dev@gmail.com>
 */
public class PlataformaRepositoryImpl implements PlataformaRepository {

    private final IDatabaseConnection connection;
    private static PlataformaRepository instance;

    private PlataformaRepositoryImpl(IDatabaseConnection connection) {
        this.connection = connection;
    }

    public static PlataformaRepository getInstance(IDatabaseConnection connection) {
        if (instance == null) {
            return new PlataformaRepositoryImpl(connection);
        }
        return instance;
    }

    @Override
    public Plataforma buscarPorId(int id) {
        // Query para buscar a plataforma
        String queryPlataforma = "SELECT id, nome FROM plataformas WHERE id = ?";

        try {
            PreparedStatement stmtPlataforma = connection.prepareStatement(queryPlataforma);
            stmtPlataforma.setInt(1, id);
            ResultSet rsPlataforma = stmtPlataforma.executeQuery();

            if (rsPlataforma.next()) {
                return new Plataforma(
                        rsPlataforma.getInt("id"),
                        rsPlataforma.getString("nome")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
