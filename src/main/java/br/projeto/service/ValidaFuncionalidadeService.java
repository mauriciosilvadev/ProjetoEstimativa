/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.projeto.service;

import java.util.List;

import br.projeto.model.Funcionalidade;

/**
 *
 * @author Maurício Silva <mauricio.s.dev@gmail.com>
 */
public class ValidaFuncionalidadeService {

    private static ValidaFuncionalidadeService instance;
    private List<String> categoriasComSelecaoUnicaDeFuncionalidade = List.of("Tamanho do App", "Nível de UI");

    private ValidaFuncionalidadeService() {
    }

    public static ValidaFuncionalidadeService getInstance() {
        if (instance == null) {
            instance = new ValidaFuncionalidadeService();
        }
        return instance;
    }

    /**
     * Verifica se não há conflito de funcionalidades selecionadas. Exemplo: só
     * pode haver 1 funcionalidade da categoria "Tamanho do App" (regra de
     * negócio do sistema).
     */
    public void validaFuncionalidades(List<Funcionalidade> funcionalidades) {
        for (String categoria : categoriasComSelecaoUnicaDeFuncionalidade) {
            validaApenasUmaFuncionalidadePorCategoria(funcionalidades, categoria);
        }
    }

    private void validaApenasUmaFuncionalidadePorCategoria(List<Funcionalidade> funcionalidades, String categoria) {
        long count = funcionalidades.stream()
                .filter(func -> categoria.equals(func.getCategoria().getNome()))
                .count();

        if (count > 1) {
            throw new IllegalArgumentException(
                    "Só pode existir uma funcionalidade da categoria '" + categoria + "'"
            );
        }
    }
}
