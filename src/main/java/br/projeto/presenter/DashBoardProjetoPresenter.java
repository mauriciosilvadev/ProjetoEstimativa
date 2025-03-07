package br.projeto.presenter;

import java.util.List;

import org.jfree.data.general.DefaultPieDataset;

import br.projeto.model.Projeto;
import br.projeto.repository.ProjetoRepositoryImpl;
import br.projeto.service.EstimaProjetoService;
import br.projeto.view.DashBoardProjetoView;

public class DashBoardProjetoPresenter implements Observer {

    private final DashBoardProjetoView view;
    private final EstimaProjetoService estimaService;
    private final ProjetoRepositoryImpl repository;

    public DashBoardProjetoPresenter(DashBoardProjetoView view, ProjetoRepositoryImpl repository) {
        this.view = view;
        this.repository = repository;
        this.estimaService = new EstimaProjetoService();

        this.repository.addObserver(this);
        carregarDashboard();
    }

    private void carregarDashboard() {
        List<Projeto> projetos = repository.getProjetos();

        int totalProjetos = projetos.size();
        int diasTotais = projetos.stream()
                .mapToInt(estimaService::calcularDiasTotais)
                .sum();
        double custoTotal = projetos.stream()
                .mapToDouble(estimaService::calcularCusto)
                .sum();

        view.exibirDadosConsolidados(totalProjetos, diasTotais, custoTotal);

        DefaultPieDataset datasetCustos = gerarDatasetCustos(projetos);
    }

    private DefaultPieDataset gerarDatasetCustos(List<Projeto> projetos) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Projeto projeto : projetos) {
            double custo = estimaService.calcularCusto(projeto);
            dataset.setValue(projeto.getNome(), custo);
        }
        return dataset;
    }

    @Override
    public void update(List<Projeto> projetos) {
        carregarDashboard();
    }
}
