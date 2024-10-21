package io.fundacti.inventario.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

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

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dataSaida;

    @NotNull
    private Integer quantidade;

    private String termoSaida;

    @NotNull
    private Long inventarioId;
 
}