/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.projeto.repository;

import java.util.List;

import br.projeto.model.Perfil;

/**
 *
 * @author Maurício Silva <mauricio.s.dev@gmail.com>
 */
public interface PerfilRepository {

    void inserir(Perfil perfil);

    void atualizar(Perfil perfil, int id);

    void deletar(int id);

    List<Perfil> listar();

    Perfil buscarPorId(int id);
}
