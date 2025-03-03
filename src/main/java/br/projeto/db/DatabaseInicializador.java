package br.projeto.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;

public class DatabaseInicializador {

    private static final String DB_NAME = "caixa_do_conhecimento.db";
    private static final String DB_URL = "jdbc:sqlite:" + DB_NAME;

    public static void iniciarBancoDeDados() {
        apagarBanco(); // Usar em ambiente de desenvolvimento
        tentarConexao();
        Flyway flyway = configurarFlyway();
        rodarMigrations(flyway);
    }

    private static void apagarBanco() {
        File dbFile = new File(DB_NAME);

        if (dbFile.exists()) {
            if (dbFile.delete()) {
                System.out.println("Banco de dados deletado com sucesso.");
            } else {
                System.err.println("Erro ao deletar o banco de dados.");
            }
        } else {
            System.out.println("Banco de dados não existe.");
        }
    }

    private static void tentarConexao() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            if (conn != null) {
                System.out.println("Conectado ao banco de dados SQLite.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao SQLite: " + e.getMessage());
        }
    }

    private static Flyway configurarFlyway() {
        return Flyway.configure()
                .dataSource(DB_URL, null, null)
                .locations("classpath:db/migration")
                .mixed(true)
                .baselineOnMigrate(true)
                .load();
    }

    private static void rodarMigrations(Flyway flyway) {
        try {
            flyway.migrate();
            System.out.println("Migrations aplicadas com sucesso.");
        } catch (FlywayException e) {
            System.err.println("Erro ao executar as migrations: " + e);
        }
    }
}
