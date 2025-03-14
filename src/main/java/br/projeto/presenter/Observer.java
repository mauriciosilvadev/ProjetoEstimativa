package br.projeto.presenter;

import java.util.List;

import br.projeto.model.ProjetoEstimativa;

public interface Observer {

    void update(List<ProjetoEstimativa> projetos);
}
