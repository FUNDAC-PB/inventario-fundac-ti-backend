package io.fundacti.inventario.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.quarkus.runtime.annotations.RegisterForReflection;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@RegisterForReflection
@Getter
@Setter
@ToString
public class GarantiaDTO {

    private Long id;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dataInicio;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dataFim;

    private String detalhes;

    @NotNull
    private Long inventarioId;


}