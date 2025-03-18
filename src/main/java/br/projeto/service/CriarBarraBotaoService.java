package br.projeto.service;

import java.util.Map;

import javax.swing.JButton;
import javax.swing.JToolBar;

import br.projeto.command.ProjetoCommand;
import br.projeto.view.LogConfigView;

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
        adicionarBotaoLog(toolBar);

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
    
    // Método para adicionar o botão de Log
    private void adicionarBotaoLog(JToolBar toolBar) {
        JButton botaoLog = new JButton("Log");
        botaoLog.setIcon(IconService.getIcon("log"));
        botaoLog.addActionListener(e -> {
            // Quando o botão "Log" for clicado, abre a tela de configuração de log
            new LogConfigView().setVisible(true);
        });
        toolBar.add(botaoLog);
    }
}
