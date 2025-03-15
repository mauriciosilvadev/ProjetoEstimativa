/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.projeto.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Maurício Silva <mauricio.s.dev@gmail.com>
 */
public class Perfil {

    private int id;
    private String nome;
    private List<Categoria> categorias = new ArrayList<>();
    private List<Plataforma> plataformas = new ArrayList<>();
    private List<Funcionalidade> funcionalidades = new ArrayList<>();

    public Perfil(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public List<Plataforma> getPlataformas() {
        return plataformas;
    }

    public List<Funcionalidade> getFuncionalidades() {
        return funcionalidades;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void addCategoria(Categoria categoria) {
        this.categorias.add(categoria);
    }

    public void addPlataforma(Plataforma plataforma) {
        this.plataformas.add(plataforma);
    }

    public void addFuncionalidade(Funcionalidade funcionalidade) {
        this.funcionalidades.add(funcionalidade);
    }

    public Categoria getCategoriaById(int id) {
        for (Categoria categoria : categorias) {
            if (categoria.getId() == id) {
                return categoria;
            }
        }
        return null;
    }

    public Plataforma getPlataformaById(int id) {
        for (Plataforma plataforma : plataformas) {
            if (plataforma.getId() == id) {
                return plataforma;
            }
        }
        return null;
    }

    public void setFuncionalidades(List<Funcionalidade> funcionalidades) {
        this.funcionalidades = funcionalidades;
    }

    @Override
    public String toString() {
        return nome;
    }
}
