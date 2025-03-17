package br.projeto.presenter.window_command;


import br.projeto.command.MostrarMensagemProjetoCommand;
import br.projeto.command.ProjetoCommand;
import br.projeto.presenter.PrincipalPresenter;
import br.projeto.service.CriarBarraBotaoService;
import br.projeto.service.CriarBotaoPowerService;
import java.awt.BorderLayout;

import javax.swing.*;

public class ConfigurarViewCommand implements WindowCommand {
    private final PrincipalPresenter presenter;

    public ConfigurarViewCommand(PrincipalPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void execute() {
        presenter.configurarArvore();
        JPanel painelSuperior = new JPanel(new BorderLayout());
        JButton botaoSair = new CriarBotaoPowerService(presenter.getComandos()).getPower();
        JToolBar barraDeBotoes = new CriarBarraBotaoService(presenter.getComandos()).criarBarraDeBotoes();
        painelSuperior.add(barraDeBotoes, BorderLayout.WEST);
        painelSuperior.add(botaoSair, BorderLayout.EAST);

        presenter.getView().setMainComponents(painelSuperior);
        SwingUtilities.invokeLater(() -> {
            ProjetoCommand comandoPrincipal = presenter.getComandos().get("Principal");
            if (comandoPrincipal != null) {
                comandoPrincipal.execute();
            } else {
                new MostrarMensagemProjetoCommand("Comando 'Principal' não encontrado.").execute();
            }
        });
    }
}
