/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.projeto.presenter;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import br.projeto.model.ProjetoEstimativa;
import br.projeto.model.Usuario;
import br.projeto.repository.ProjetoRepository;
import br.projeto.repository.UsuarioRepository;
import br.projeto.view.CompartilharProjetoEstimativaView;

public class CompartilharProjetoEstimativaPresenter {

    private final CompartilharProjetoEstimativaView view;
    private final UsuarioRepository usuarioRepository;
    private final ProjetoRepository projetoRepository;
    private final ProjetoEstimativa projeto;

    public CompartilharProjetoEstimativaPresenter(
            CompartilharProjetoEstimativaView view,
            UsuarioRepository usuarioRepository,
            ProjetoRepository projetoRepository,
            ProjetoEstimativa projeto
    ) {
        this.view = view;
        this.usuarioRepository = usuarioRepository;
        this.projetoRepository = projetoRepository;
        this.projeto = projeto;

        configurarTela();
        initListeners();

        view.setVisible(true);
    }

    /**
     * Configura a tela inicial
     */
    private void configurarTela() {
        view.getLblProjetoCompartilhado().setText("Projeto: " + projeto.getNome());
    }

    /**
     * Registra os listeners para os botões
     */
    private void initListeners() {
        view.getBtnPesquisar().addActionListener(e -> pesquisarUsuarios());
        view.getBtnCompartilhar().addActionListener(e -> compartilharProjeto());
    }

    /**
     * Faz a pesquisa dos usuários no repositório
     */
    private void pesquisarUsuarios() {
        String textoPesquisa = view.getTxtUsuarioPesquisado().getText().trim();

        List<Usuario> listaDeUsuarios = usuarioRepository.buscarUsuariosByNome(textoPesquisa);

        if (listaDeUsuarios.isEmpty()) {
            JOptionPane.showMessageDialog(view,
                    "Nenhum usuário encontrado.",
                    "Aviso",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        // Atualiza a tabela
        DefaultTableModel model = (DefaultTableModel) view.getTblUsuarios().getModel();
        model.setRowCount(0); // limpa linhas existentes

        for (Usuario u : listaDeUsuarios) {
            Object[] linha = {u.getId(), u.getNome(), u.getEmail()};
            model.addRow(linha);
        }
    }

    /**
     * Compartilha o projeto selecionado com o usuário selecionado na tabela.
     */
    private void compartilharProjeto() {
        int rowIndex = view.getTblUsuarios().getSelectedRow();

        if (rowIndex < 0) {
            JOptionPane.showMessageDialog(view,
                    "Selecione um usuário para compartilhar.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int usuarioId = (int) view.getTblUsuarios().getValueAt(rowIndex, 0);
        String usuarioNome = (String) view.getTblUsuarios().getValueAt(rowIndex, 1);

        if (projetoRepository.compartilharProjeto(projeto.getId(), usuarioId)) {
            JOptionPane.showMessageDialog(view,
                    "Projeto '" + projeto + "' compartilhado com o usuário '" + usuarioNome + "'",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(view,
                    "Projeto já compartilhado com o usuário '" + usuarioNome + "'",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
