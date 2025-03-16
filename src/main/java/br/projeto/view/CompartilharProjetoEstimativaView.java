/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package br.projeto.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * Tela para Compartilhar Projeto.
 */
public class CompartilharProjetoEstimativaView extends JInternalFrame {

    private JTextField txtUsuarioPesquisado;
    private JButton btnPesquisar;
    private JTable tblUsuarios;
    private JLabel lblProjetoCompartilhado;
    private JButton btnCompartilhar;

    public CompartilharProjetoEstimativaView() {
        setTitle("Compartilhar Projeto");
        setClosable(true);
        setMaximizable(true);
        setIconifiable(false);
        setResizable(true);
        setSize(1000, 700);

        JPanel painelPrincipal = new JPanel(new BorderLayout());
        add(painelPrincipal);

        // Painel superior para busca de usuários
        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblPesquisarUsuario = new JLabel("Usuário:");
        txtUsuarioPesquisado = new JTextField(20);
        btnPesquisar = new JButton("Pesquisar");

        painelBusca.add(lblPesquisarUsuario);
        painelBusca.add(txtUsuarioPesquisado);
        painelBusca.add(btnPesquisar);

        // Painel central para a tabela
        tblUsuarios = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Nome", "E-mail"}
        ));
        JScrollPane scrollTabela = new JScrollPane(tblUsuarios);

        // Painel inferior para seleção de projeto e botão de compartilhar
        JPanel painelCompartilhar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel lblProjeto = new JLabel("Projeto:");
        lblProjetoCompartilhado = new JLabel();
        btnCompartilhar = new JButton("Compartilhar");

        painelCompartilhar.add(lblProjeto);
        painelCompartilhar.add(lblProjetoCompartilhado);
        painelCompartilhar.add(btnCompartilhar);

        // Adiciona os painéis ao painel principal
        painelPrincipal.add(painelBusca, BorderLayout.NORTH);
        painelPrincipal.add(scrollTabela, BorderLayout.CENTER);
        painelPrincipal.add(painelCompartilhar, BorderLayout.SOUTH);
    }

    // GETTERS para uso na Presenter
    public JTextField getTxtUsuarioPesquisado() {
        return txtUsuarioPesquisado;
    }

    public JButton getBtnPesquisar() {
        return btnPesquisar;
    }

    public JTable getTblUsuarios() {
        return tblUsuarios;
    }

    public JLabel getLblProjetoCompartilhado() {
        return lblProjetoCompartilhado;
    }

    public JButton getBtnCompartilhar() {
        return btnCompartilhar;
    }
}
