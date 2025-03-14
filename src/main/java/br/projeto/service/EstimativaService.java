/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.projeto.service;

import br.projeto.model.Funcionalidade;
import br.projeto.model.Plataforma;
import br.projeto.model.ProjetoEstimativa;

/**
 *
 * @author Maurício Silva <mauricio.s.dev@gmail.com>
 */
public class EstimativaService {

    public ProjetoEstimativa calcularEstimativa(ProjetoEstimativa projetoEstimativa) {
        double valorTotal = 0.0;

        double[] diasPorPlataforma = new double[projetoEstimativa.getPlatafomasSelecionadas().size()];
        double[] valorPorPlataforma = new double[projetoEstimativa.getFuncionalidadesSelecionadas().size()];

        for (int i = 0; i < projetoEstimativa.getPlatafomasSelecionadas().size(); i++) {
            Plataforma plataforma = projetoEstimativa.getPlatafomasSelecionadas().get(i);

            double dias = 0;
            double valorMonetario = 0;

            for (Funcionalidade funcionalidade : projetoEstimativa.getFuncionalidadesSelecionadas()) {

                switch (funcionalidade.getCategoria().getTipo()) {
                    case "dia":
                        try {
                            dias += funcionalidade.getValorPorPlataforma(plataforma);
                        } catch (Exception e) {
                            System.out.println("Erro ao calcular dias: " + e.getMessage());
                        }
                        break;

                    case "percentual":
                        Funcionalidade funcionalidadeDoNucleo = projetoEstimativa.getFuncionalidadesSelecionadas().stream()
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
        for (int i = 0; i < projetoEstimativa.getPlatafomasSelecionadas().size(); i++) {
            somaDias += diasPorPlataforma[i];
            valorTotal += (diasPorPlataforma[i] * valorPorPlataforma[i]);
        }

        projetoEstimativa.setTotalDias(somaDias);

        valorTotal += projetoEstimativa.getCustoHardware() + projetoEstimativa.getCustoSoftware() + projetoEstimativa.getCustosRiscos() + projetoEstimativa.getCustoGarantia() + projetoEstimativa.getFundoReserva() + projetoEstimativa.getOutrosCustos();

        projetoEstimativa.setValorTotal(valorTotal);

        return projetoEstimativa;
    }
}
