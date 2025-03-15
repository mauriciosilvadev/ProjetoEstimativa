package br.projeto.state.login;

import br.projeto.dbConnection.connections.IDatabaseConnection;
import br.projeto.dbConnection.factory.ConnectionFactory;
import br.projeto.presenter.LoginPresenter;
import br.projeto.presenter.PrincipalPresenter;
import br.projeto.presenter.helpers.WindowManager;

public class LoginAutenticadoState implements LoginState {

    @Override
    public void handle(LoginPresenter presenter) {
        IDatabaseConnection connection = ConnectionFactory.getInstance().getConnection();
        PrincipalPresenter principalPresenter = new PrincipalPresenter(connection, presenter);
        WindowManager.getInstance().initialize(principalPresenter);
        presenter.getView().dispose();
    }

}
