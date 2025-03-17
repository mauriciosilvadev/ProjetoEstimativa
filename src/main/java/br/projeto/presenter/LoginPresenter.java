package br.projeto.presenter;

import br.projeto.repository.UsuarioRepositoryImpl;
import br.projeto.service.CriarContaService;
import br.projeto.service.LoginService;
import br.projeto.state.login.LoginCriandoContaState;
import br.projeto.state.login.LoginNaoAutenticadoState;
import br.projeto.state.login.LoginState;
import br.projeto.view.CriarContaView;
import br.projeto.view.LoginView;

public class LoginPresenter {

    private final LoginView view;
    private final LoginService service;
    private LoginState state;
    private CriarContaPresenter criarContaPresenter;

    public LoginPresenter(UsuarioRepositoryImpl repository) {

        this.view = new LoginView();
        this.service = new LoginService(repository);
        this.state = new LoginNaoAutenticadoState();

        this.criarContaPresenter = new CriarContaPresenter(new CriarContaView(), new CriarContaService(repository), this);

        view.getBtnAcessar().addActionListener(e -> autenticar());
        view.getBtnCriarConta().addActionListener(e -> criarConta(repository));
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }

    public void autenticar() {
        state.handle(this);
    }

    public void criarConta(UsuarioRepositoryImpl repository) {

        setState(new LoginCriandoContaState());
        state.handle(this);

    }

    public LoginView getView() {
        return view;
    }

    public LoginService getService() {
        return service;
    }

    public LoginState getState() {
        return state;
    }

    public void setState(LoginState state) {
        this.state = state;
    }

    public CriarContaPresenter getCriarContaPresenter() {
        return criarContaPresenter;
    }

}
