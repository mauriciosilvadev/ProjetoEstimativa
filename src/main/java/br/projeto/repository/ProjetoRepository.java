package br.projeto.repository;

import java.util.List;

import br.projeto.model.ProjetoEstimativa;
import br.projeto.presenter.Observer;

public interface ProjetoRepository {

    List<ProjetoEstimativa> getProjetos();

    List<ProjetoEstimativa> getProjetosCompartilhados();

    ProjetoEstimativa getProjetoPorNome(String nome);

    void adicionarProjeto(ProjetoEstimativa projetoEstimativa);

    boolean removerProjetoPorNome(String nome);

    boolean hasProjeto(String nome);

    void addObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers(List<ProjetoEstimativa> projetos);

    boolean compartilharProjeto(int idProjeto, int idUsuario);

    void atualizarProjeto(ProjetoEstimativa projetoEstimativa);
}
