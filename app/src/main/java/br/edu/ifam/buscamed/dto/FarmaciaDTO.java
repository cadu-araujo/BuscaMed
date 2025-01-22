package br.edu.ifam.buscamed.dto;

import br.edu.ifam.buscamed.model.Farmacia;

public class FarmaciaDTO {
    private Long id;
    private String nome;
    private String cnpj;
    private String endereco;
    public FarmaciaDTO() {}

    public FarmaciaDTO(Farmacia farmacia) {
        this.cnpj = farmacia.getCnpj();
        this.endereco = farmacia.getEndereco();
        this.id = farmacia.getId();
        this.nome = farmacia.getNome();
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

    public Farmacia getFarmacia(){
        return new Farmacia(nome, cnpj, id, endereco);
    }
}
