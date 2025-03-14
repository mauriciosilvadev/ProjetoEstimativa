/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.projeto.repository;

import br.projeto.model.Plataforma;

/**
 *
 * @author Maurício Silva <mauricio.s.dev@gmail.com>
 */
public interface PlataformaRepository {

    Plataforma buscarPorId(int id);
}
