/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.projeto.service;

import br.projeto.command.ProjetoCommand;
import java.util.Map;
import javax.swing.JButton;

/**
 *
 * @author Usuário
 */
public class CriarBotaoPowerService {
    public static JButton power;
    public CriarBotaoPowerService(Map<String, ProjetoCommand> comandos) {
        power = new JButton("Sair");
        power.setIcon(IconService.getIcon("power"));
        power.addActionListener(e -> {
            ProjetoCommand comando = comandos.get("Sair");
            if (comando == null) {
                throw new IllegalArgumentException("Comando não encontrado: " + "Sair");
            }
            comando.execute();
        });
    }
    public JButton getPower(){
        return power;
    }
}
