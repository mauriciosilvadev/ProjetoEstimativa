package br.projeto.model;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

public class Estimativa {
    private int id;
    private LocalDateTime created_at;
    private Map<String, Integer> elementos;
    private ProjetoModel projeto;

    public Estimativa(int id, LocalDateTime created_at, Map<String, Integer> campos, ProjetoModel projeto) {
        this.id = id;
        this.created_at = created_at;
        this.elementos = campos;
        this.projeto = projeto;
    }

    public Map<String, Integer> getElementos() {
        return Collections.unmodifiableMap(elementos);
    }

    public void adicionarCampo(String nomeFuncionalidade, int dias) {
        if (nomeFuncionalidade == null || nomeFuncionalidade.isEmpty()) {
            throw new IllegalArgumentException("Erro: Nome da funcionalidade não pode ser vazia ou nula.");
        }
        if (dias < 0) {
            throw new IllegalArgumentException("Erro: Quantidade de dias não pode ser negativo. Chave: " + nomeFuncionalidade + " Valor: " + dias);
        }
        elementos.put(nomeFuncionalidade, dias);
    }
}
