package br.projeto.adapter;

import br.projeto.session.UsuarioSession;
import br.ufes.log.*;

import java.io.File;

import io.github.cdimascio.dotenv.Dotenv;

public class LoggerAdapterImpl implements LoggerAdapter {

    private LogFormatter formatter;
    private LogWriter writer;
    private static LoggerAdapterImpl instance;
    private String logType;

    private LoggerAdapterImpl() {
        Dotenv env = Dotenv.load();

        this.logType = env.get("LOG_TYPE");

        configureLog();
    }

    private void configureLog() {
        String directoryPath = "logs";

        new File(directoryPath).mkdirs();

        String filePath = directoryPath + "/log." + (this.logType.equalsIgnoreCase("csv") ? "csv" : "json");

        if ("csv".equalsIgnoreCase(this.logType)) {
            this.formatter = new CSVLogFormatter();
        } else if ("json".equalsIgnoreCase(this.logType)) {
            this.formatter = new JSONLogFormatter();
        } else {
            throw new IllegalArgumentException("Tipo de log inválido.");
        }
        this.writer = new FileLogWriter(filePath);
    }

    @Override
    public void log(String operation, String name) {
        String usuarioNome = UsuarioSession.getInstance().getUsuarioLogado() != null
                ? UsuarioSession.getInstance().getUsuarioLogado().getNome()
                : "Desconhecido";

        String formattedMessage = formatter.format(operation, name, usuarioNome);
        writer.write(formattedMessage);
    }

    public static LoggerAdapterImpl getInstance() {
        if (instance == null) {
            instance = new LoggerAdapterImpl();
        }
        return instance;
    }

    public void setLogType(String logType) {
        this.logType = logType;

        configureLog();
    }
}
