/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package br.projeto.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import br.projeto.model.Funcionalidade;
import br.projeto.model.Perfil;
import br.projeto.model.Plataforma;
import br.projeto.presenter.CriarProjetoPresenter;
import br.projeto.view.components.EstimativaTable;
import br.projeto.view.components.EstimativaTableModel;

public class CriarProjetoView extends JInternalFrame {

    private CriarProjetoPresenter presenter;

    private JLabel lblPlataformas;
    private JLabel lblQuantidadeDeDias;
    private JLabel lblValorTotal;
    private JTextField txtNomeProjeto;
    private JTextArea txtFuncionalidades;

    private EstimativaTable tabela;
    private EstimativaTableModel modeloTabela;

    public CriarProjetoView() {
        // Configurações iniciais da tela
        setTitle("Criar Projeto");
        setClosable(true);
        setMaximizable(true);
        setIconifiable(false);
        setResizable(true);
        setSize(1000, 700);
    }

    /**
     * Chamado pelo Presenter para injetar dependências e configurar os
     * componentes na tela.
     */
    public void initComponents(Perfil perfil, EstimativaTableModel tableModel) {
        this.modeloTabela = tableModel;

        // Painel principal
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        add(painelPrincipal);

        // Painel de cabeçalho
        JPanel painelCabecalho = new JPanel();
        painelCabecalho.setBorder(BorderFactory.createTitledBorder("Informações da criação do projeto"));
        painelCabecalho.setLayout(new BoxLayout(painelCabecalho, BoxLayout.Y_AXIS));

        // Linha 1: Label "Nome do Projeto"
        JPanel linha1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblNome = new JLabel("Nome do Projeto de estimativa:");
        linha1.add(lblNome);
        painelCabecalho.add(linha1);

        // Linha 2: JTextField do nome do projeto
        JPanel linha2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtNomeProjeto = new JTextField(20);
        linha2.add(txtNomeProjeto);
        painelCabecalho.add(linha2);

        // Linha 3: Label de plataformas selecionadas
        JPanel linha3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblPlataformas = new JLabel("Plataformas selecionadas:");
        linha3.add(lblPlataformas);
        painelCabecalho.add(linha3);

        // Linha 4: Label funcionalidades
        JPanel linha4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblFunc = new JLabel("Funcionalidades selecionadas:");
        linha4.add(lblFunc);
        painelCabecalho.add(linha4);

        // Linha 5: JTextArea + JScrollPane para exibir funcionalidades selecionadas
        JPanel linha5 = new JPanel(new FlowLayout(FlowLayout.LEFT));

        txtFuncionalidades = new JTextArea(5, 40);
        txtFuncionalidades.setEditable(false);
        txtFuncionalidades.setLineWrap(true);
        txtFuncionalidades.setWrapStyleWord(true);

        JScrollPane scrollFuncionalidades = new JScrollPane(
                txtFuncionalidades,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        scrollFuncionalidades.setPreferredSize(new Dimension(600, 100));

        linha5.add(scrollFuncionalidades);
        painelCabecalho.add(linha5);

        // Linha 6: Label para quantidade de dias totais
        JPanel linha6 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblQuantidadeDeDias = new JLabel("Dias totais: 0");
        linha6.add(lblQuantidadeDeDias);
        painelCabecalho.add(linha6);

        // Linha 7: Label para valor total do projeto
        JPanel linha7 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblValorTotal = new JLabel("Valor total do projeto: R$ 0,00");
        linha7.add(lblValorTotal);
        painelCabecalho.add(linha7);

        // Adiciona o painel de cabeçalho no topo do painel principal
        painelPrincipal.add(painelCabecalho, BorderLayout.NORTH);

        // Painel central para a tabela
        JPanel panelCentral = new JPanel(new BorderLayout());
        tabela = new EstimativaTable(modeloTabela);

        JScrollPane scrollPane = new JScrollPane(tabela);
        panelCentral.add(scrollPane, BorderLayout.CENTER);

        painelPrincipal.add(panelCentral, BorderLayout.CENTER);
    }

    public void setPresenter(CriarProjetoPresenter presenter) {
        this.presenter = presenter;
    }

    public String getNomeProjeto() {
        return txtNomeProjeto.getText().trim();
    }

    /**
     * Exibe na tela as plataformas e funcionalidades selecionadas.
     */
    public void exibirItensSelecionados(List<Plataforma> plataformasSelecionadas,
            List<Funcionalidade> funcionalidadesSelecionadas) {
        // Monta String de plataformas
        StringBuilder plataformasStr = new StringBuilder();
        for (Plataforma plat : plataformasSelecionadas) {
            plataformasStr.append("- ").append(plat.getNome()).append("\n");
        }
        lblPlataformas.setText("Plataformas selecionadas:\n" + plataformasStr);

        // Monta String de funcionalidades
        StringBuilder funcionalidadesStr = new StringBuilder();
        for (Funcionalidade func : funcionalidadesSelecionadas) {
            funcionalidadesStr.append("- ")
                    .append(func.getNome())
                    .append(" [")
                    .append(func.getCategoria().getNome())
                    .append("]\n");
        }
        txtFuncionalidades.setText(funcionalidadesStr.toString());
        txtFuncionalidades.setCaretPosition(0); // Volta scroll para topo
    }

    /**
     * Exibe na tela o total de dias e valor total.
     */
    public void exibirEstimativa(double dias, double valorTotalProjeto) {
        lblQuantidadeDeDias.setText("Dias totais: " + dias);

        DecimalFormat df = new DecimalFormat("R$ #,##0.00");
        lblValorTotal.setText("Valor total: " + df.format(valorTotalProjeto));
    }
}
