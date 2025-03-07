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

public class SqliteConnection implements IDatabaseConnection {

    private static Connection connection;
    private final String db_name;
    private final String db_url;

    public SqliteConnection() throws SQLException {
        Dotenv env = Dotenv.load();
        this.db_name = env.get("DB_NAME");
        this.db_url = "jdbc:sqlite:" + db_name;

        if (Boolean.parseBoolean(env.get("CONFIGURE_DATABASE"))) {
            this.deleteDatabase();
            Flyway flyway = this.configureFlyway();
            this.runMigrations(flyway);
        }

        SqliteConnection.connection = DriverManager.getConnection(this.db_url);
    }

    @Override
    public boolean disconnect() throws SQLException {
        if (!SqliteConnection.connection.isClosed()) {
            SqliteConnection.connection.close();
        }
        return true;
    }

    @Override
    public Statement createStatement() throws SQLException {
        return SqliteConnection.connection.createStatement();
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return SqliteConnection.connection.prepareStatement(sql);
    }

    private void deleteDatabase() {
        File dbFile = new File(db_name);

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

    private Flyway configureFlyway() {
        return Flyway.configure()
                .dataSource(this.db_url, null, null)
                .locations("classpath:db/migration")
                .mixed(true)
                .baselineOnMigrate(true)
                .load();
    }

    private void runMigrations(Flyway flyway) {
        try {
            flyway.migrate();
            System.out.println("Migrations aplicadas com sucesso.");
        } catch (FlywayException e) {
            System.err.println("Erro ao executar as migrations: " + e);
        }
    }

}
