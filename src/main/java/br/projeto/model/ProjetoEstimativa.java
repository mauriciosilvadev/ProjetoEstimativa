/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.projeto.model;

import java.util.List;

/**
 *
 * @author Maurício Silva <mauricio.s.dev@gmail.com>
 */
public class ProjetoEstimativa {

    private String nome;
    private int id;
    private int perfilId;
    private int userId;
    private double totalDias;
    private double valorTotal;
    private double percentualImposto;
    private double percentualLucro;
    private double custoHardware;
    private double custoSoftware;
    private double custosRiscos;
    private double custoGarantia;
    private double fundoReserva;
    private double outrosCustos;
    private List<Plataforma> platafomasSelecionadas;
    private List<Funcionalidade> funcionalidadesSelecionadas;

    public ProjetoEstimativa(double totalDias, double valorTotal, double percentualImposto, double percentualLucro, double custoHardware, double custoSoftware, double custosRiscos, double custoGarantia, double fundoReserva, double outrosCustos, List<Plataforma> platafomasSelecionadas, List<Funcionalidade> funcionalidadesSelecionadas) {
        this.totalDias = totalDias;
        this.valorTotal = valorTotal;
        this.percentualImposto = percentualImposto;
        this.percentualLucro = percentualLucro;
        this.custoHardware = custoHardware;
        this.custoSoftware = custoSoftware;
        this.custosRiscos = custosRiscos;
        this.custoGarantia = custoGarantia;
        this.fundoReserva = fundoReserva;
        this.outrosCustos = outrosCustos;
        this.platafomasSelecionadas = platafomasSelecionadas;
        this.funcionalidadesSelecionadas = funcionalidadesSelecionadas;
    }

    public ProjetoEstimativa(double totalDias, double valorTotal, double percentualImposto, double percentualLucro, double custoHardware, double custoSoftware, double custosRiscos, double custoGarantia, double fundoReserva, double outrosCustos, int id, int perfilId, int userId, String nome) {
        this.totalDias = totalDias;
        this.valorTotal = valorTotal;
        this.percentualImposto = percentualImposto;
        this.percentualLucro = percentualLucro;
        this.custoHardware = custoHardware;
        this.custoSoftware = custoSoftware;
        this.custosRiscos = custosRiscos;
        this.custoGarantia = custoGarantia;
        this.fundoReserva = fundoReserva;
        this.outrosCustos = outrosCustos;
        this.id = id;
        this.perfilId = perfilId;
        this.userId = userId;
        this.nome = nome;
    }

    public double getTotalDias() {
        return totalDias;
    }

    public void setTotalDias(double totalDias) {
        this.totalDias = totalDias;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public double getPercentualImposto() {

        return percentualImposto;
    }

    public double getPercentualLucro() {
        return percentualLucro;
    }

    public double getCustoHardware() {
        return custoHardware;
    }

    public double getCustoSoftware() {
        return custoSoftware;
    }

    public double getCustosRiscos() {
        return custosRiscos;
    }

    public double getCustoGarantia() {
        return custoGarantia;
    }

    public double getFundoReserva() {
        return fundoReserva;
    }

    public double getOutrosCustos() {
        return outrosCustos;
    }

    public double getTotalCusto() {
        return custoHardware + custoSoftware + custosRiscos + custoGarantia + fundoReserva + outrosCustos;
    }

    public double getValorImposto() {
        return valorTotal * (percentualImposto / 100.0);
    }

    public double getValorComImpost() {
        return valorTotal + getValorImposto();
    }

    public double getValorLucro() {
        return valorTotal * (percentualLucro / 100.0);
    }

    public double getValorComLucro() {
        return valorTotal + getValorLucro();
    }

    public double getValorFinalDoCliente() {
        return valorTotal + getValorImposto() + getValorLucro();
    }

    public double getMeses() {
        return totalDias / 30.0;
    }

    public double getMediaPorMes() {
        return getValorFinalDoCliente() / getMeses();
    }

    public List<Plataforma> getPlatafomasSelecionadas() {
        return platafomasSelecionadas;
    }

    public List<Funcionalidade> getFuncionalidadesSelecionadas() {
        return funcionalidadesSelecionadas;
    }

    public void setPlatafomasSelecionadas(List<Plataforma> platafomasSelecionadas) {
        this.platafomasSelecionadas = platafomasSelecionadas;
    }

    public void setFuncionalidadesSelecionadas(List<Funcionalidade> funcionalidadesSelecionadas) {
        this.funcionalidadesSelecionadas = funcionalidadesSelecionadas;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public int getPerfilId() {
        return perfilId;
    }

    public void setPerfilId(int perfilId) {
        this.perfilId = perfilId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
