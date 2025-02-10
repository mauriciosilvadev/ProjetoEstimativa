package br.projeto.adapter;

import br.ufes.log.*;

public class LoggerAdapterImpl implements LoggerAdapter{
    private final LogFormatter formatter;
    private final LogWriter writer;

    LoggerAdapterImpl(Builder builder) {
        String logType = builder.logType;
        String filePath = builder.filePath;

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
    public void log(String operation, String name, String user) {
        String formattedMessage = formatter.format(operation, name, user);
        writer.write(formattedMessage);
    }


    public static class Builder {
        private String logType;
        private String filePath;

        public Builder logType(String logType) {
            this.logType = logType;
            return this;
        }

        public Builder filePath(String filePath) {
            this.filePath = filePath;
            return this;
        }

        public LoggerAdapterImpl build() {
            return new LoggerAdapterImpl(this);
        }
    }
}
