package br.projeto.repository;

import br.projeto.model.Projeto;
import br.projeto.model.Subject;
import br.projeto.presenter.Observer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class ProjetoRepositoryImpl implements Subject, ProjetoRepository {
    private final List<Projeto> projetos;
    private final Connection connection;
    private final List<Observer> observers;

    public ProjetoRepositoryImpl() {
        projetos = new ArrayList<>();
        observers = new ArrayList<>();
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:banco.db");
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS projetos (\n"
                    + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + " nome TEXT NOT NULL,\n"
                    + " usuario TEXT NOT NULL,\n"
                    + " data_inicio TEXT NOT NULL,\n"
                    + " status TEXT NOT NULL,\n"
                    + " concluido BOOLEAN NOT NULL,\n"
                    + " data_conclusao TEXT,\n"
                    + " categorias TEXT NOT NULL\n"
                    + ");";
            statement.execute(sql);

            sql = "CREATE TABLE IF NOT EXISTS funcionalidades (\n"
                    + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + " projeto_id INTEGER NOT NULL,\n"
                    + " nome TEXT NOT NULL,\n"
                    + " peso INTEGER NOT NULL,\n"
                    + " FOREIGN KEY (projeto_id) REFERENCES projetos(id)\n"
                    + ");";
            statement.execute(sql);

        } catch (SQLException e) {
            throw new IllegalArgumentException("Não foi possível conectar ao banco");
        }

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
