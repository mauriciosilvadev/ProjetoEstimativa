package br.projeto.state.login;

import br.projeto.presenter.LoginPresenter;

public class LoginCriandoContaState implements LoginState {


    @Override
    public void handle(LoginPresenter presenter) {
        presenter.getView().setVisible(false);
        presenter.getCriarContaPresenter().getView().setVisible(true);
    }
}
