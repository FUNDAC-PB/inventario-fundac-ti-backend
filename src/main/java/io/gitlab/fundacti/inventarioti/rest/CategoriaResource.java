package io.gitlab.fundacti.inventarioti.rest;

import java.util.Optional;

import io.gitlab.fundacti.inventarioti.domain.model.Categoria;
import io.gitlab.fundacti.inventarioti.rest.dto.CreateCategoriaRequest;
import io.gitlab.fundacti.inventarioti.rest.dto.UpdateCategoriaRequest;
import io.gitlab.fundacti.inventarioti.rest.service.CategoriaService;
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

@Path("/categoria")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CategoriaResource {

    @Inject
    CategoriaService categoriaService;

    @POST
    @RolesAllowed("user")
    public Response createCategoria(CreateCategoriaRequest categoriaRequest) {
        Categoria categoria = categoriaService.createCategoria(categoriaRequest);
        return Response.ok(categoria).status(Response.Status.CREATED).build();
    }

    @GET
    @RolesAllowed("user")
    public Response listAll() {
        return Response.ok(categoriaService.listAll()).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("user")
    public Response updateItem(@PathParam("id") Long id, UpdateCategoriaRequest categoriaRequest) {
        Optional<Categoria> updatedCategoria = categoriaService.updateItem(id, categoriaRequest);
        return updatedCategoria.map(categoria -> Response.ok(categoria).build())
                                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("user")
    public Response deleteItem(@PathParam("id") Long id) {
        boolean deleted = categoriaService.deleteItem(id);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/search/categoria/{nome}")
    @RolesAllowed("user")
    public Response findByNome(@PathParam("nome") String nome) {
        return Response.ok(categoriaService.findByNome(nome)).build();
    }


}