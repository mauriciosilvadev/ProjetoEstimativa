package br.projeto.state.login;

import br.projeto.presenter.LoginPresenter;

public interface LoginState {
    void handle(LoginPresenter presenter);
}
