package br.projeto.repository;

import br.projeto.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepositoryImpl implements UsuarioRepository {
    private final Connection connection;
    private final List<Usuario> usuarios = new ArrayList<>();

    public UsuarioRepositoryImpl() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:banco.db");
            Statement statement = connection.createStatement();

            String sql =
                    "CREATE TABLE IF NOT EXISTS usuarios (\n"
                            + "	id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                            + "	nome text NOT NULL,\n"
                            + "	senha text NOT NULL,\n"
                            + " email text NOT NULL\n"
                            + ");";

            statement.execute(sql);
        } catch (SQLException e) {
            throw new IllegalArgumentException("Não foi possível conectar ao banco");
        }

    }

    @Override
    public void inserir(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nome, senha, email) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, usuario.getNome());
            pstmt.setString(2, usuario.getSenha());
            pstmt.setString(3, usuario.getEmail());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException("Não foi possível inserir o usuário", e);
        }
    }

    @Override
    public void atualizar(Usuario usuario, int id) {
        String sql = "UPDATE produto SET nome = ?, " + "senha = ? " + "email = ? " + "WHERE id = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, usuario.getNome());
            pstmt.setString(2, usuario.getSenha());
            pstmt.setString(3, usuario.getEmail());
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível atualizar objeto, pois o mesmo não existe");
        }
    }

    @Override
    public void deletar(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Usuario> listar() {
        String sql = "SELECT nome, senha, email FROM usuarios";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String nome = rs.getString("nome");
                String senha = rs.getString("senha");
                String email = rs.getString("email");
                Usuario usuario = new Usuario.Builder()
                        .nome(nome)
                        .senha(senha)
                        .email(email)
                        .build();
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Não foi possível listar os usuários", e);
        }
        return usuarios;
    }
}
