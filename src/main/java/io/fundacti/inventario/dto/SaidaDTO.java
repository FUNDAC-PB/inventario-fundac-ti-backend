package io.fundacti.inventario.dto;

import java.time.LocalDate;

import io.quarkus.runtime.annotations.RegisterForReflection;
import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@RegisterForReflection
@Getter
@Setter
@ToString

public class SaidaDTO {

    private Long id;

    @NotBlank
    private String tipoSaida;

    @NotBlank
    private LocalDate dataSaida;

    @NotBlank
    private Long quantidade;

    private String termoSaida;

    @NotNull
    private Long inventarioId;
 
}