package br.projeto.service;

import br.projeto.model.Usuario;
import br.projeto.repository.UsuarioRepository;

import java.util.Optional;

public class LoginService {
    private final UsuarioRepository repository;

    public LoginService(UsuarioRepository repository) {
        this.repository = repository;
    }


    // Criar um chain of responsibility para autenticar
    public Boolean autenticar(String email, String senha) {

        Usuario usuario = repository.buscarPorEmail(email);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        if (!usuario.getSenha().equals(senha)) {
            throw new IllegalArgumentException("Senha inválida");
        }
        return Optional.ofNullable(usuario.getNome()).isPresent();
    }


}
