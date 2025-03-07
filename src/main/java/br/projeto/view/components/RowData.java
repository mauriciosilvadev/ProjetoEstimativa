/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.projeto.view.components;

import br.projeto.model.Categoria;
import br.projeto.model.Funcionalidade;

/**
 *
 * @author Maurício Silva <mauricio.s.dev@gmail.com>
 */
public class RowData {

    private RowType rowType;
    private Categoria categoria;
    private Funcionalidade funcionalidade;

    public RowData(RowType rowType, Categoria categoria, Funcionalidade funcionalidade) {
        this.rowType = rowType;
        this.categoria = categoria;
        this.funcionalidade = funcionalidade;
    }

    public RowType getRowType() {
        return rowType;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Funcionalidade getFuncionalidade() {
        return funcionalidade;
    }
}
