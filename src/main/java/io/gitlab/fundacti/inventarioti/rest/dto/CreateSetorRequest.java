package io.gitlab.fundacti.inventarioti.rest.dto;

import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;

public class CreateSetorRequest {
    @NotBlank
    private String nome;

    public CreateSetorRequest() {}

    // Getters and Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}