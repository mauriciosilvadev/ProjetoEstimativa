package br.projeto.model;

public class Usuario {

    private int id;
    private String nome;
    private String email;
    private String senha;

    public Usuario(Builder builder) {
        this.id = builder.id;
        this.nome = builder.nome;
        this.senha = builder.senha;
        this.email = builder.email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static class Builder {

        private int id;
        private String nome;
        private String senha;
        private String email;

        public Builder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder senha(String senha) {
            this.senha = senha;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Usuario build() {
            return new Usuario(this);
        }
    }

}
