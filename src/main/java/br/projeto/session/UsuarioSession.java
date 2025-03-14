/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.projeto.session;

import br.projeto.model.Usuario;

/**
 *
 * @author Maurício Silva <mauricio.s.dev@gmail.com>
 */
public class UsuarioSession {

    private static UsuarioSession instance;
    private Usuario usuarioLogado;

    private UsuarioSession() {
    }

    public static UsuarioSession getInstance() {
        if (instance == null) {
            instance = new UsuarioSession();
        }
        return instance;
    }

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void limparSessao() {
        this.usuarioLogado = null;
    }

    public boolean isUsuarioLogado() {
        return usuarioLogado != null;
    }
}
