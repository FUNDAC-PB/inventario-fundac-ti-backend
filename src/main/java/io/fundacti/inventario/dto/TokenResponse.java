package io.fundacti.inventario.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TokenResponse {
    private String token;
    private String refreshToken;
    private final String type = "Bearer";
}
