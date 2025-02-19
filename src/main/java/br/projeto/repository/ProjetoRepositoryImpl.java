package br.projeto.repository;

import br.projeto.model.Projeto;
import br.projeto.model.Subject;
import br.projeto.presenter.Observer;

import java.util.*;

public class ProjetoRepositoryImpl implements Subject, ProjetoRepository {
    private final List<Projeto> projetos;
    private final List<Observer> observers;

    public ProjetoRepositoryImpl() {
        projetos = new ArrayList<>();
        observers = new ArrayList<>();

        Map<String, Integer> funcionalidades1 = new HashMap<>();
        funcionalidades1.put("Tamanho do App: Médio", 30);
        funcionalidades1.put("Cadastro por E-mail e Senha", 1);
        funcionalidades1.put("Painel (Dashboard)", 5);
        funcionalidades1.put("Contas Multi-tenant", 3);
        funcionalidades1.put("Subdomínios", 4);
        funcionalidades1.put("E-mails Transacionais", 2);
        funcionalidades1.put("Gerente de Projeto", 10);
        funcionalidades1.put("Nível de UI: Básico", 50);
        funcionalidades1.put("Integração com CMS", 7);
        funcionalidades1.put("Monitoramento de Performance", 1);
        funcionalidades1.put("Relatórios de Erros", 1);

        projetos.add(new Projeto(
                "Gerenciamento Corporativo",
                "Usuario 1",
                "01/01/2023",
                "Estimado",
                false,
                null,
                Arrays.asList("Web/Back-end"),
                funcionalidades1
        ));


    }

    @Override
    public List<Projeto> getProjetos() {
        return projetos;
    }

    @Override
    public Projeto getProjetoPorNome(String nome) {
        return projetos.stream()
                .filter(projeto -> projeto.getNome().equals(nome))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void adicionarProjeto(String nome, String criador, String dataCriacao, String status,
                                 boolean compartilhado, String compartilhadoPor,
                                 List<String> tipos, Map<String, Integer> funcionalidadesEscolhidas) {
        Projeto novoProjeto = new Projeto(nome, criador, dataCriacao, status, compartilhado,
                compartilhadoPor, tipos, funcionalidadesEscolhidas);
        projetos.add(novoProjeto);
        notifyObservers();

    }

    @Override
    public boolean removerProjetoPorNome(String nome) {
        boolean removido = projetos.removeIf(projeto -> projeto.getNome().equals(nome));
        if (removido) {
            notifyObservers();
        }
        return removido;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(projetos);
        }
    }

}
