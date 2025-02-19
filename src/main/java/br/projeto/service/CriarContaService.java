package br.projeto.service;

import br.projeto.model.Usuario;
import br.projeto.repository.UsuarioRepository;
import com.pss.senha.validacao.ValidadorSenha;

import java.util.Collections;
import java.util.List;

public class CriarContaService {
    private final UsuarioRepository repository;

    public CriarContaService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public void criarConta(String nome, String email, String senha) throws IllegalArgumentException {

        Usuario usuario = new Usuario.Builder()
                .nome(nome)
                .senha(senha)
                .email(email)
                .build();

        repository.buscarPorEmail(email);
        if (repository.buscarPorEmail(email) != null) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        ValidadorSenha validadorSenha = new ValidadorSenha();

        List<String> erros = validadorSenha.validar(senha);
        if (!erros.isEmpty()) {
            String mensagemErro = String.join("\n", erros);
            throw new IllegalArgumentException("Senha inválida:\n" + mensagemErro);
        }

        repository.inserir(usuario);

    }


}
