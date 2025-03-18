/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.projeto.command;

import br.projeto.presenter.LoginPresenter;
import br.projeto.presenter.PrincipalPresenter;
import br.projeto.state.login.LoginNaoAutenticadoState;

/**
 *
 * @author Usuário
 */
public class SairCommand implements ProjetoCommand {

    private final PrincipalPresenter principalPresenter;
    private final LoginPresenter loginPresenter;

    public SairCommand(PrincipalPresenter principalPresenter, LoginPresenter loginPresenter) {
        this.principalPresenter = principalPresenter;
        this.loginPresenter = loginPresenter;
    }

    @Override
    public void execute() {
        principalPresenter.getView().dispose();
        loginPresenter.setState(new LoginNaoAutenticadoState());

        loginPresenter.getView().getTxtEmail().setText("");
        loginPresenter.getView().getTxtSenha().setText("");

        loginPresenter.getView().setVisible(true);
    }

}
