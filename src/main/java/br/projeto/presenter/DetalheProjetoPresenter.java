package br.projeto.presenter;

import java.util.List;

import br.projeto.model.Funcionalidade;
import br.projeto.model.Plataforma;
import br.projeto.model.ProjetoEstimativa;
import br.projeto.model.Usuario;
import br.projeto.repository.ProjetoRepository;
import br.projeto.repository.UsuarioRepository;
import br.projeto.service.EstimativaService;
import br.projeto.utils.FormatadorParaDinheiro;
import br.projeto.view.DetalheProjetoView;

public class DetalheProjetoPresenter implements Observer {

    private final DetalheProjetoView view;
    private final EstimativaService estimativaService;
    private final ProjetoRepository projetoRepository;
    private final UsuarioRepository usuarioRepository;
    private final String projetoNome;
    private final boolean isCompartilhado;

    public DetalheProjetoPresenter(DetalheProjetoView view, ProjetoRepository projetoRepository, String projetoNome, UsuarioRepository usuarioRepository, boolean isCompartilhado) {
        this.view = view;
        this.projetoRepository = projetoRepository;
        this.usuarioRepository = usuarioRepository;
        this.projetoNome = projetoNome;
        this.isCompartilhado = isCompartilhado;
        this.estimativaService = new EstimativaService();

        this.projetoRepository.addObserver(this);
        carregarDetalhesProjeto();
    }

    private void carregarDetalhesProjeto() {
        ProjetoEstimativa projeto = projetoRepository.getProjetoPorNome(projetoNome);
        if (projeto != null) {
            projeto = estimativaService.calcularEstimativa(projeto);
            carregarValoresDaTela(projeto);
            carregarDetalhesDaTabela(projeto);
        }
    }

    private void carregarValoresDaTela(ProjetoEstimativa projeto) {
        Usuario usuario = usuarioRepository.buscarPorId(projeto.getUserId());;
        List<Plataforma> plataformas = projeto.getPlatafomasSelecionadas();

        String plataformaFormatadas = plataformas.stream()
                .map(Plataforma::getNome)
                .reduce((a, b) -> a + ", " + b)
                .orElse("N/A");

        view.getLblNome().setText("Nome: " + projeto.getNome());
        if (isCompartilhado) {
            view.getLblCriador().setText("Compartilhado por: " + usuario.getNome() + " (" + usuario.getEmail() + ")");
        } else {
            view.getLblCriador().setText("Criador: " + usuario.getNome() + " (" + usuario.getEmail() + ")");
        }
        view.getLblData().setText("Data de Criação: " + projeto.getDataCriacao());
        view.getLblPerfilProjeto().setText("Perfil do Projeto: " + projeto.getPerfil().getNome());
        view.getLblPlataformas().setText("Plataformas cotadas: " + plataformaFormatadas);
        view.getLblStatus().setText("Status: Criado");
        view.getLblTotal().setText("Valor Total Calculado: " + FormatadorParaDinheiro.formatar(projeto.getValorTotal()));
        view.getLblTotalDias().setText("Dias Totais: " + projeto.getTotalDias());
        view.getLblValorTotal().setText("Valor Final do cliente: " + FormatadorParaDinheiro.formatar(projeto.getValorFinalDoCliente()));
        view.getLblTotalCusto().setText("Total de Custo: " + FormatadorParaDinheiro.formatar(projeto.getTotalCusto()));
        view.getLblImposto().setText("Percentual de Imposto: " + projeto.getPercentualImposto() + "%");
        view.getLblTotalComImposto().setText("Total com Imposto: " + FormatadorParaDinheiro.formatar(projeto.getValorComImpost()));
        view.getLblLucro().setText("Percentual de Lucro: " + projeto.getPercentualLucro() + "%");
        view.getLblTotalComLucro().setText("Total com Lucro: " + FormatadorParaDinheiro.formatar(projeto.getValorComLucro()));
        view.getLblMeses().setText("Meses: " + projeto.getMeses());
        view.getLblMediaMensal().setText("Média Mensal: " + FormatadorParaDinheiro.formatar(projeto.getMediaPorMes()));
    }

    private void carregarDetalhesDaTabela(ProjetoEstimativa projeto) {
        List<Funcionalidade> funcionalidadesSelecionadas = projeto.getFuncionalidadesSelecionadas();

        Object[][] dados = new Object[funcionalidadesSelecionadas.size()][2];
        int row = 0;
        for (Funcionalidade func : funcionalidadesSelecionadas) {
            String nomeFuncionalidade = func.getNome();
            StringBuilder detalhes = new StringBuilder();
            List<Plataforma> plataformas = projeto.getPlatafomasSelecionadas();
            for (Plataforma plataforma : plataformas) {
                double valor = 0;
                valor = func.getValorPorPlataforma(plataforma);
                String linhaDetalhe;
                switch (func.getCategoria().getTipo()) {
                    case "dia":
                        linhaDetalhe = "[" + plataforma.getNome() + " - " + valor + " dias]";
                        break;
                    case "percentual":
                        linhaDetalhe = "[" + plataforma.getNome() + " - " + valor + "%]";
                        break;
                    case "monetario":
                        linhaDetalhe = "[" + plataforma.getNome() + " - " + FormatadorParaDinheiro.formatar(valor) + "]";
                        break;
                    default:
                        linhaDetalhe = "[" + plataforma.getNome() + " - " + valor + "]";
                        break;
                }
                detalhes.append(linhaDetalhe).append(" ");
            }
            dados[row][0] = nomeFuncionalidade;
            dados[row][1] = detalhes.toString().trim();
            row++;
        }

        double valorTotal = projeto.getValorFinalDoCliente();
        view.atualizarTabela(dados, valorTotal);
    }

    @Override
    public void update(List<ProjetoEstimativa> projetos) {
        carregarDetalhesProjeto();
    }
}
