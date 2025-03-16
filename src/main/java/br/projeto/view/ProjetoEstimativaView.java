/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package br.projeto.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import br.projeto.model.Funcionalidade;
import br.projeto.model.Perfil;
import br.projeto.model.Plataforma;
import br.projeto.presenter.CriarProjetoPresenter;
import br.projeto.presenter.ManterProjetoPresenter;
import br.projeto.view.components.EstimativaTable;
import br.projeto.view.components.EstimativaTableModel;

public class ProjetoEstimativaView extends JInternalFrame {

    private ManterProjetoPresenter presenter;

    private JComboBox<Perfil> comboPerfis;   // Combo de perfis

    private JLabel lblPlataformas;
    private JLabel lblQuantidadeDeDias;
    private JLabel lblQuantidadeMeses;
    private JLabel lblValorTotal;
    private JTextField txtNomeProjeto;
    private JTextField txtCustoHardware;
    private JTextField txtCustoSoftware;
    private JTextField txtCustoRiscos;
    private JTextField txtCustoGarantia;
    private JTextField txtFundoReserva;
    private JTextField txtOutrosCustos;
    private JTextField txtPercentualImposto;
    private JTextField txtPercentualLucro;
    private JLabel lblValorDoImposto;
    private JLabel lblValorComImposto;
    private JLabel lblValorDoLucro;
    private JLabel lblPrecoFinalDoCliente;
    private JTextArea txtFuncionalidades;
    private JButton btnSalvar;

    private EstimativaTable tabela;
    private EstimativaTableModel modeloTabela;

    public ProjetoEstimativaView() {
        // Configurações iniciais da tela
        setTitle("Criar Projeto");
        setClosable(true);
        setMaximizable(true);
        setIconifiable(false);
        setResizable(true);
        setSize(1000, 700);
    }

    public void setPresenter(ManterProjetoPresenter presenter) {
        this.presenter = presenter;
    }

    /**
     * Inicializa o combo de perfis na tela
     *
     * @param perfis
     */
    public void initComboPerfis(List<Perfil> perfis) {
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        // Painel superior
        JPanel painelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Label
        JLabel lblSelecionePerfil = new JLabel("Selecione o Perfil:");
        painelSuperior.add(lblSelecionePerfil);

        // Combo de perfis
        comboPerfis = new JComboBox<>();
        for (Perfil p : perfis) {
            comboPerfis.addItem(p);
        }

        comboPerfis.addActionListener(e -> {
            Perfil perfilSelecionado = (Perfil) comboPerfis.getSelectedItem();
            if (perfilSelecionado != null) {
                presenter.onPerfilSelecionado(perfilSelecionado);
            }
        });

        painelSuperior.add(comboPerfis);

        add(painelSuperior, BorderLayout.NORTH);

        // Exibe a janela
        revalidate();
        repaint();
    }

