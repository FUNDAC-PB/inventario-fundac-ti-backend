package io.gitlab.fundacti.inventarioti.rest;

import java.util.Optional;

import io.gitlab.fundacti.inventarioti.domain.model.Lotacao;
import io.gitlab.fundacti.inventarioti.rest.dto.CreateLotacaoRequest;
import io.gitlab.fundacti.inventarioti.rest.dto.UpdateLotacaoRequest;
import io.gitlab.fundacti.inventarioti.rest.service.LotacaoService;
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

@Path("/lotacao")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LotacaoResource {

    @Inject
    LotacaoService lotacaoService;

    @POST
    @RolesAllowed("user")
    public Response createLotacao(CreateLotacaoRequest lotacaoRequest) {
        Lotacao lotacao = lotacaoService.createLotacao(lotacaoRequest);
        return Response.ok(lotacao).status(Response.Status.CREATED).build();
    }

    @GET
    @RolesAllowed("user")
    public Response listAll() {
        return Response.ok(lotacaoService.listAll()).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("user")
    public Response updateLItem(@PathParam("id") Long id, UpdateLotacaoRequest lotacaoRequest) {
        Optional<Lotacao> updatedLotacao = lotacaoService.updateItem(id, lotacaoRequest);
        return updatedLotacao.map(lotacao -> Response.ok(lotacao).build())
                                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("user")
    public Response deleteItem(@PathParam("id") Long id) {
        boolean deleted = lotacaoService.deleteItem(id);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
 
    @GET
    @Path("/search/lotacao/{nome}")
    @RolesAllowed("user")
    public Response findByNome(@PathParam("nome") String nome) {
        return Response.ok(lotacaoService.findByNome(nome)).build();
    }


}