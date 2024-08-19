package io.gitlab.fundacti.inventarioti.rest;

import io.gitlab.fundacti.inventarioti.rest.dto.LoginRequest;
import io.gitlab.fundacti.inventarioti.rest.service.LoginService;
import io.gitlab.fundacti.inventarioti.domain.model.Usuario;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/login")
@Produces("application/json")
@Consumes("application/json")
public class LoginResource {

    @Inject
    LoginService loginService;

    @POST
    public Response login(LoginRequest request) {
        Usuario usuario = loginService.authenticate(request.getUsername(), request.getPassword());
        if (usuario != null) {
            String token = loginService.generateToken(usuario);
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return Response.ok(response).build();
        } else {
            return Response.status(401).build();
        }
    }
}