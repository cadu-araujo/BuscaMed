package br.edu.ifam.buscamed.dto;

import br.edu.ifam.buscamed.interfaces.FarmaciaAPI;
import br.edu.ifam.buscamed.model.Usuario;

public class UsuarioDTO {
    private Long id;
    private String nome;
    private String telefone;
    private String email;
    private String endereco;
    private Long login;
    public UsuarioDTO() {}

    public UsuarioDTO(Long id, String nome, String telefone, String email, String endereco, Long login) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
        this.login = login;
    }

    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.telefone = usuario.getTelefone();
        this.email = usuario.getEmail();
        this.endereco = usuario.getEndereco();
        this.login = usuario.getLogin();
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Long getLogin() {
        return login;
    }

    public void setLogin(Long login) {
        this.login = login;
    }

    public Usuario getUsuario(){
        return new Usuario(id, nome, telefone, email, endereco, login);
    }
}
