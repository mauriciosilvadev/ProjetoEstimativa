package br.projeto.repository;

import java.util.List;

import br.projeto.model.Usuario;

public interface UsuarioRepository {

    Usuario inserir(String nome, String email, String senha);

    void atualizar(Usuario usuario, int id);

    void deletar(int id);

    List<Usuario> listar();

    List<Usuario> buscarUsuariosByNome(String nomePesquisado);

    Usuario buscarPorEmail(String email);

    Usuario buscarPorId(int id);
}
