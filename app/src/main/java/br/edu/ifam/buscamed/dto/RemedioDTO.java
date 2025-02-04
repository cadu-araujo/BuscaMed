package br.edu.ifam.buscamed.dto;

import br.edu.ifam.buscamed.model.Remedio;

public class RemedioDTO {
    private Long id;
    private String nome;
    private String marca;
    private String descricao;
    private int quantidade;
    private Float valor;
    private String farmacia;

    public RemedioDTO() {}

    public RemedioDTO(Remedio remedio) {
        this.descricao = remedio.getDescricao();
        this.farmacia = remedio.getFarmacia();
        this.id = remedio.getId();
        this.marca = remedio.getMarca();
        this.nome = remedio.getNome();
        this.quantidade = remedio.getQuantidade();
        this.valor = remedio.getValor();
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getFarmacia() {
        return farmacia;
    }

    public void setFarmacia(String farmacia) {
        this.farmacia = farmacia;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getNome() {
        return nome;
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

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public Remedio getRemedio(){
        return new Remedio(descricao,farmacia,id,marca, nome,quantidade,valor);
    }


}