    /**
     * Inicializa os componentes da tela
     *
     * @param perfil
     * @param tableModel
     */
    public void initComponents(Perfil perfil, EstimativaTableModel tableModel) {
        this.modeloTabela = tableModel;

        // Pega o layout principal
        Container contentPane = getContentPane();
        contentPane.removeAll();

        // Painel principal
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        contentPane.add(painelPrincipal, BorderLayout.CENTER);

        // Painel de cabeçalho "principal"
        JPanel painelCabecalho = new JPanel();
        painelCabecalho.setBorder(BorderFactory.createTitledBorder("Informações da criação do projeto"));
        painelCabecalho.setLayout(new BorderLayout());

        JPanel painelTresColunas = new JPanel(new GridLayout(1, 2));
        painelCabecalho.add(painelTresColunas, BorderLayout.CENTER);

        JPanel painelEsquerda = new JPanel();
        painelEsquerda.setLayout(new BoxLayout(painelEsquerda, BoxLayout.Y_AXIS));

        // Label "Nome do Projeto"
        JPanel linha1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblNome = new JLabel("Nome do Projeto de estimativa:");
        linha1.add(lblNome);
        painelEsquerda.add(linha1);

        // JTextField do nome do projeto
        JPanel linha2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtNomeProjeto = new JTextField(20);
        linha2.add(txtNomeProjeto);
        painelEsquerda.add(linha2);

        // Label de plataformas selecionadas
        JPanel linha3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblPlataformas = new JLabel("Plataformas selecionadas:");
        linha3.add(lblPlataformas);
        painelEsquerda.add(linha3);

        // Label funcionalidades
        JPanel linha4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblFunc = new JLabel("Funcionalidades selecionadas:");
        linha4.add(lblFunc);
        painelEsquerda.add(linha4);

        // JTextArea + JScrollPane para exibir funcionalidades selecionadas
        JPanel linha5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtFuncionalidades = new JTextArea(5, 20);
        txtFuncionalidades.setEditable(false);
        txtFuncionalidades.setLineWrap(true);
        txtFuncionalidades.setWrapStyleWord(true);

        JScrollPane scrollFuncionalidades = new JScrollPane(
                txtFuncionalidades,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        scrollFuncionalidades.setPreferredSize(new Dimension(300, 100));
        linha5.add(scrollFuncionalidades);
        painelEsquerda.add(linha5);

        // Label para quantidade de dias totais
        JPanel linhaDias = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblQuantidadeDeDias = new JLabel("Dias totais: 0");
        linhaDias.add(lblQuantidadeDeDias);
        painelEsquerda.add(linhaDias);

        // Label para quantidade de dias totais
        JPanel linhaMeses = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblQuantidadeMeses = new JLabel("Meses: 0");
        linhaMeses.add(lblQuantidadeMeses);
        painelEsquerda.add(linhaMeses);

        // Label para valor total bruto
        JPanel linhaValorTotal = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblValorTotal = new JLabel("Valor total do projeto: R$ 0,00");
        linhaValorTotal.add(lblValorTotal);
        painelEsquerda.add(linhaValorTotal);

        // Label para valor do imposto
        JPanel linhaValorImposto = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblValorDoImposto = new JLabel("Valor imposto: R$ 0,00");
        linhaValorImposto.add(lblValorDoImposto);
        painelEsquerda.add(linhaValorImposto);

        // Label para valor do imposto
        JPanel linhaValorComIMposto = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblValorComImposto = new JLabel("Valor com imposto: R$ 0,00");
        linhaValorComIMposto.add(lblValorComImposto);
        painelEsquerda.add(linhaValorComIMposto);

        // Label para valor do lucro
        JPanel linhaValorLucro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblValorDoLucro = new JLabel("Lucro estimado: R$ 0,00");
        linhaValorLucro.add(lblValorDoLucro);
        painelEsquerda.add(linhaValorLucro);

        // Label para valor final do cliente
        JPanel linhaValorFinalCliente = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblPrecoFinalDoCliente = new JLabel("Valor final para o cliente: R$ 0,00");
        linhaValorFinalCliente.add(lblPrecoFinalDoCliente);
        painelEsquerda.add(linhaValorFinalCliente);

        // Adiciona o painel da coluna esquerda no GridLayout
        painelTresColunas.add(painelEsquerda);

        JPanel painelDireita = new JPanel();
        painelDireita.setLayout(new BoxLayout(painelDireita, BoxLayout.Y_AXIS));

        txtCustoHardware = new JTextField(20);
        montaLabelEField(painelDireita, "Custo com hardware:", txtCustoHardware);

        txtCustoRiscos = new JTextField(20);
        montaLabelEField(painelDireita, "Custo com riscos:", txtCustoRiscos);

        txtCustoSoftware = new JTextField(20);
        montaLabelEField(painelDireita, "Custo com software:", txtCustoSoftware);

        txtCustoGarantia = new JTextField(20);
        montaLabelEField(painelDireita, "Custo com garantia:", txtCustoGarantia);

        txtFundoReserva = new JTextField(20);
        montaLabelEField(painelDireita, "Fundo de reserva:", txtFundoReserva);

        txtOutrosCustos = new JTextField(20);
        montaLabelEField(painelDireita, "Outros custos:", txtOutrosCustos);

        txtPercentualImposto = new JTextField(20);
        montaLabelEField(painelDireita, "Percentual de imposto:", txtPercentualImposto);

        txtPercentualLucro = new JTextField(20);
        montaLabelEField(painelDireita, "Percentual de lucro:", txtPercentualLucro);

        btnSalvar = new JButton("Salvar Estimativa");
        btnSalvar.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

        painelDireita.add(Box.createVerticalGlue()); // Adiciona espaço flexível para empurrar o botão para baixo
        painelDireita.add(btnSalvar);

        // Adiciona o painel da coluna da direita no GridLayout
        painelTresColunas.add(painelDireita);

        // Adiciona o painel de cabeçalho no topo do painel principal
        painelPrincipal.add(painelCabecalho, BorderLayout.NORTH);

        // Painel central para a tabela
        JPanel panelCentral = new JPanel(new BorderLayout());
        tabela = new EstimativaTable(modeloTabela);

        JScrollPane scrollPane = new JScrollPane(tabela);
        panelCentral.add(scrollPane, BorderLayout.CENTER);

        painelPrincipal.add(panelCentral, BorderLayout.CENTER);

        // Se existir comboPerfis, adiciona no NORTH do contentPane
        if (comboPerfis != null) {
            contentPane.add(comboPerfis, BorderLayout.NORTH);
        }

        contentPane.revalidate();
        contentPane.repaint();
    }

    public String getNomeProjeto() {
        return txtNomeProjeto.getText().trim();
    }

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

    public void exibirEstimativa(double dias, double valorTotalProjeto, double valorImposto, double valorComImposto, double valorLucro, double valorFinalCliente, double meses) {

        DecimalFormat df = new DecimalFormat("R$ #,##0.00");

        lblQuantidadeDeDias.setText("Dias totais: " + dias);
        lblQuantidadeMeses.setText("Meses: " + meses);
        lblValorTotal.setText("Valor total: " + df.format(valorTotalProjeto));
        lblValorDoImposto.setText("Valor imposto: " + df.format(valorImposto));
        lblValorComImposto.setText("Valor com imposto: " + df.format(valorComImposto));
        lblValorDoLucro.setText("Lucro estimado: " + df.format(valorLucro));
        lblPrecoFinalDoCliente.setText("Valor final para o cliente: " + df.format(valorFinalCliente));
    }

    public JTextField getTxtNomeProjeto() {
        return txtNomeProjeto;
    }

    public JTextField getTxtCustoHardware() {
        return txtCustoHardware;
    }

    public JTextField getTxtCustoRiscos() {
        return txtCustoRiscos;
    }

    public JTextField getTxtCustoSoftware() {
        return txtCustoSoftware;
    }

    public JTextField getTxtCustoGarantia() {
        return txtCustoGarantia;
    }

    public JTextField getTxtFundoReserva() {
        return txtFundoReserva;
    }

    public JTextField getTxtOutrosCustos() {
        return txtOutrosCustos;
    }

    public JTextField getTxtPercentualImposto() {
        return txtPercentualImposto;
    }

    public JTextField getTxtPercentualLucro() {
        return txtPercentualLucro;
    }

    public JButton getBntSalvar() {
        return btnSalvar;
    }

    private void montaLabelEField(JPanel parent, String labelText, JTextField textField) {
        // Cria um painel container com layout vertical (BoxLayout)
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        // Cria o JLabel e define o alinhamento à esquerda
        JLabel label = new JLabel(labelText);
        label.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        container.add(label);

        // Espaçamento opcional entre o label e o campo
        container.add(Box.createVerticalStrut(5));

        // Configura o JTextField para ter tamanho fixo e evitar que se expanda
        Dimension tamanho = new Dimension(250, textField.getPreferredSize().height);
        textField.setPreferredSize(tamanho);
        textField.setMaximumSize(tamanho);
        textField.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT); // Alinha o campo à esquerda

        // Configura o documento para restrição de números
        textField.setDocument(regraDeApenasNumeros());

        // Adiciona o JTextField ao container
        container.add(textField);

        // Adiciona o container ao painel pai
        parent.add(container);

        // Adiciona o listener para atualizar a view conforme a entrada do usuário
        textField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                presenter.notificaView(presenter.processaEstimativa());
            }
        });
    }

    private PlainDocument regraDeApenasNumeros() {
        return new PlainDocument() {
            @Override
            public void insertString(int offs, String str, javax.swing.text.AttributeSet a) throws BadLocationException {
                if (str == null) {
                    return;
                }
                for (char c : str.toCharArray()) {
                    if (!Character.isDigit(c)) {
                        JOptionPane.showMessageDialog(null, "Apenas números são permitidos");
                        return;
                    }
                }
                super.insertString(offs, str, a);
            }
        };
    }
}
