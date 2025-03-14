package br.projeto.presenter;

import java.util.List;

import br.projeto.model.Funcionalidade;
import br.projeto.model.Plataforma;
import br.projeto.model.ProjetoEstimativa;
import br.projeto.repository.ProjetoRepository;
import br.projeto.service.EstimativaService;
import br.projeto.view.DetalheProjetoView;

public class DetalheProjetoPresenter implements Observer {

    private final DetalheProjetoView view;
    private final EstimativaService estimativaService;
    private final ProjetoRepository repository;
    private final String projetoNome;

    public DetalheProjetoPresenter(DetalheProjetoView view, ProjetoRepository repository, String projetoNome) {
        this.view = view;
        this.repository = repository;
        this.projetoNome = projetoNome;
        this.estimativaService = new EstimativaService();

        this.repository.addObserver(this);
        carregarDetalhesProjeto();
    }

    private void carregarDetalhesProjeto() {
        ProjetoEstimativa projeto = repository.getProjetoPorNome(projetoNome);
        if (projeto != null) {
            carregarCabecalho(projeto);
            carregarDetalhes(projeto);
        }
    }

    private void carregarCabecalho(ProjetoEstimativa projeto) {
        String tiposConcatenados = projeto.getPerfilId() + " perfil id";
        String criador = projeto.getUserId() + " usuario id";

        view.atualizarCabecalho(
                projeto.getNome(),
                criador,
                "Data de criação ainda precisa adicionar",
                tiposConcatenados,
                "Criado"
        );
    }

    private void carregarDetalhes(ProjetoEstimativa projeto) {
        List<Funcionalidade> funcionalidadesSelecionadas = projeto.getFuncionalidadesSelecionadas();

        Object[][] dadosTabela = funcionalidadesSelecionadas.stream()
                .map(func -> {
                    String nomeFuncionalidade = func.getNome();
                    List<Plataforma> plataformas = projeto.getPlatafomasSelecionadas();
                    String valorFormatado;
                    if (!plataformas.isEmpty()) {
                        Plataforma primeiraPlataforma = plataformas.get(0);
                        Double valor = func.getValorPorPlataforma(primeiraPlataforma);
                        valorFormatado = (valor != null) ? String.format("R$ %.2f", valor) : "N/A";
                    } else {
                        valorFormatado = "N/A";
                    }
                    return new Object[]{nomeFuncionalidade, valorFormatado};
                })
                .toArray(Object[][]::new);

        double valorTotal = projeto.getValorFinalDoCliente();
        view.atualizarTabela(dadosTabela, valorTotal);
    }

    @Override
    public void update(List<ProjetoEstimativa> projetos) {
        carregarDetalhesProjeto();
    }
}
