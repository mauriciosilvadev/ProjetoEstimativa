package br.projeto.state.login;

import br.projeto.presenter.LoginPresenter;
import br.projeto.presenter.PrincipalPresenter;
import br.projeto.presenter.helpers.WindowManager;
import br.projeto.repository.ProjetoRepositoryImpl;

public class LoginAutenticadoState implements LoginState {
    @Override
    public void handle(LoginPresenter presenter) {
        PrincipalPresenter principalPresenter = new PrincipalPresenter(new ProjetoRepositoryImpl());
        WindowManager.getInstance().initialize(principalPresenter);
        presenter.getView().dispose();
    }


}
