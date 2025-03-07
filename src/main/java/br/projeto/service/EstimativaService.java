/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.projeto.service;

import java.util.List;

import br.projeto.model.EstimativaResultado;
import br.projeto.model.Funcionalidade;
import br.projeto.model.Plataforma;

/**
 *
 * @author Maurício Silva <mauricio.s.dev@gmail.com>
 */
public class EstimativaService {

    public EstimativaResultado calcularEstimativa(
            List<Plataforma> plataformasSelecionadas,
            List<Funcionalidade> funcionalidadesSelecionadas
    ) {
        double valorTotal = 0.0;
        double diasTotais = 0.0;

        double[] diasPorPlataforma = new double[plataformasSelecionadas.size()];
        double[] valorPorPlataforma = new double[plataformasSelecionadas.size()];

        for (int i = 0; i < plataformasSelecionadas.size(); i++) {
            Plataforma plataforma = plataformasSelecionadas.get(i);

            double dias = 0;
            double valorMonetario = 0;

            for (Funcionalidade funcionalidade : funcionalidadesSelecionadas) {

                switch (funcionalidade.getCategoria().getTipo()) {
                    case "dia":
                        dias += funcionalidade.getValorPorPlataforma(plataforma);
                        break;

                    case "percentual":
                        Funcionalidade funcionalidadeDoNucleo = funcionalidadesSelecionadas.stream()
                                .filter(f -> "Tamanho do App".equals(f.getCategoria().getNome()))
                                .findFirst()
                                .orElse(null);
                        if (funcionalidadeDoNucleo != null) {
                            double perc = funcionalidade.getValorPorPlataforma(plataforma);
                            double valorNucleo = funcionalidadeDoNucleo.getValorPorPlataforma(plataforma);
                            dias += (perc / 100.0) * valorNucleo;
                        }
                        break;

                    case "monetario":
                        valorMonetario += funcionalidade.getValorPorPlataforma(plataforma);
                        break;

                    default:
                        break;
                }
            }

            diasPorPlataforma[i] = dias;
            valorPorPlataforma[i] = valorMonetario;
        }

        // Calcular valores totais
        double somaDias = 0;
        for (int i = 0; i < plataformasSelecionadas.size(); i++) {
            somaDias += diasPorPlataforma[i];
            valorTotal += (diasPorPlataforma[i] * valorPorPlataforma[i]);
        }

        diasTotais = somaDias;

        // Retorna um objeto com os valores estimados
        return new EstimativaResultado(diasTotais, valorTotal);
    }
}
