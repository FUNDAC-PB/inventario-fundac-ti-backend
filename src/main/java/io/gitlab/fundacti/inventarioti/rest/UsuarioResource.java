package io.gitlab.fundacti.inventarioti.rest;

import io.gitlab.fundacti.inventarioti.domain.model.Usuario;
import io.gitlab.fundacti.inventarioti.rest.dto.CreateUsuarioRequest;
import io.gitlab.fundacti.inventarioti.rest.dto.UpdateUsuarioRequest;
import io.gitlab.fundacti.inventarioti.rest.service.UsuarioService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@Path("/usuarios")
@Produces("application/json")
@Consumes("application/json")
public class UsuarioResource {

    @Inject
    UsuarioService usuarioService;

    @POST
    @Path("/registro")
    @RolesAllowed("admin")
    public Response register(CreateUsuarioRequest request) {
        Usuario usuario = usuarioService.createUser(request);
        return Response.status(201).entity(usuario).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("admin")
    public Response update(@PathParam("id") Long id, UpdateUsuarioRequest request) {
        usuarioService.updateUser(id, request);
        return Response.noContent().build();
    }
}