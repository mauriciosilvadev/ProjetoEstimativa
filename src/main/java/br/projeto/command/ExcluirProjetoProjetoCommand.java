package br.projeto.command;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

import br.projeto.repository.ProjetoRepository;

public class ExcluirProjetoProjetoCommand implements ProjetoCommand {

    private final ProjetoRepository repository;
    private String projetoNome;
    private JDesktopPane desktop;

    public ExcluirProjetoProjetoCommand(ProjetoRepository repository) {
        this.repository = repository;
    }

    public ExcluirProjetoProjetoCommand(ProjetoRepository repository, String projetoNome, JDesktopPane desktop) {
        this.repository = repository;
        this.projetoNome = projetoNome;
        this.desktop = desktop;
    }

    public void setProjetoNome(String projetoNome) {
        this.projetoNome = projetoNome;
    }

    @Override
    public void execute() {
        if (projetoNome == null || projetoNome.isEmpty()) {
            new MostrarMensagemProjetoCommand("Nome do projeto não definido.").execute();
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(
                null,
                "Deseja realmente excluir o projeto \"" + projetoNome + "\"?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
            boolean removido = repository.removerProjetoPorNome(projetoNome);
            if (removido) {
                new MostrarMensagemProjetoCommand("Projeto \"" + projetoNome + "\" removido com sucesso!").execute();
                if (desktop != null) {
                    for (JInternalFrame frame : desktop.getAllFrames()) {
                        if (!frame.getTitle().equals("Dashboard de Projetos")) {
                            frame.dispose();
                        }
                    }
                }
            } else {
                new MostrarMensagemProjetoCommand("Erro ao remover o projeto \"" + projetoNome + "\".").execute();
            }
        }
    }
}
