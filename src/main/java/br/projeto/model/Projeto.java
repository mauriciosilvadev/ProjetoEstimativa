package br.projeto.model;

import java.util.Map;

public class Projeto {

    private final Integer id;
    private final String nome;
    private final Integer usuarioId;
    private final Integer perfilId;
    private final Double custoHardware;
    private final Double custoSoftware;
    private final Double custoRiscos;
    private final Double custoGarantia;
    private final Double fundoReserva;
    private final Double outrosCustos;
    private final Double percentualImposto;
    private final Double percentualLucro;
    private final Integer totalDias;
    private final String dataCriacao;
    private final Map<Integer, Integer> funcionalidadesEscolhidas;

    public Projeto(Integer id, String nome, Integer usuarioId, Integer perfilId, Double custoHardware, Double custoSoftware, Double custoRiscos, Double custoGarantia, Double fundoReserva, Double outrosCustos, Double percentualImposto, Double percentualLucro, Integer totalDias, String dataCriacao, Map<Integer, Integer> funcionalidadesEscolhidas) {
        this.id = id;
        this.nome = nome;
        this.usuarioId = usuarioId;
        this.perfilId = perfilId;
        this.custoHardware = custoHardware;
        this.custoSoftware = custoSoftware;
        this.custoRiscos = custoRiscos;
        this.custoGarantia = custoGarantia;
        this.fundoReserva = fundoReserva;
        this.outrosCustos = outrosCustos;
        this.percentualImposto = percentualImposto;
        this.percentualLucro = percentualLucro;
        this.totalDias = totalDias;
        this.dataCriacao = dataCriacao;
        this.funcionalidadesEscolhidas = funcionalidadesEscolhidas;
    }

    public Integer getId() {
        return id;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public Integer getPerfilId() {
        return perfilId;
    }

    public Double getCustoHardware() {
        return custoHardware;
    }

    public Double getCustoSoftware() {
        return custoSoftware;
    }

    public Double getCustoRiscos() {
        return custoRiscos;
    }

    public Double getCustoGarantia() {
        return custoGarantia;
    }

    public Double getFundoReserva() {
        return fundoReserva;
    }

    public Double getOutrosCustos() {
        return outrosCustos;
    }

    public Double getPercentualImposto() {
        return percentualImposto;
    }

    public Double getPercentualLucro() {
        return percentualLucro;
    }

    public Integer getTotalDias() {
        return totalDias;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public String getNome() {
        return nome;
    }

    public Map<Integer, Integer> getFuncionalidadesEscolhidas() {
        return funcionalidadesEscolhidas;
    }
}
