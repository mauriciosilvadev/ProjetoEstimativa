package br.projeto.state.login;

import javax.swing.JOptionPane;

import br.projeto.presenter.LoginPresenter;

public class LoginNaoAutenticadoState implements LoginState {

    @Override
    public void handle(LoginPresenter presenter) {
        if (presenter.getView().getTxtSenha().getText().isEmpty() || presenter.getView().getTxtEmail().getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos");
            return;
        }

        try {
            boolean isAuthenticated = presenter.getService().autenticar(
                    presenter.getView().getTxtEmail().getText(),
                    presenter.getView().getTxtSenha().getText()
            );

            if (isAuthenticated) {
                presenter.setState(new LoginAutenticadoState());
                presenter.getState().handle(presenter);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

}
