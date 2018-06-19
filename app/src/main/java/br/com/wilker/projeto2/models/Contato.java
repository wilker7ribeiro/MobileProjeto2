package br.com.wilker.projeto2.models;

import java.io.Serializable;

public class Contato implements Serializable{

    private Long id;
    private String nomePessoa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomePessoa() {
        return nomePessoa;
    }

    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
    }
}
