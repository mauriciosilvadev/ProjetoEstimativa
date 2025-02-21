package br.projeto.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class ProjetoModel {
    private int id;
    private String nomeProjeto;
    private LocalDate date;
    private String tipo;
    private String status;
    private Usuario usuario;

    private boolean foiCompartilhado;
    private String compartilhadoPor;
    private List<ValorBase> valorBase;

    // criar service para essa lista
    private List<Usuario> usuariosCompartilhados;
}
