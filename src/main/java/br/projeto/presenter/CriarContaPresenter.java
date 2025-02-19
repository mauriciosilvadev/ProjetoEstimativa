package br.projeto.presenter;

import br.projeto.repository.UsuarioRepositoryImpl;
import br.projeto.service.CriarContaService;
import br.projeto.view.CriarContaView;

import javax.swing.*;

public class CriarContaPresenter {
    private final CriarContaView view;
    private final CriarContaService service;
    private final LoginPresenter loginPresenter;

    public CriarContaPresenter(CriarContaView view, CriarContaService service, LoginPresenter loginPresenter) {
        this.view = view;
        this.service = service;
        this.loginPresenter = loginPresenter;
        view.getBtnCriar().addActionListener(e -> criarConta());
        view.getBtnCancelar().addActionListener(e -> voltar());
        view.setVisible(true);
    }


    public CriarContaView getView() {
        return view;
    }

    public void criarConta() {
        String nome = view.getTxtNome().getText();
        String email = view.getTxtEmail().getText();
        String senha = view.getTxtSenha().getText();
        try {
            service.criarConta(nome, email, senha);

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(view, e.getMessage());
            return;
        }

        JOptionPane.showMessageDialog(view, "Conta criada com sucesso");
        view.dispose();
        loginPresenter.getView().setVisible(true);
    }

    public void voltar() {
        view.dispose();
        loginPresenter.getView().setVisible(true);
    }
}
