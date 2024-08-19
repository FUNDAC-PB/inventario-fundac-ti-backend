package io.gitlab.fundacti.inventarioti.rest;

import java.time.LocalDate;
import java.util.Optional;

import io.gitlab.fundacti.inventarioti.domain.model.Saida;
import io.gitlab.fundacti.inventarioti.rest.dto.CreateSaidaRequest;
import io.gitlab.fundacti.inventarioti.rest.dto.UpdateSaidaRequest;
import io.gitlab.fundacti.inventarioti.rest.service.SaidaService;
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

@Path("/saida")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SaidaResource {

    @Inject
    SaidaService saidaService;

    @POST
    @RolesAllowed("user")
    public Response createSaida(CreateSaidaRequest saidaRequest) {
        Saida saida = saidaService.createSaida(saidaRequest);
        return Response.ok(saida).status(Response.Status.CREATED).build();
    }

    @GET
    @RolesAllowed("user")
    public Response listAllSaida() {
        return Response.ok(saidaService.listAll()).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("user")
    public Response updateSaida(@PathParam("id") Long id, UpdateSaidaRequest saidaRequest) {
        Optional<Saida> updatedSaida = saidaService.updateSaida(id, saidaRequest);
        return updatedSaida.map(saida -> Response.ok(saida).build())
                                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("user")
    public Response deleteSaida(@PathParam("id") Long id) {
        boolean deleted = saidaService.deleteSaida(id);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/search/dataSaida/{dataSaida}")
    @RolesAllowed("user")
    public Response findByDate(@PathParam("dataSaida") LocalDate dataSaida) {
        return Response.ok(saidaService.findByDate(dataSaida)).build();
    }


}