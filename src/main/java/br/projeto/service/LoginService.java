package br.projeto.service;

import java.util.Optional;

import br.projeto.adapter.LoggerAdapter;
import br.projeto.adapter.LoggerAdapterImpl;
import br.projeto.model.Usuario;
import br.projeto.repository.UsuarioRepository;
import br.projeto.session.UsuarioSession;

public class LoginService {

    private final UsuarioRepository repository;

    public LoginService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public Boolean autenticar(String email, String senha) {
        Usuario usuario = repository.buscarPorEmail(email);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        if (!usuario.getSenha().equals(senha)) {
            throw new IllegalArgumentException("Senha inválida");
        }

        UsuarioSession.getInstance().setUsuarioLogado(usuario);

        LoggerAdapterImpl.getInstance().log("Login", "Usuário autenticado: " + usuario.getNome());

        return Optional.ofNullable(usuario.getNome()).isPresent();
    }
}
