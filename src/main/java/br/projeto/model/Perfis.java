package br.projeto.model;

public class Perfis {
    private String id;
    private final String nome;
    private String projectId;

    public Perfis(String nome, String projectId) {
        this.nome = nome;
        this.projectId = projectId;
    }

    public String getNome() {
        return nome;
    }

    public String getProjectId() {
        return projectId;
    }
}
