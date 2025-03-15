package br.projeto.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DetalheProjetoView extends JInternalFrame {

    private JLabel lblNome, lblCriador, lblData, lblPerfilProjeto, lblStatus, lblValorTotal, lblPlataformas, lblTotal, lblTotalDias, lblTotalCusto, lblImposto, lblTotalComImposto, lblLucro, lblTotalComLucro, lblMeses, lblMediaMensal;
    private JTable tabelaDetalhes;
    private DefaultTableModel modeloTabela;

    public DetalheProjetoView() {
        setTitle("Detalhes do Projeto");
        setClosable(true);
        setMaximizable(true);
        setIconifiable(false);
        setResizable(true);
        setSize(1000, 700);

        JPanel painelPrincipal = new JPanel(new BorderLayout());
        add(painelPrincipal);

        JPanel painelCabecalho = new JPanel();
        painelCabecalho.setLayout(new GridLayout(5, 2, 10, 15));
        painelCabecalho.setBorder(BorderFactory.createTitledBorder("Informações do Projeto"));

        lblNome = new JLabel();
        lblNome.setFont(new Font("Arial", Font.BOLD, 13));

        lblCriador = new JLabel();
        lblCriador.setFont(new Font("Arial", Font.BOLD, 13));

        lblData = new JLabel();
        lblData.setFont(new Font("Arial", Font.BOLD, 13));

        lblPerfilProjeto = new JLabel();
        lblPerfilProjeto.setFont(new Font("Arial", Font.BOLD, 13));

        lblStatus = new JLabel();
        lblStatus.setFont(new Font("Arial", Font.BOLD, 13));

        lblValorTotal = new JLabel();
        lblValorTotal.setFont(new Font("Arial", Font.BOLD, 13));

        lblPlataformas = new JLabel();
        lblPlataformas.setFont(new Font("Arial", Font.BOLD, 13));

        lblTotal = new JLabel();
        lblTotal.setFont(new Font("Arial", Font.BOLD, 13));

        lblTotalDias = new JLabel();
        lblTotalDias.setFont(new Font("Arial", Font.BOLD, 13));

        lblTotalCusto = new JLabel();
        lblTotalCusto.setFont(new Font("Arial", Font.BOLD, 13));

        lblImposto = new JLabel();
        lblImposto.setFont(new Font("Arial", Font.BOLD, 13));

        lblTotalComImposto = new JLabel();
        lblTotalComImposto.setFont(new Font("Arial", Font.BOLD, 13));

        lblLucro = new JLabel();
        lblLucro.setFont(new Font("Arial", Font.BOLD, 13));

        lblTotalComLucro = new JLabel();
        lblTotalComLucro.setFont(new Font("Arial", Font.BOLD, 13));

        lblMeses = new JLabel();
        lblMeses.setFont(new Font("Arial", Font.BOLD, 13));

        lblMediaMensal = new JLabel();
        lblMediaMensal.setFont(new Font("Arial", Font.BOLD, 13));

        painelCabecalho.add(lblNome);
        painelCabecalho.add(lblCriador);
        painelCabecalho.add(lblData);
        painelCabecalho.add(lblPerfilProjeto);
        painelCabecalho.add(lblPlataformas);
        painelCabecalho.add(lblTotal);
        painelCabecalho.add(lblTotalDias);
        painelCabecalho.add(lblTotalCusto);
        painelCabecalho.add(lblImposto);
        painelCabecalho.add(lblTotalComImposto);
        painelCabecalho.add(lblLucro);
        painelCabecalho.add(lblTotalComLucro);
        painelCabecalho.add(lblMeses);
        painelCabecalho.add(lblMediaMensal);
        painelCabecalho.add(lblStatus);

        painelPrincipal.add(painelCabecalho, BorderLayout.NORTH);

        JPanel painelTabela = new JPanel(new BorderLayout());
        painelTabela.setBorder(BorderFactory.createTitledBorder("Funcionalidades do Projeto"));

        modeloTabela = new DefaultTableModel(new Object[]{"Nome funcionalidade", "Valores por plataforma"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaDetalhes = new JTable(modeloTabela);
        tabelaDetalhes.setFillsViewportHeight(true);

        JScrollPane scrollTabela = new JScrollPane(tabelaDetalhes);
        painelTabela.add(scrollTabela, BorderLayout.CENTER);

        JPanel painelValorTotal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblValorTotal = new JLabel("Valor Final do Cliente: R$ 0,00");
        lblValorTotal.setFont(new Font("Arial", Font.BOLD, 14));
        painelValorTotal.add(lblValorTotal);

        painelTabela.add(painelValorTotal, BorderLayout.SOUTH);
        painelPrincipal.add(painelTabela, BorderLayout.CENTER);
    }

    public JLabel getLblNome() {
        return lblNome;
    }

    public JLabel getLblCriador() {
        return lblCriador;
    }

    public JLabel getLblData() {
        return lblData;
    }

    public JLabel getLblPerfilProjeto() {
        return lblPerfilProjeto;
    }

    public JLabel getLblStatus() {
        return lblStatus;
    }

    public JLabel getLblValorTotal() {
        return lblValorTotal;
    }

    public JLabel getLblPlataformas() {
        return lblPlataformas;
    }

    public JLabel getLblTotal() {
        return lblTotal;
    }

    public JLabel getLblTotalDias() {
        return lblTotalDias;
    }

    public JLabel getLblTotalCusto() {
        return lblTotalCusto;
    }

    public JLabel getLblImposto() {
        return lblImposto;
    }

    public JLabel getLblTotalComImposto() {
        return lblTotalComImposto;
    }

    public JLabel getLblLucro() {
        return lblLucro;
    }

    public JLabel getLblTotalComLucro() {
        return lblTotalComLucro;
    }

    public JLabel getLblMeses() {
        return lblMeses;
    }

    public JLabel getLblMediaMensal() {
        return lblMediaMensal;
    }

    public void atualizarTabela(Object[][] dados, double valorTotal) {
        modeloTabela.setRowCount(0);
        for (Object[] linha : dados) {
            modeloTabela.addRow(linha);
        }
    }
}
