/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.projeto.model;

/**
 *
 * @author Maurício Silva <mauricio.s.dev@gmail.com>
 */
public class EstimativaResultado {

    private final double totalDias;
    private final double valorTotal;

    public EstimativaResultado(double totalDias, double valorTotal) {
        this.totalDias = totalDias;
        this.valorTotal = valorTotal;
    }

    public double getTotalDias() {
        return totalDias;
    }

    public double getValorTotal() {
        return valorTotal;
    }
}
