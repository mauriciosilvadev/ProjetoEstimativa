package br.projeto.presenter;

import br.projeto.presenter.helpers.WindowManager;
import br.projeto.repository.ProjetoRepositoryImpl;
import br.projeto.repository.UsuarioRepositoryImpl;
import br.projeto.service.LoginService;
import br.projeto.view.LoginView;

public class LoginPresenter {
    private final LoginView view;
    private final LoginService service;

    public LoginPresenter(UsuarioRepositoryImpl repository) {
        this.view = new LoginView();
        this.service = new LoginService(repository);
        view.getBtnAcessar().addActionListener(e -> autenticar());
        view.setVisible(true);
    }

    public void autenticar() {
        service.autenticar(view.getTxtEmail().getText(), view.getTxtSenha().getText());
        PrincipalPresenter presenter = new PrincipalPresenter(new ProjetoRepositoryImpl());
        WindowManager.getInstance().initialize(presenter);
    }

}
