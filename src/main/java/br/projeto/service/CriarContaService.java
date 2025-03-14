package br.projeto.service;

import java.util.List;

import com.pss.senha.validacao.ValidadorSenha;

import br.projeto.model.Usuario;
import br.projeto.repository.UsuarioRepository;
import br.projeto.session.UsuarioSession;

public class CriarContaService {

    private final UsuarioRepository repository;

    public CriarContaService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public void criarConta(String nome, String email, String senha) throws IllegalArgumentException {

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

        Usuario usuario = repository.inserir(nome, email, senha);

        UsuarioSession.getInstance().setUsuarioLogado(usuario);
    }

}
