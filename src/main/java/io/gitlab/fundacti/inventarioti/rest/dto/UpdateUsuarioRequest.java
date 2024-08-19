package io.gitlab.fundacti.inventarioti.rest.dto;

import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;

public class UpdateUsuarioRequest {

    @NotBlank
    private String username;

    private String password;

    private String role;

    public UpdateUsuarioRequest() {}

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}