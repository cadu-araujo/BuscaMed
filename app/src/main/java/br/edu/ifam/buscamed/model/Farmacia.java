package br.edu.ifam.buscamed.model;

public class Farmacia {
    private Long id;
    private String nome;
    private String cnpj;
    private String endereco;
    private String email;
    private Long login;

    public Farmacia() {}

    public Farmacia(String cnpj, String endereco, String email, Long id, String nome, Long login) {
        this.cnpj = cnpj;
        this.endereco = endereco;
        this.email = email;
        this.id = id;
        this.nome = nome;
        this.login = login;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getLogin() {
        return login;
    }

    public void setLogin(Long login) {
        this.login = login;
    }
}
