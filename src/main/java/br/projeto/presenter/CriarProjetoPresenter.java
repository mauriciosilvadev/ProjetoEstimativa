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
import br.projeto.service.ValidaFuncionalidadeService;
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

    // Podemos expor o TableModel, caso seja útil para acessar dados nele
    private final EstimativaTableModel tableModel;

    private Perfil perfil;

    public CriarProjetoPresenter(CriarProjetoView view,
            PerfilRepository repository) {
        this.view = view;
        this.repository = repository;
        this.estimativaService = new EstimativaService();

        // Carrega o Perfil (exemplo fixo: id=1)
        this.perfil = repository.buscarPorId(1);

        // Cria o TableModel passando o Perfil
        // e 'this' para que possamos notificar o Presenter quando algo mudar
        this.tableModel = new EstimativaTableModel(perfil, this);

        // Configura a view
        this.view.setPresenter(this);
        this.view.initComponents(perfil, tableModel);
    }

    /**
     * Chamado quando o TableModel detecta mudanças (marcar/desmarcar
     * funcionalidades/plataformas). Aqui recalculamos a estimativa e
     * atualizamos a View.
     */
    public void onTableDataChanged() {
        // Coletamos as funcionalidades e plataformas selecionadas do TableModel
        List<Funcionalidade> funcsSelecionadas = tableModel.getFuncionalidadesSelecionadas();
        List<Plataforma> platsSelecionadas = tableModel.getPlataformasSelecionadas();

        // 1) Valida se não há conflito
        try {
            ValidaFuncionalidadeService.getInstance().validaFuncionalidades(funcsSelecionadas);
        } catch (IllegalArgumentException e) {
            // A validação interna já exibe um JOptionPane no TableModel, mas poderíamos tratar aqui também.
            // Se quisermos abortar o fluxo, poderíamos desfazer a seleção. 
            // Neste exemplo, assumo que a mensagem já foi exibida, então apenas retorno.
            return;
        }

        // 2) Calcula estimativa
        EstimativaResultado resultado = estimativaService.calcularEstimativa(platsSelecionadas, funcsSelecionadas);

        // 3) Pede para a View mostrar as plataformas/funcionalidades selecionadas e os valores
        view.exibirItensSelecionados(platsSelecionadas, funcsSelecionadas);
        view.exibirEstimativa(resultado.getTotalDias(), resultado.getValorTotal());
    }

    /**
     * Poderíamos ter um método para "salvar" o projeto de estimativa, usando o
     * nome digitado no campo, a lista de funcionalidades/plataformas
     * selecionadas etc.
     */
    public void salvarProjetoEstimativa() {
        String nomeProjeto = view.getNomeProjeto();
        // Lógica de persistência, se necessário...
        // ...
    }
}
