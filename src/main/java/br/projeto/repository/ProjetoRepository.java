package br.projeto.repository;

import br.projeto.model.Projeto;

import java.util.List;
import java.util.Map;

public interface ProjetoRepository {
    List<Projeto> getProjetos();
    Projeto getProjetoPorNome(String nome);
    void adicionarProjeto(String nome, String criador, String dataCriacao, String status,
                          boolean compartilhado, String compartilhadoPor,
                          List<String> tipos, Map<String, Integer> funcionalidadesEscolhidas);
    boolean removerProjetoPorNome(String nome);
}
