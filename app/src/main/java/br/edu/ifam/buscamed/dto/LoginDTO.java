package br.edu.ifam.buscamed.dto;

import br.edu.ifam.buscamed.model.Login;

public class LoginDTO {
    private Long id;
    private String email;
    private String senha;
    private String tipo;

    public LoginDTO(Login login) {
        this.email = login.getEmail();
        this.senha = login.getSenha();
        this.tipo = login.getTipo();
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Login getLogin(){
        return new Login(id, email, senha, tipo);
    }
}
