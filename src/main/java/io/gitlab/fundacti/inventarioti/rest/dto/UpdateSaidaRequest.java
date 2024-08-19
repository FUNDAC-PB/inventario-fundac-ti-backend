package io.gitlab.fundacti.inventarioti.rest.dto;

import java.time.LocalDate;

public class UpdateSaidaRequest {

    private Long inventarioId;
    private String tipoSaida;
    private LocalDate dataSaida;
    private Long quantidade;
    private String termoSaida;

    // Constructor vazio
    public UpdateSaidaRequest() {}
    
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