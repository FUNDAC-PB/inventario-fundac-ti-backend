package io.gitlab.fundacti.inventarioti.rest.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;

public class CreateEntradaRequest {
    @NotBlank
    private Long inventarioId;

    @NotBlank
    private String tipoEntrada;

    @NotBlank
    private LocalDate dataEntrada;

    @NotBlank
    private Long quantidade;

    private String termoRecebimento;

    // Constructor vazio
    public CreateEntradaRequest() {}

    public Long getInventarioId() {
        return inventarioId;
    }

    public void setInventarioId(Long inventarioId) {
        this.inventarioId = inventarioId;
    }

    public String getTipoEntrada() {
        return tipoEntrada;
    }

    public void setTipoEntrada(String tipoEntrada) {
        this.tipoEntrada = tipoEntrada;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }

    public String getTermoRecebimento() {
        return termoRecebimento;
    }

    public void setTermoRecebimento(String termoRecebimento) {
        this.termoRecebimento = termoRecebimento;
    }
   


}