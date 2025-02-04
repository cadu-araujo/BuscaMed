package br.edu.ifam.buscamed.dto;

import br.edu.ifam.buscamed.model.Farmacia;

public class FarmaciaDTO {
    private Long id;
    private String nome;
    private String cnpj;
    private String email;
    private String endereco;
    private Long login;
    public FarmaciaDTO() {}

    public FarmaciaDTO(Farmacia farmacia) {
        this.cnpj = farmacia.getCnpj();
        this.endereco = farmacia.getEndereco();
        this.id = farmacia.getId();
        this.email = farmacia.getEmail();
        this.nome = farmacia.getNome();
        this.login = farmacia.getLogin();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getLogin() {
        return login;
    }

    public void setLogin(Long login) {
        this.login = login;
    }

    public Farmacia getFarmacia(){
        return new Farmacia(cnpj, endereco, email, id, nome, login);
    }
}
