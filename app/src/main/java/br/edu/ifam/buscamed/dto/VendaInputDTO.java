package br.edu.ifam.buscamed.dto;

public class VendaInputDTO {

    private Long usuarioId, remedioId;
    private String concluida;
    private int quantidade;

    public VendaInputDTO() {
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public VendaInputDTO(Long remedio, String situacao, Long usuario, int quantidade) {
        this.quantidade = quantidade;
        this.remedioId = remedio;
        this.concluida = situacao;
        this.usuarioId = usuario;
    }

    public Long getRemedioId() {
        return remedioId;
    }

    public void setRemedioId(Long remedioId) {
        this.remedioId = remedioId;
    }

    public String getConcluida() {
        return concluida;
    }

    public void setConcluida(String concluida) {
        this.concluida = concluida;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

}
