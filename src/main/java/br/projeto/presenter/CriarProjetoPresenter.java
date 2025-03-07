/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.projeto.presenter;

import java.util.List;

import br.projeto.model.EstimativaResultado;
import br.projeto.model.Funcionalidade;
import br.projeto.model.Perfil;
import br.projeto.model.Plataforma;
import br.projeto.repository.PerfilRepository;
import br.projeto.service.EstimativaService;
import br.projeto.view.CriarProjetoView;
import br.projeto.view.components.EstimativaTableModel;

/**
 *
 * @author Maurício Silva <mauricio.s.dev@gmail.com>
 */
public class CriarProjetoPresenter {

    private final CriarProjetoView view;
    private final PerfilRepository repository;
    private final EstimativaService estimativaService;

    private final EstimativaTableModel tableModel;

    private Perfil perfil;

    public CriarProjetoPresenter(CriarProjetoView view,
            PerfilRepository repository) {
        this.view = view;
        this.repository = repository;
        this.estimativaService = new EstimativaService();

        // Carregar Perfil
        this.perfil = repository.buscarPorId(1);

        // Criar TableModel passando o Perfil passando o this para que o presenter possa ser notificado caso algo aconteça
        this.tableModel = new EstimativaTableModel(perfil, this);

        // Configura view
        this.view.setPresenter(this);
        this.view.initComponents(perfil, tableModel);
    }

    /**
     * Chamado quando algo na tabela muda (funcionalidades, plataformas ou valor
     * das intercesões da funcionalidade x plataforma)
     */
    public void onTableDataChanged() {
        List<Funcionalidade> funcsSelecionadas = tableModel.getFuncionalidadesSelecionadas();
        List<Plataforma> platsSelecionadas = tableModel.getPlataformasSelecionadas();

        // Calcular estimativa
        EstimativaResultado resultado = estimativaService.calcularEstimativa(platsSelecionadas, funcsSelecionadas);

        // Mostrar valores na view
        view.exibirItensSelecionados(platsSelecionadas, funcsSelecionadas);
        view.exibirEstimativa(resultado.getTotalDias(), resultado.getValorTotal());
    }

    /**
     * Método salvar (ainda não implementado).
     */
    public void salvarProjetoEstimativa() {
        String nomeProjeto = view.getNomeProjeto();
    }
}
