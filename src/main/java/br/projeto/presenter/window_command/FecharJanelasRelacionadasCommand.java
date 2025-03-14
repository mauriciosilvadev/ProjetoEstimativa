package br.projeto.presenter.window_command;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

import br.projeto.model.ProjetoEstimativa;

public class FecharJanelasRelacionadasCommand implements WindowCommand {

    private final JDesktopPane desktop;
    private final List<ProjetoEstimativa> listaProjetos;

    public FecharJanelasRelacionadasCommand(JDesktopPane desktop, List<ProjetoEstimativa> listaProjetos) {
        this.desktop = desktop;
        this.listaProjetos = listaProjetos;
    }

    @Override
    public void execute() {
        List<String> nomesProjetos = new ArrayList<>();
        for (ProjetoEstimativa projeto : listaProjetos) {
            nomesProjetos.add(projeto.getNome());
        }

        JInternalFrame[] quadrosInternos = desktop.getAllFrames();
        for (JInternalFrame quadroInterno : quadrosInternos) {
            if (quadroInterno.getTitle().startsWith("Detalhes do Projeto: ")) {
                String nomeProjeto = quadroInterno.getTitle().replace("Detalhes do Projeto: ", "");
                if (!nomesProjetos.contains(nomeProjeto)) {
                    quadroInterno.dispose();
                }
            }
        }
    }
}
