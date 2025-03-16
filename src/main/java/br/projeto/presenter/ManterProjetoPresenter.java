/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.projeto.presenter;

import br.projeto.model.Perfil;
import br.projeto.model.ProjetoEstimativa;

/**
 *
 * @author Maurício Silva <mauricio.s.dev@gmail.com>
 */
public interface ManterProjetoPresenter {

    void onMudancaNaTabela();

    void onPerfilSelecionado(Perfil perfilSelecionado);

    ProjetoEstimativa processaEstimativa();

    void notificaView(ProjetoEstimativa estimativa);

    void salvarProjetoEstimativa();
}
