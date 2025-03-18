package br.projeto.dbConnection.connections;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;

import io.github.cdimascio.dotenv.Dotenv;

public class H2Connection implements IDatabaseConnection {

    private static Connection connection;
    private final String dbName;
    private final String dbUrl;

    public H2Connection() throws SQLException {
        Dotenv env = Dotenv.load();
        this.dbName = env.get("H2_DB_NAME");
        this.dbUrl = "jdbc:h2:./" + dbName;

        if (Boolean.parseBoolean(env.get("CONFIGURE_DATABASE"))) {
            deleteDatabase();
            Flyway flyway = configureFlyway();
            runMigrations(flyway);
        }

        H2Connection.connection = DriverManager.getConnection(this.dbUrl, "sa", "");
    }

    @Override
    public boolean disconnect() throws SQLException {
        if (!H2Connection.connection.isClosed()) {
            H2Connection.connection.close();
        }
        return true;
    }

    @Override
    public Statement createStatement() throws SQLException {
        return H2Connection.connection.createStatement();
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return H2Connection.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    }

    private void deleteDatabase() {
        File dbFile = new File(dbName + ".mv.db");
        if (dbFile.exists()) {
            if (dbFile.delete()) {
                System.out.println("Banco de dados H2 deletado com sucesso.");
            } else {
                System.err.println("Erro ao deletar o banco de dados H2.");
            }
        } else {
            System.out.println("Banco de dados H2 não existe.");
        }
    }

    private Flyway configureFlyway() {
        return Flyway.configure()
                .dataSource(this.dbUrl, "sa", "")
                .locations("classpath:db/migration/h2")
                .mixed(true)
                .baselineOnMigrate(true)
                .load();
    }

    private void runMigrations(Flyway flyway) {
        try {
            flyway.migrate();
            System.out.println("Migrations H2 aplicadas com sucesso.");
        } catch (FlywayException e) {
            System.err.println("Erro ao executar as migrations H2: " + e);
        }
    }
}
