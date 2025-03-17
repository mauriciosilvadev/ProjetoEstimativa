/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.projeto.state.projetoEstimativa;

import javax.swing.JOptionPane;

import br.projeto.model.ProjetoEstimativa;
import br.projeto.repository.ProjetoRepository;
import br.projeto.view.ProjetoEstimativaView;

/**
 *
 * @author Maurício Silva <mauricio.s.dev@gmail.com>
 */
public class CriarProjetoState implements ProjetoState {

    private final ProjetoRepository projetoRepository;
    private final ProjetoEstimativaView view;

    public CriarProjetoState(ProjetoRepository projetoRepository, ProjetoEstimativaView view) {
        this.projetoRepository = projetoRepository;
        this.view = view;
    }

    @Override
    public void salvarProjeto(ProjetoEstimativa estimativa) {
        try {
            projetoRepository.adicionarProjeto(estimativa);
            JOptionPane.showMessageDialog(view, "Projeto salvo com sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            view.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao salvar projeto: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
