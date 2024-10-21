package io.fundacti.inventario.controller;

import java.util.Optional;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import io.fundacti.inventario.domain.model.Lotacao;
import io.fundacti.inventario.dto.LotacaoDTO;
import io.fundacti.inventario.service.LotacaoService;
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

@Path("/api/lotacao")
@Tag(name = "Lotacoes", description = "Gerenciamento de Lotacoes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LotacaoResource {

    @Inject
    LotacaoService lotacaoService;

    @POST
	@Operation(summary = "Adiciona uma nova lotacao",
               description = "Adiciona uma nova lotacao")
   @RolesAllowed("user,admin")
    public Response addLotacao(LotacaoDTO lotacaoRequest) {
        Lotacao lotacao = lotacaoService.addLotacao(lotacaoRequest);
        return Response.ok(lotacao).status(Response.Status.CREATED).build();
    }

    @GET
	@Operation(summary = "Listar todos as lotacoes",
               description = "Retorna uma lista de todas as lotacoes")
    @RolesAllowed("user,admin")
    public Response listAll() {
        return Response.ok(lotacaoService.listAll()).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("user,admin")
    public Response updateLLotacao(@PathParam("id") Long id, LotacaoDTO lotacaoRequest) {
        Optional<Lotacao> updatedLotacao = lotacaoService.updateLotacao(id, lotacaoRequest);
        return updatedLotacao.map(lotacao -> Response.ok(lotacao).build())
                                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("user,admin")
    public Response deleteLotacao(@PathParam("id") Long id) {
        boolean deleted = lotacaoService.deleteLotacao(id);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
 
    @GET
    @Path("/search/lotacao/{nome}")
    @RolesAllowed("user,admin")
    public Response findByNome(@PathParam("nome") String nome) {
        return Response.ok(lotacaoService.findByNome(nome)).build();
    }


}