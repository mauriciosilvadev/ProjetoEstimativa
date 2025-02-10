package br.projeto.command;

import br.projeto.model.Projeto;
import br.projeto.repository.ProjetoRepositoryImpl;
import br.projeto.service.CriarProjetoMock;

import javax.swing.*;
import java.util.Optional;

public class CriarProjetoProjetoCommand implements ProjetoCommand {
    private final ProjetoRepositoryImpl repository;
    private final JDesktopPane desktop;
    private final CriarProjetoMock criarProjetoMock;

    public CriarProjetoProjetoCommand(ProjetoRepositoryImpl repository, JDesktopPane desktop) {
        this.repository = repository;
        this.desktop = desktop;
        this.criarProjetoMock = new CriarProjetoMock(repository);
    }

    @Override
    public void execute() {
        Optional<Projeto> projetoCriado = criarProjetoMock.criarProjetoAleatorio();

        projetoCriado.ifPresentOrElse(
                projeto -> {
                    repository.adicionarProjeto(
                            projeto.getNome(),
                            projeto.getCriador(),
                            projeto.getDataCriacao(),
                            projeto.getStatus(),
                            projeto.isCompartilhado(),
                            projeto.getCompartilhadoPor(),
                            projeto.getPerfis(),
                            projeto.getFuncionalidadesEscolhidas()
                    );
                    new MostrarMensagemProjetoCommand("Projeto \"" + projeto.getNome() + "\" criado com sucesso!").execute();
                },
                () -> new MostrarMensagemProjetoCommand("Falha ao criar o projeto.").execute());
    }
}
