package io.fundacti.inventario.controller;

import java.util.Optional;
import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import io.fundacti.inventario.domain.model.Inventario;
import io.fundacti.inventario.dto.InventarioDTO;
import io.fundacti.inventario.service.InventarioService;
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

@Path("/api/inventario")
@Tag(name = "Inventário", description = "Gerenciamento de Inventário")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class InventarioResource {

    @Inject
    InventarioService inventarioService;

    @POST
	@Operation(summary = "Adiciona um novo item",
               description = "Adiciona um novo item ao inventário")
    @RolesAllowed("user,admin")
    public Response addItem(InventarioDTO itemRequest) {
        Inventario inventario = inventarioService.addItem(itemRequest);
        return Response.status(Response.Status.CREATED).entity(inventario).build();
    }

    @GET
	@Operation(summary = "Listar todos os itens",
               description = "Retorna uma lista de todos os itens do inventário")
    @RolesAllowed("user,admin")
    public Response listAllInventario() {
        List<Inventario> inventarios = inventarioService.listAll();
        return Response.ok(inventarios).build();
    }

    @PUT
	@Operation(summary = "Atualiza um item",
               description = "Atualiza um item ao inventário")
    @Path("/{id}")
    @RolesAllowed("user,admin")
    public Response updateItem(@PathParam("id") Long id, InventarioDTO itemRequest) {
        Optional<Inventario> updatedInventario = inventarioService.updateItem(id, itemRequest);
        return updatedInventario.map(inventario -> Response.ok(inventario).build())
                                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
	@Operation(summary = "Deleta um item",
               description = "Deleta um item ao inventário")
    @Path("/{id}")
    @RolesAllowed("user,admin")
    public Response deleteItem(@PathParam("id") Long id) {
        boolean deleted = inventarioService.deleteItem(id);
        return deleted ? Response.noContent().build() : Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
	@Operation(summary = "Buscar item por nome",
               description = "Busca um item por nome no inventário")
    @Path("/search/nome/{nome}")
    @RolesAllowed("user,admin")
    public Response findByNome(@PathParam("nome") String nome) {
        return Response.ok(inventarioService.findByNome(nome)).build();
    }

    @GET
	@Operation(summary = "Buscar item por tipo",
               description = "Busca um item por tipo no inventário")
    @Path("/search/tipo/{tipo}")
    @RolesAllowed("user,admin")
    public Response findByTipo(@PathParam("tipo") String tipo) {
        return Response.ok(inventarioService.findByTipo(tipo)).build();
    }
}