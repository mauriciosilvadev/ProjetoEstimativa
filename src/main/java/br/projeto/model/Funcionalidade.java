/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.projeto.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore; // Importe a anotação

/**
 *
 * @author Maurício Silva <mauricio.s.dev@gmail.com>
 */
public class Funcionalidade {

    private int id;
    private Categoria categoria;

    @JsonIgnore // Ignora a referência ao Perfil durante a serialização
    private Perfil perfil;

    private String nome;
    private boolean foiSelecionada;
    private Map<Plataforma, Double> valoresPlataformas = new HashMap<>();

    public Funcionalidade(int id, Categoria categoria, Perfil perfil, String nome) {
        this.id = id;
        this.categoria = categoria;
        this.perfil = perfil;
        this.nome = nome;
    }

    public Funcionalidade(int id, Categoria categoria, String nome) {
        this.id = id;
        this.categoria = categoria;
        this.nome = nome;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public int getId() {
        return id;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public String getNome() {
        return nome;
    }

    public boolean isFoiSelecionada() {
        return foiSelecionada;
    }

    public Double getValorPorPlataforma(Plataforma plataforma) {
        for (Plataforma p : valoresPlataformas.keySet()) {
            if (p.getId() == plataforma.getId()) {
                return valoresPlataformas.get(p);
            }
        }
        return 0.0;
    }

    public void addValorPlataforma(Plataforma plataforma, Double valor) {
        for (Plataforma p : valoresPlataformas.keySet()) {
            if (p.getId() == plataforma.getId()) {
                valoresPlataformas.replace(p, valor);
                return;
            }
        }
        valoresPlataformas.put(plataforma, valor);
    }

    public void setFoiSelecionada(boolean foiSelecionada) {
        this.foiSelecionada = foiSelecionada;
    }
}
