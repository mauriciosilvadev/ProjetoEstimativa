/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.projeto.view;

import javax.swing.*;
import br.projeto.adapter.LoggerAdapterImpl;

/**
 *
 * @author Romerson
 */
public class LogConfigView extends JFrame {
    private JComboBox<String> comboBoxLogType;
    private JTextField txtFilePath;
    private JButton btnSalvar;

    public LogConfigView() {
        setTitle("Configuração de Log");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Elementos da interface
        comboBoxLogType = new JComboBox<>(new String[]{"CSV", "JSON"});
        txtFilePath = new JTextField(20);
        btnSalvar = new JButton("Salvar");

        // Layout
        JPanel panel = new JPanel();
        panel.add(new JLabel("Tipo de Log:"));
        panel.add(comboBoxLogType);
        panel.add(new JLabel("Caminho do Arquivo:"));
        panel.add(txtFilePath);
        panel.add(btnSalvar);

        add(panel);

        // Ação do botão salvar
        btnSalvar.addActionListener(e -> salvarConfiguracao());
    }

    private void salvarConfiguracao() {
        // Obtém as informações do tipo de log e do caminho do arquivo
        String logType = (String) comboBoxLogType.getSelectedItem();
        String filePath = txtFilePath.getText();

        // Configura o LoggerAdapter com as informações fornecidas
        LoggerAdapterImpl logger = new LoggerAdapterImpl.Builder()
                .logType(logType)
                .build();

        

        // Fecha a janela de configuração
        dispose();
    }
}
