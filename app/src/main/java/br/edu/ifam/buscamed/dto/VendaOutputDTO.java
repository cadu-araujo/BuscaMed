package br.edu.ifam.buscamed.dto;

public class VendaOutputDTO {
    private String usuario, telefone, remedio, concluida, farmacia;
    private int quantidade;

    public VendaOutputDTO() {}

    public VendaOutputDTO(String nome, int quantidade, String remedio, String situacao, String telefone, String farmacia) {
        this.usuario = nome;
        this.farmacia = farmacia;
        this.quantidade = quantidade;
        this.remedio = remedio;
        this.concluida = situacao;
        this.telefone = telefone;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getFarmacia() {
        return farmacia;
    }

    public void setFarmacia(String farmacia) {
        this.farmacia = farmacia;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
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

    public String getConcluida() {
        return concluida;
    }

    public void setConcluida(String concluida) {
        this.concluida = concluida;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
