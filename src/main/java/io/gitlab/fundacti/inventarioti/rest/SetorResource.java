package io.gitlab.fundacti.inventarioti.rest;

import java.util.Optional;

import io.gitlab.fundacti.inventarioti.domain.model.Setor;
import io.gitlab.fundacti.inventarioti.rest.dto.CreateSetorRequest;
import io.gitlab.fundacti.inventarioti.rest.dto.UpdateSetorRequest;
import io.gitlab.fundacti.inventarioti.rest.service.SetorService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/setor")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SetorResource {

    @Inject
    SetorService setorService;

    @POST
    @RolesAllowed("user")
    public Response createSetor(CreateSetorRequest setorRequest) {
        Setor setor = setorService.createSetor(setorRequest);
        return Response.ok(setor).status(Response.Status.CREATED).build();
    }

    @GET
    @RolesAllowed("user")
    public Response listAll() {
        return Response.ok(setorService.listAll()).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("user")
    public Response updateItem(@PathParam("id") Long id, UpdateSetorRequest setorRequest) {
        Optional<Setor> updatedSetor = setorService.updateItem(id, setorRequest);
        return updatedSetor.map(setor -> Response.ok(setor).build())
                                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("user")
    public Response deleteItem(@PathParam("id") Long id) {
        boolean deleted = setorService.deleteItem(id);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    @GET
    @Path("/search/setor/{nome}")
    @RolesAllowed("user")
    public Response findByNome(@PathParam("nome") String nome) {
        return Response.ok(setorService.findByNome(nome)).build();
    }


}