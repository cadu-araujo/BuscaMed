package br.edu.ifam.buscamed.model;

public class Login {
    private Long id;
    private String email, senha;
    private String tipo;

    public Login() {
    }

    public Login(Long id, String email, String senha, String tipo) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
