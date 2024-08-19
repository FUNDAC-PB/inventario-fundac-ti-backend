package io.gitlab.fundacti.inventarioti.rest;

import java.util.Optional;

import io.gitlab.fundacti.inventarioti.domain.model.Inventario;
import io.gitlab.fundacti.inventarioti.rest.dto.CreateItemRequest;
import io.gitlab.fundacti.inventarioti.rest.dto.UpdateItemRequest;
import io.gitlab.fundacti.inventarioti.rest.service.InventarioService;
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

@Path("/inventario")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class InventarioResource {

    @Inject
    InventarioService inventarioService;

    @POST
    @RolesAllowed("user")
    public Response createItem(CreateItemRequest itemRequest) {
        Inventario inventario = inventarioService.createItem(itemRequest);
        return Response.ok(inventario).status(Response.Status.CREATED).build();
    }

    @GET
    @RolesAllowed("user")
    public Response listAllInventario() {
        return Response.ok(inventarioService.listAll()).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("user")
    public Response updateItem(@PathParam("id") Long id, UpdateItemRequest itemRequest) {
        Optional<Inventario> updatedInventario = inventarioService.updateItem(id, itemRequest);
        return updatedInventario.map(inventario -> Response.ok(inventario).build())
                                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("user")
    public Response deleteItem(@PathParam("id") Long id) {
        boolean deleted = inventarioService.deleteItem(id);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/search/nome/{nome}")
    @RolesAllowed("user")
    public Response findByNome(@PathParam("nome") String nome) {
        return Response.ok(inventarioService.findByNome(nome)).build();
    }

    @GET
    @Path("/search/tipo/{tipo}")
    @RolesAllowed("user")
    public Response findByTipo(@PathParam("tipo") String tipo) {
        return Response.ok(inventarioService.findByTipo(tipo)).build();
    }
}