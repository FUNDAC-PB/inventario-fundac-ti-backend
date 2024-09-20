package io.fundacti.inventario.dto;

import java.time.LocalDate;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    private LocalDate dataInicio;

    @NotBlank
    private LocalDate dataFim;

    private String detalhes;

    @NotNull
    private Long inventarioId;


}