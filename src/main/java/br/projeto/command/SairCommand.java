/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.projeto.command;

import br.projeto.presenter.LoginPresenter;
import br.projeto.presenter.PrincipalPresenter;
import br.projeto.state.login.LoginNaoAutenticadoState;
import br.projeto.dbConnection.factory.ConnectionFactory;
import java.sql.SQLException;

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
        // Executa a lógica de logout
        System.out.println("Tentando desconectar do banco de dados...");

    try {
        ConnectionFactory.getInstance().getConnection().disconnect();
        System.out.println("Conexão com o banco de dados fechada com sucesso.");
    } catch (SQLException e) {
        System.err.println("Erro ao fechar a conexão com o banco de dados: " + e.getMessage());
    }
        
        // Fecha a tela principal e reexibe a tela de login
        principalPresenter.getView().dispose();
        loginPresenter.setState(new LoginNaoAutenticadoState());
        loginPresenter.getView().setVisible(true);
    }

}
