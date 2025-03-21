/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.projeto.presenter;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import br.projeto.model.Funcionalidade;
import br.projeto.model.Perfil;
import br.projeto.model.Plataforma;
import br.projeto.model.ProjetoEstimativa;
import br.projeto.repository.PerfilRepository;
import br.projeto.repository.ProjetoRepository;
import br.projeto.service.EstimativaService;
import br.projeto.session.UsuarioSession;
import br.projeto.state.projetoEstimativa.CriarProjetoState;
import br.projeto.state.projetoEstimativa.ProjetoState;
import br.projeto.view.ProjetoEstimativaView;
import br.projeto.view.components.EstimativaTableModel;

/**
 *
 * @author Maurício Silva <mauricio.s.dev@gmail.com>
 */
public class CriarProjetoPresenter implements ManterProjetoPresenter {

    private final ProjetoEstimativaView view;
    private final PerfilRepository perfilRepository;
    private final ProjetoRepository projetoRepository;
    private final EstimativaService estimativaService;
    private EstimativaTableModel tableModel;
    private List<Perfil> perfis;
    private Perfil perfilAtual;
    private ProjetoEstimativa resultado;

    // Campo para o estado atual (criação)
    private ProjetoState state;

    public CriarProjetoPresenter(ProjetoEstimativaView view,
            PerfilRepository perfilRepository,
            ProjetoRepository projetoRepository) {
        this.view = view;
        this.perfilRepository = perfilRepository;
        this.projetoRepository = projetoRepository;
        this.estimativaService = new EstimativaService();

        // Carregar perfis
        this.perfis = perfilRepository.getPerfis();

        // Configura a view
        this.view.setPresenter(this);
        this.view.initComboPerfis(perfis);

        // Inicializa o state para criação
        this.state = new CriarProjetoState(projetoRepository, view);
    }

    @Override
    public void onPerfilSelecionado(Perfil perfilSelecionado) {
        this.perfilAtual = perfilRepository.buscarPorId(perfilSelecionado.getId());
        this.tableModel = new EstimativaTableModel(perfilAtual, this);
        view.initComponents(perfilAtual, tableModel);
        view.getBntSalvar().addActionListener(e -> salvarProjetoEstimativa());
    }

    private double getValorTxt(JTextField txt) {
        return Double.parseDouble(txt.getText().isEmpty() ? "0" : txt.getText());
    }

    @Override
    public ProjetoEstimativa processaEstimativa() {
        ProjetoEstimativa estimativa = new ProjetoEstimativa(
                0,
                0,
                getValorTxt(view.getTxtPercentualImposto()),
                getValorTxt(view.getTxtPercentualLucro()),
                getValorTxt(view.getTxtCustoHardware()),
                getValorTxt(view.getTxtCustoSoftware()),
                getValorTxt(view.getTxtCustoRiscos()),
                getValorTxt(view.getTxtCustoGarantia()),
                getValorTxt(view.getTxtFundoReserva()),
                getValorTxt(view.getTxtOutrosCustos()),
                tableModel.getPlataformasSelecionadas(),
                tableModel.getFuncionalidadesSelecionadas()
        );
        resultado = estimativaService.calcularEstimativa(estimativa);
        return resultado;
    }

    @Override
    public void notificaView(ProjetoEstimativa estimativa) {
        List<Funcionalidade> funcsSelecionadas = tableModel.getFuncionalidadesSelecionadas();
        List<Plataforma> platsSelecionadas = tableModel.getPlataformasSelecionadas();
        view.exibirItensSelecionados(platsSelecionadas, funcsSelecionadas);
        view.exibirEstimativa(
                estimativa.getTotalDias(),
                estimativa.getValorTotal(),
                estimativa.getValorImposto(),
                estimativa.getValorComImpost(),
                estimativa.getValorLucro(),
                estimativa.getValorFinalDoCliente(),
                estimativa.getMeses()
        );
    }

    @Override
    public void onMudancaNaTabela() {
        if (perfilAtual == null) {
            return;
        }
        notificaView(processaEstimativa());
    }

    @Override
    public void salvarProjetoEstimativa() {
        if (perfilAtual == null) {
            JOptionPane.showMessageDialog(view, "Selecione um perfil", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (view.getNomeProjeto().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Nome do projeto não pode ser vazio", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (resultado.getFuncionalidadesSelecionadas().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Selecione pelo menos uma funcionalidade", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (resultado.getPlatafomasSelecionadas().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Selecione pelo menos uma plataforma", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (projetoRepository.hasProjeto(view.getNomeProjeto())) {
            JOptionPane.showMessageDialog(view, "Já existe um projeto com esse nome", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        resultado.setNome(view.getNomeProjeto());
        resultado.setPerfilId(perfilAtual.getId());
        resultado.setUserId(UsuarioSession.getInstance().getUsuarioLogado().getId());

        state.salvarProjeto(resultado);
    }
}
