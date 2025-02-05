package br.edu.ifam.buscamed.dto;

public class VendaOutputDTO {
    private String nome, telefone, remedio, situacao, farmacia;
    private int quantidade;

    public VendaOutputDTO() {}

    public VendaOutputDTO(String nome, int quantidade, String remedio, String situacao, String telefone, String farmacia) {
        this.nome = nome;
        this.farmacia = farmacia;
        this.quantidade = quantidade;
        this.remedio = remedio;
        this.situacao = situacao;
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public String getFarmacia() {
        return farmacia;
    }

    public void setFarmacia(String farmacia) {
        this.farmacia = farmacia;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getRemedio() {
        return remedio;
    }

    public void setRemedio(String remedio) {
        this.remedio = remedio;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
