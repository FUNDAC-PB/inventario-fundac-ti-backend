package io.gitlab.fundacti.inventarioti.rest.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;

public class CreateSaidaRequest {
    @NotBlank
    private Long inventarioId;

    @NotBlank
    private String tipoSaida;

    @NotBlank
    private LocalDate dataSaida;

    @NotBlank
    private Long quantidade;

    private String termoSaida;

    public CreateSaidaRequest() {}

    public Long getInventarioId() {
        return inventarioId;
    }

    public void setInventarioId(Long inventarioId) {
        this.inventarioId = inventarioId;
    }

    public String getTipoSaida() {
        return tipoSaida;
    }

    public void setTipoSaida(String tipoSaida) {
        this.tipoSaida = tipoSaida;
    }

    public LocalDate getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(LocalDate dataSaida) {
        this.dataSaida = dataSaida;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }

    public String getTermoSaida() {
        return termoSaida;
    }

    public void setTermoSaida(String termoSaida) {
        this.termoSaida = termoSaida;
    }
   


}