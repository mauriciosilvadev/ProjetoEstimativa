package br.projeto.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.projeto.dbConnection.connections.IDatabaseConnection;
import br.projeto.model.Usuario;
import br.projeto.repository.traits.PegarUltimoIdInserido;
import br.projeto.session.UsuarioSession;

public class UsuarioRepositoryImpl extends PegarUltimoIdInserido implements UsuarioRepository {

    private final IDatabaseConnection connection;

    public UsuarioRepositoryImpl(IDatabaseConnection connection) {
        this.connection = connection;
    }

    @Override
    public Usuario inserir(String nome, String email, String senha) {
        String sql = "INSERT INTO usuarios (nome, email, senha) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.setString(2, email);
            pstmt.setString(3, senha);
            pstmt.executeUpdate();

            int id = obterUltimoIdInserido(connection, pstmt);

            return new Usuario.Builder()
                    .id(id)
                    .nome(nome)
                    .email(email)
                    .senha(senha)
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível inserir o usuário", e);
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
        String sql = "SELECT * FROM usuarios";

        List<Usuario> usuarios = new ArrayList<>();

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
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

    @Override
    public List<Usuario> buscarUsuariosByNome(String nomePesquisado) {

        int idUsuarioLogado = UsuarioSession.getInstance().getUsuarioLogado().getId();

        String sql = "SELECT * FROM usuarios WHERE UPPER(nome) LIKE UPPER(?) AND id != ?";

        List<Usuario> usuarios = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + nomePesquisado + "%");
            ps.setInt(2, idUsuarioLogado);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nome = rs.getString("nome");
                    String senha = rs.getString("senha");
                    String email = rs.getString("email");
                    Usuario usuario = new Usuario.Builder()
                            .id(id)
                            .nome(nome)
                            .senha(senha)
                            .email(email)
                            .build();
                    usuarios.add(usuario);
                }
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Não foi possível listar os usuários", e);
        }

        return usuarios;
    }

    @Override
    public Usuario buscarPorId(int id) {
        String sql = "SELECT nome, senha, email FROM usuarios WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String nome = rs.getString("nome");
                String senha = rs.getString("senha");
                String email = rs.getString("email");
                return new Usuario.Builder()
                        .id(id)
                        .nome(nome)
                        .senha(senha)
                        .email(email)
                        .build();
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Não foi possível buscar o usuário", e);
        }
        return null;
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        String sql = "SELECT id, nome, senha, email FROM usuarios WHERE email = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String senha = rs.getString("senha");
                return new Usuario.Builder()
                        .id(id)
                        .nome(nome)
                        .senha(senha)
                        .email(email)
                        .build();
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Não foi possível buscar o usuário", e);
        }
        return null;
    }
}
