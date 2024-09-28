package io.fundacti.inventario.controller;

import java.util.Optional;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import io.fundacti.inventario.domain.model.Categoria;
import io.fundacti.inventario.dto.CategoriaDTO;
import io.fundacti.inventario.service.CategoriaService;
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

@Path("/api/categoria")
@Tag(name = "Categorias", description = "Gerenciamento de Categorias")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CategoriaResource {

    @Inject
    CategoriaService categoriaService;

    @POST
	@Operation(summary = "Adiciona uma nova categoria",
               description = "Adiciona uma nova categoria")
  //  @RolesAllowed("user,admin")
    public Response addCategoria(CategoriaDTO categoriaRequest) {
        Categoria categoria = categoriaService.addCategoria(categoriaRequest);
        return Response.ok(categoria).status(Response.Status.CREATED).build();
    }

    @GET
	@Operation(summary = "Listar todos as categorias",
               description = "Retorna uma lista de todas as categorias")
    // @RolesAllowed("user,admin")
    public Response listAll() {
        return Response.ok(categoriaService.listAll()).build();
    }

    @PUT
    @Path("/{id}")
    // @RolesAllowed("user,admin")
    public Response updateCategoria(@PathParam("id") Long id, CategoriaDTO categoriaRequest) {
        Optional<Categoria> updatedCategoria = categoriaService.updateCategoria(id, categoriaRequest);
        return updatedCategoria.map(categoria -> Response.ok(categoria).build())
                                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}")
    // @RolesAllowed("user,admin")
    public Response deleteCategoria(@PathParam("id") Long id) {
        boolean deleted = categoriaService.deleteCategoria(id);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/search/categoria/{nome}")
    // @RolesAllowed("user,admin")
    public Response findByNome(@PathParam("nome") String nome) {
        return Response.ok(categoriaService.findByNome(nome)).build();
    }


}