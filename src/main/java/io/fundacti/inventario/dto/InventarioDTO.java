package io.fundacti.inventario.dto;

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
public class InventarioDTO {

    private Long id;

    @NotBlank
    private String nome;

    private String descricao;

    private String responsavel;

    private String status;

    @NotBlank
    private String tipo;

    @NotBlank
    private String patrimonio;

    @NotBlank
    private String numeroSerie;

    @NotNull
    private Long lotacaoId;

    @NotNull
    private Long setorId;

    @NotNull
    private Long categoriaId;
}