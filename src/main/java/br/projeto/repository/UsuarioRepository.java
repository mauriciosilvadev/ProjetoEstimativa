package br.projeto.repository;

import br.projeto.model.Usuario;

import java.util.List;

public interface UsuarioRepository {
    void inserir(Usuario usuario);

    void atualizar(Usuario usuario, int id);

    void deletar(int id);

    List<Usuario> listar();

    Usuario buscarPorEmail(String email);
}
