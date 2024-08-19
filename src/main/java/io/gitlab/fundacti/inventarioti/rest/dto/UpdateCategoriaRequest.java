package io.gitlab.fundacti.inventarioti.rest.dto;

import jakarta.validation.constraints.NotBlank;

public class UpdateCategoriaRequest {
    
    @NotBlank
    private String nome;

    public UpdateCategoriaRequest() {}

    // Getters and Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}