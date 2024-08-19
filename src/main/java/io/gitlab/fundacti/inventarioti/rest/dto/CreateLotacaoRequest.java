package io.gitlab.fundacti.inventarioti.rest.dto;

import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;

public class CreateLotacaoRequest {
    @NotBlank
    private String nome;

    public CreateLotacaoRequest() {}

    // Getters and Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}