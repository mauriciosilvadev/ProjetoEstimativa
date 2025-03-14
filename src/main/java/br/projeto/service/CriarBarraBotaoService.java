package br.projeto.service;

import java.util.Map;

import javax.swing.JButton;
import javax.swing.JToolBar;

import br.projeto.command.ProjetoCommand;
import br.projeto.session.UsuarioSession;

public class CriarBarraBotaoService {

    private final Map<String, ProjetoCommand> comandos;

    public CriarBarraBotaoService(Map<String, ProjetoCommand> comandos) {
        this.comandos = comandos;
    }

    public JToolBar criarBarraDeBotoes() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        adicionarBotao(toolBar, "Dashboard", "principal", "Principal");
        adicionarBotao(toolBar, "Novo Projeto", "projeto", "Novo projeto");
        adicionarBotao(toolBar, UsuarioSession.getInstance().getUsuarioLogado().getNome(), "usuario", "Usuário");

        return toolBar;
    }

    private void adicionarBotao(JToolBar toolBar, String texto, String iconeKey, String comandoChave) {
        JButton botao = new JButton(texto);
        botao.setIcon(IconService.getIcon(iconeKey));
        botao.addActionListener(e -> {
            ProjetoCommand comando = comandos.get(comandoChave);
            if (comando == null) {
                throw new IllegalArgumentException("Comando não encontrado: " + comandoChave);
            }
            comando.execute();
        });
        toolBar.add(botao);
    }
}
