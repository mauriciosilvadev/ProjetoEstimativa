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
import br.projeto.state.projetoEstimativa.EditarProjetoState;
import br.projeto.state.projetoEstimativa.ProjetoState;
import br.projeto.view.ProjetoEstimativaView;
import br.projeto.view.components.EstimativaTableModel;

/**
 *
 * @author Maurício Silva <mauricio.s.dev@gmail.com>
 */
public class EditarProjetoPresenter implements ManterProjetoPresenter {

    private final ProjetoEstimativaView view;
    private final PerfilRepository perfilRepository;
    private final ProjetoRepository projetoRepository;
    private final ProjetoEstimativa projetoEstimativa;
    private final EstimativaService estimativaService;
    private EstimativaTableModel tableModel;
    private Perfil perfilAtual;
    private ProjetoEstimativa resultado;
    private ProjetoState state;
    private final int projetoEstimativaId;

    public EditarProjetoPresenter(ProjetoEstimativaView view,
            PerfilRepository perfilRepository,
            ProjetoRepository projetoRepository,
            ProjetoEstimativa projetoEstimativa) {
        this.view = view;
        this.perfilRepository = perfilRepository;
        this.projetoRepository = projetoRepository;
        this.projetoEstimativa = projetoEstimativa;
        this.projetoEstimativaId = projetoEstimativa.getId();
        this.estimativaService = new EstimativaService();
        this.perfilAtual = perfilRepository.buscarPorId(projetoEstimativa.getPerfilId());

        configuraValores();
        preencherCampos();
        notificaView(processaEstimativa());

        // Inicializa o state para edição
        this.state = new EditarProjetoState(projetoRepository, view, projetoEstimativa);
    }

    private void configuraValores() {
        List<Funcionalidade> funcionalidadesPerfil = this.perfilAtual.getFuncionalidades();
        List<Funcionalidade> funcionalidadesProjeto = this.projetoEstimativa.getFuncionalidadesSelecionadas();

        for (int i = 0; i < funcionalidadesPerfil.size(); i++) {
            Funcionalidade funcOriginal = funcionalidadesPerfil.get(i);
            for (Funcionalidade funcProjeto : funcionalidadesProjeto) {
                if (funcOriginal.getId() == funcProjeto.getId()) {
                    for (Plataforma plataforma : this.perfilAtual.getPlataformas()) {
                        if (Math.abs(funcProjeto.getValorPorPlataforma(plataforma)) < 1e-6) {
                            funcProjeto.addValorPlataforma(plataforma, funcOriginal.getValorPorPlataforma(plataforma));
                        }
                    }
                    funcionalidadesPerfil.set(i, funcProjeto);
                    break;
                }
            }
        }

        this.perfilAtual.setFuncionalidades(funcionalidadesPerfil);

        List<Perfil> perfis = List.of(this.perfilAtual);

        // Configura a View
        this.view.setPresenter(this);
        this.view.initComboPerfis(perfis);
        this.onPerfilSelecionado(this.perfilAtual);

        for (Plataforma plataforma : this.projetoEstimativa.getPlatafomasSelecionadas()) {
            this.tableModel.setPlataformaSelecionada(plataforma);
        }
    }

    private void preencherCampos() {
        view.getTxtNomeProjeto().setText(projetoEstimativa.getNome());
        view.getTxtPercentualImposto().setText(Integer.toString((int) projetoEstimativa.getPercentualImposto()));
        view.getTxtPercentualLucro().setText(Integer.toString((int) projetoEstimativa.getPercentualLucro()));
        view.getTxtCustoHardware().setText(Integer.toString((int) projetoEstimativa.getCustoHardware()));
        view.getTxtCustoSoftware().setText(Integer.toString((int) projetoEstimativa.getCustoSoftware()));
        view.getTxtCustoRiscos().setText(Integer.toString((int) projetoEstimativa.getCustosRiscos()));
        view.getTxtCustoGarantia().setText(Integer.toString((int) projetoEstimativa.getCustoGarantia()));
        view.getTxtFundoReserva().setText(Integer.toString((int) projetoEstimativa.getFundoReserva()));
        view.getTxtOutrosCustos().setText(Integer.toString((int) projetoEstimativa.getOutrosCustos()));
    }

    @Override
    public void onPerfilSelecionado(Perfil perfilSelecionado) {
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

        resultado.setNome(view.getNomeProjeto());
        resultado.setPerfilId(perfilAtual.getId());
        resultado.setUserId(UsuarioSession.getInstance().getUsuarioLogado().getId());
        resultado.setId(projetoEstimativaId);

        // Delegar a operação de salvar para o state de edição
        state.salvarProjeto(resultado);
    }
}
