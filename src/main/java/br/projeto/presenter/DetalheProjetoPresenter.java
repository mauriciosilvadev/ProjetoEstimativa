package br.projeto.presenter;

import java.util.List;

import br.projeto.model.Funcionalidade;
import br.projeto.model.Plataforma;
import br.projeto.model.ProjetoEstimativa;
import br.projeto.model.Usuario;
import br.projeto.repository.ProjetoRepository;
import br.projeto.repository.UsuarioRepository;
import br.projeto.service.EstimativaService;
import br.projeto.view.DetalheProjetoView;

public class DetalheProjetoPresenter implements Observer {

    private final DetalheProjetoView view;
    private final EstimativaService estimativaService;
    private final ProjetoRepository projetoRepository;
    private final UsuarioRepository usuarioRepository;
    private final String projetoNome;

    public DetalheProjetoPresenter(DetalheProjetoView view, ProjetoRepository projetoRepository, String projetoNome, UsuarioRepository usuarioRepository) {
        this.view = view;
        this.projetoRepository = projetoRepository;
        this.usuarioRepository = usuarioRepository;
        this.projetoNome = projetoNome;
        this.estimativaService = new EstimativaService();

        this.projetoRepository.addObserver(this);
        carregarDetalhesProjeto();
    }

    private void carregarDetalhesProjeto() {
        ProjetoEstimativa projeto = projetoRepository.getProjetoPorNome(projetoNome);
        if (projeto != null) {
            carregarCabecalho(projeto);
            carregarDetalhes(projeto);
        }
    }

    private void carregarCabecalho(ProjetoEstimativa projeto) {
        String perfilUtilizado = projeto.getPerfil().getNome();
        Usuario usuario = usuarioRepository.buscarPorId(projeto.getUserId());

        String criador = usuario.getNome() + " (" + usuario.getEmail() + ")";

        view.atualizarCabecalho(
                projeto.getNome(),
                criador,
                "Data de criação ainda precisa adicionar",
                perfilUtilizado,
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
