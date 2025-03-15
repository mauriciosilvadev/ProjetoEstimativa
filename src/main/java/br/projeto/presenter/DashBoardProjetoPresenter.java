package br.projeto.presenter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jfree.data.general.DefaultPieDataset;

import br.projeto.model.ProjetoEstimativa;
import br.projeto.repository.ProjetoRepository;
import br.projeto.service.EstimativaService;
import br.projeto.view.DashBoardProjetoView;

public class DashBoardProjetoPresenter implements Observer {

    private final DashBoardProjetoView view;
    private final EstimativaService estimativaService;
    private final ProjetoRepository repository;

    public DashBoardProjetoPresenter(DashBoardProjetoView view, ProjetoRepository repository) {
        this.view = view;
        this.repository = repository;
        this.estimativaService = new EstimativaService();

        this.repository.addObserver(this);
        carregarDashboard();
    }

    private void carregarDashboard() {
        List<ProjetoEstimativa> projetos = repository.getProjetos();

        int totalProjetos = projetos.size();

        double diasTotais = projetos.stream().mapToDouble(p -> p.getTotalDias()).sum();
        double custoTotal = projetos.stream().mapToDouble(p -> (estimativaService.calcularEstimativa(p)).getValorFinalDoCliente()).sum();

        view.exibirDadosConsolidados(totalProjetos, diasTotais, custoTotal);

        DefaultPieDataset datasetCustos = gerarDatasetCustos(projetos);
        DefaultPieDataset datasetProjetos = gerarDatasetProjetos(projetos);

        view.atualizarGraficos(datasetCustos, datasetProjetos);
    }

    private DefaultPieDataset gerarDatasetCustos(List<ProjetoEstimativa> projetos) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (ProjetoEstimativa projeto : projetos) {
            double custo = projeto.getTotalCusto();
            dataset.setValue(projeto.getNome(), custo);
        }
        return dataset;
    }

    private DefaultPieDataset gerarDatasetProjetos(List<ProjetoEstimativa> projetos) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        Map<String, Long> tipos = projetos.stream()
                .collect(Collectors.groupingBy(ProjetoEstimativa::getNome, Collectors.counting()));

        for (Map.Entry<String, Long> entry : tipos.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }
        return dataset;
    }

    @Override
    public void update(List<ProjetoEstimativa> projetos) {
        carregarDashboard();
    }
}
