package br.edu.ifam.buscamed.model;

public class Farmacia {
    private Long id;
    private String nome;
    private String cnpj;
    private String endereco;

    public Farmacia() {}

    public Farmacia(String cnpj, String endereco, Long id, String nome) {
        this.cnpj = cnpj;
        this.endereco = endereco;
        this.id = id;
        this.nome = nome;
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
}
