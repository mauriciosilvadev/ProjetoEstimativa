/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.projeto.command;

import javax.swing.JDesktopPane;

import br.projeto.model.ProjetoEstimativa;
import br.projeto.presenter.CompartilharProjetoEstimativaPresenter;
import br.projeto.presenter.helpers.WindowManager;
import br.projeto.repository.ProjetoRepository;
import br.projeto.repository.UsuarioRepository;
import br.projeto.view.CompartilharProjetoEstimativaView;

/**
 *
 * @author Maurício Silva <mauricio.s.dev@gmail.com>
 */
public class AbrirCompartilharProjetoEstimativaCommand implements ProjetoCommand {

    private final UsuarioRepository usuarioRepository;
    private final ProjetoRepository projetoRepository;
    private final ProjetoEstimativa projeto;
    private final JDesktopPane desktop;

    public AbrirCompartilharProjetoEstimativaCommand(UsuarioRepository usuarioRepository, ProjetoRepository projetoRepository, ProjetoEstimativa projeto, JDesktopPane desktop) {
        this.usuarioRepository = usuarioRepository;
        this.projetoRepository = projetoRepository;
        this.projeto = projeto;
        this.desktop = desktop;
    }

    @Override
    public void execute() {
        WindowManager windowManager = WindowManager.getInstance();

        String tituloJanela = "Compartilhar Projeto: " + projeto.getNome();

        if (windowManager.isFrameAberto(tituloJanela)) {
            windowManager.bringToFront(tituloJanela);
            return;
        }

        CompartilharProjetoEstimativaView compartilharProjetoView = new CompartilharProjetoEstimativaView();
        compartilharProjetoView.setTitle(tituloJanela);
        new CompartilharProjetoEstimativaPresenter(compartilharProjetoView, usuarioRepository, projetoRepository, projeto);
        desktop.add(compartilharProjetoView);

        try {
            compartilharProjetoView.setMaximum(true);
        } catch (Exception ignored) {
        }
    }
}
