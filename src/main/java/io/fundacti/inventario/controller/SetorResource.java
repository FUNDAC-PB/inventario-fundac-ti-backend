package io.fundacti.inventario.controller;

import java.util.Optional;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import io.fundacti.inventario.domain.model.Setor;
import io.fundacti.inventario.dto.SetorDTO;
import io.fundacti.inventario.service.SetorService;
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

@Path("/api/setor")
@Tag(name = "Setores", description = "Gerenciamento de Setores")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SetorResource {

    @Inject
    SetorService setorService;

    @POST
	@Operation(summary = "Adiciona um novo setor",
               description = "Adiciona um novo setor")
    //@RolesAllowed("user,admin")
    public Response addSetor(SetorDTO setorRequest) {
        Setor setor = setorService.addSetor(setorRequest);
        return Response.ok(setor).status(Response.Status.CREATED).build();
    }

    @GET
	@Operation(summary = "Listar todos os setores",
               description = "Retorna uma lista de todos os setores")
   // @RolesAllowed("user,admin")
    public Response listAll() {
        return Response.ok(setorService.listAll()).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("user,admin")
    public Response updateItem(@PathParam("id") Long id, SetorDTO setorRequest) {
        Optional<Setor> updatedSetor = setorService.updateItem(id, setorRequest);
        return updatedSetor.map(setor -> Response.ok(setor).build())
                                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("user,admin")
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
    @RolesAllowed("user,admin")
    public Response findByNome(@PathParam("nome") String nome) {
        return Response.ok(setorService.findByNome(nome)).build();
    }


}