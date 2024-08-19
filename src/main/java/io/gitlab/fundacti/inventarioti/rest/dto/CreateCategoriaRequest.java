package io.gitlab.fundacti.inventarioti.rest.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateCategoriaRequest {
    
    @NotBlank
    private String nome;

    // Constructor vazio
    public CreateCategoriaRequest() {}

    // Getters and Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}