/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.projeto.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.projeto.dbConnection.connections.IDatabaseConnection;
import br.projeto.model.Categoria;
import br.projeto.model.Funcionalidade;

/**
 *
 * @author Maurício Silva <mauricio.s.dev@gmail.com>
 */
public class FuncionalidadeRepositoryImpl implements FuncionalidadeRepository {

    private final IDatabaseConnection connection;
    private static FuncionalidadeRepositoryImpl instance;

    private FuncionalidadeRepositoryImpl(IDatabaseConnection connection) {
        this.connection = connection;
    }

    public static FuncionalidadeRepositoryImpl getInstance(IDatabaseConnection connection) {
        if (instance == null) {
            return new FuncionalidadeRepositoryImpl(connection);
        }
        return instance;
    }

    @Override
    public Funcionalidade buscarPorId(int id) {
        // Query para buscar funcionalidade
        String queryPlataforma = "SELECT f.id, f.nome, c.id AS categoria_id, c.nome AS categoria_nome, c.tipo AS categoria_tipo "
                + "FROM funcionalidades f "
                + "JOIN categorias c ON f.categoria_id = c.id "
                + "WHERE f.id = ?";

        try {
            PreparedStatement stmtPlataforma = connection.prepareStatement(queryPlataforma);
            stmtPlataforma.setInt(1, id);
            ResultSet rsPlataforma = stmtPlataforma.executeQuery();

            if (rsPlataforma.next()) {

                Categoria categoria = new Categoria(
                        rsPlataforma.getInt("categoria_id"),
                        rsPlataforma.getString("categoria_nome"),
                        rsPlataforma.getString("categoria_tipo")
                );

                return new Funcionalidade(
                        rsPlataforma.getInt("id"),
                        categoria,
                        rsPlataforma.getString("nome")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
