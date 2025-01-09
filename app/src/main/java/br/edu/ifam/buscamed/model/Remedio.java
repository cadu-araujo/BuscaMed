package br.edu.ifam.buscamed.model;

public class Remedio {
    private Long id;
    private String nome;
    private String marca;
    private String descricao;
    private int quantidade;
    private Float valor;
    private String farmacia;

    public Remedio() {}

    public Remedio(String descricao, String farmacia, Long id, String marca, String nome, int quantidade, Float valor) {
        this.descricao = descricao;
        this.farmacia = farmacia;
        this.id = id;
        this.marca = marca;
        this.nome = nome;
        this.quantidade = quantidade;
        this.valor = valor;
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
}
