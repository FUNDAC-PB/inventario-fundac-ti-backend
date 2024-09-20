package io.fundacti.inventario.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@RegisterForReflection
@Getter
@Setter
@ToString
public class LotacaoDTO {

    private Long id;

    @NotBlank
    private String nome;

}