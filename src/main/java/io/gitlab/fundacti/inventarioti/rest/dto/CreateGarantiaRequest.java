package io.gitlab.fundacti.inventarioti.rest.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;

public class CreateGarantiaRequest {
    @NotBlank
    private Long inventarioId;

    @NotBlank
    private LocalDate dataInicio;

    @NotBlank
    private LocalDate dataFim;

    private String detalhes;

    // Constructor vazio
    public CreateGarantiaRequest() {}

    public Long getInventarioId() {
        return inventarioId;
    }

    public void setInventarioId(Long inventarioId) {
        this.inventarioId = inventarioId;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }


}