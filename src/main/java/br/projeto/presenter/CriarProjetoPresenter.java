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
import br.projeto.view.CriarProjetoView;
import br.projeto.view.components.EstimativaTableModel;

/**
 *
 * @author Maurício Silva <mauricio.s.dev@gmail.com>
 */
public class CriarProjetoPresenter {

    private final CriarProjetoView view;
    private final PerfilRepository perfilRepository;
    private final ProjetoRepository projetoRepository;
    private final EstimativaService estimativaService;
    private EstimativaTableModel tableModel;
    private List<Perfil> perfis;
    private Perfil perfilAtual;
    private ProjetoEstimativa resultado;

    public CriarProjetoPresenter(CriarProjetoView view,
            PerfilRepository perfilRepository,
            ProjetoRepository projetoRepository) {
        this.view = view;
        this.perfilRepository = perfilRepository;
        this.projetoRepository = projetoRepository;
        this.estimativaService = new EstimativaService();

        // Carregar Perfis
        this.perfis = perfilRepository.getPerfis();

        // Configura a View
        this.view.setPresenter(this);
        this.view.initComboPerfis(perfis);
    }

    /**
     * Método chamado pela View quando o usuário muda o perfil
     */
    public void onPerfilSelecionado(Perfil perfilSelecionado) {

        this.perfilAtual = perfilRepository.buscarPorId(perfilSelecionado.getId());

        // Cria um novo TableModel com o perfil selecionado
        this.tableModel = new EstimativaTableModel(perfilAtual, this);

        // Inicializa a view com o perfil selecionado
        view.initComponents(perfilAtual, tableModel);

        view.getBntSalvar().addActionListener(e -> salvarProjetoEstimativa());
    }

    private double getValorTxt(JTextField txt) {
        return Double.parseDouble(txt.getText().isEmpty() ? "0" : txt.getText());
    }

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

    public void notificaView(ProjetoEstimativa estimativa) {
        List<Funcionalidade> funcsSelecionadas = tableModel.getFuncionalidadesSelecionadas();
        List<Plataforma> platsSelecionadas = tableModel.getPlataformasSelecionadas();

        view.exibirItensSelecionados(platsSelecionadas, funcsSelecionadas);
        view.exibirEstimativa(estimativa.getTotalDias(), estimativa.getValorTotal(), estimativa.getValorImposto(), estimativa.getValorComImpost(), estimativa.getValorLucro(), estimativa.getValorFinalDoCliente(), estimativa.getMeses());
    }

    /**
     * Chamado quando algo na tabela muda (funcionalidades, plataformas ou valor
     * das intercesões da funcionalidade x plataforma)
     */
    public void onMudancaNaTabela() {
        if (perfilAtual == null) {
            // Se por acaso ainda não escolheu perfil, não faz nada
            return;
        }

        notificaView(processaEstimativa());
    }

    /**
     * Método salvar salvar estimativa
     */
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

        resultado.setNome(view.getNomeProjeto());
        resultado.setPerfilId(perfilAtual.getId());
        resultado.setUserId(UsuarioSession.getInstance().getUsuarioLogado().getId());

        try {
            projetoRepository.adicionarProjeto(resultado);
            JOptionPane.showMessageDialog(view, "Projeto salvo com sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            view.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao salvar projeto:" + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
