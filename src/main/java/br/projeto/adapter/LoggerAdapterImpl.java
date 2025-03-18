package br.projeto.adapter;

import br.projeto.session.UsuarioSession;
import br.ufes.log.*;
import java.io.File;

public class LoggerAdapterImpl implements LoggerAdapter{
    private final LogFormatter formatter;
    private final LogWriter writer;

    LoggerAdapterImpl(Builder builder) {
        String logType = builder.logType;

        // Define diretório padrão para salvar os logs
        String directoryPath = "logs";
        new File(directoryPath).mkdirs(); // Cria o diretório se não existir

        // Define o nome do arquivo automaticamente baseado no tipo de log
        String filePath = directoryPath + "/log." + (logType.equalsIgnoreCase("csv") ? "csv" : "json");

        if ("csv".equalsIgnoreCase(logType)) {
            this.formatter = new CSVLogFormatter();
        } else if ("json".equalsIgnoreCase(logType)) {
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
                : "Desconhecido"; // Caso ninguém esteja logado.

        String formattedMessage = formatter.format(operation, name, usuarioNome);
        writer.write(formattedMessage);
    }

    public static class Builder {
        private String logType;

        public Builder logType(String logType) {
            this.logType = logType;
            return this;
        }

        public LoggerAdapterImpl build() {
            return new LoggerAdapterImpl(this);
        }
    }
}
