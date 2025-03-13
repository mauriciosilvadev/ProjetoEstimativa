package br.projeto.state.login;

import br.projeto.dbConnection.factory.ConnectionFactory;
import br.projeto.presenter.LoginPresenter;
import br.projeto.presenter.PrincipalPresenter;
import br.projeto.presenter.helpers.WindowManager;
import br.projeto.repository.PerfilRepositoryImpl;
import br.projeto.repository.ProjetoRepositoryImpl;

public class LoginAutenticadoState implements LoginState {

    @Override
    public void handle(LoginPresenter presenter) {
        PrincipalPresenter principalPresenter = new PrincipalPresenter(new ProjetoRepositoryImpl(), new PerfilRepositoryImpl(ConnectionFactory.getInstance().getConnection()), presenter);
        WindowManager.getInstance().initialize(principalPresenter);
        presenter.getView().dispose();
    }

}
