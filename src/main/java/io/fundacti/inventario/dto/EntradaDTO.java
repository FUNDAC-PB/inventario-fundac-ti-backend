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
public class EntradaDTO {

    private Long id;

    @NotBlank
    private String tipoEntrada;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dataEntrada;

    @NotNull
    private Integer quantidade;

    private String termoRecebimento;

    @NotNull
    private Long inventarioId;

}