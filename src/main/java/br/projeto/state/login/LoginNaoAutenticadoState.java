package br.projeto.state.login;

import javax.swing.JOptionPane;

import br.projeto.presenter.LoginPresenter;

public class LoginNaoAutenticadoState implements LoginState {

    @Override
    public void handle(LoginPresenter presenter) {
        // if (presenter.getView().getTxtSenha().getText().isEmpty() && presenter.getView().getTxtEmail().getText().isEmpty()) {
        //     return;
        // }

        // if (presenter.getView().getTxtSenha().getText().isEmpty() || presenter.getView().getTxtEmail().getText().isEmpty()) {
        //     JOptionPane.showMessageDialog(null, "Preencha todos os campos");
        //     return;
        // }
        // boolean isAuthenticated = presenter.getService().autenticar(
        //         presenter.getView().getTxtEmail().getText(),
        //         presenter.getView().getTxtSenha().getText()
        // );
        boolean isAuthenticated = presenter.getService().autenticar("mauricio.s.dev@gmail.com", "secret");
        // boolean isAuthenticated = presenter.getService().autenticar("ana@example.com", "secret");

        if (isAuthenticated) {
            presenter.setState(new LoginAutenticadoState());
            presenter.getState().handle(presenter);
        } else {
            JOptionPane.showMessageDialog(null, "Usuário ou senha inválidos");
        }
    }

}
