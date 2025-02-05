package br.edu.ifam.buscamed.dto;

public class VendaInputDTO {

    private Long usuarioId, remedioId;
    private String concluida;

    public VendaInputDTO() {
    }

    public VendaInputDTO(Long remedio, String situacao, Long usuario) {
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
