package io.gitlab.fundacti.inventarioti.rest;

import java.time.LocalDate;
import java.util.Optional;

import io.gitlab.fundacti.inventarioti.domain.model.Entrada;
import io.gitlab.fundacti.inventarioti.rest.dto.CreateEntradaRequest;
import io.gitlab.fundacti.inventarioti.rest.dto.UpdateEntradaRequest;
import io.gitlab.fundacti.inventarioti.rest.service.EntradaService;
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

@Path("/entrada")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EntradaResource {

    @Inject
    EntradaService entradaService;

    @POST
    @RolesAllowed("user")
    public Response createEntrada(CreateEntradaRequest entradaRequest) {
        Entrada entrada = entradaService.createEntrada(entradaRequest);
        return Response.ok(entrada).status(Response.Status.CREATED).build();
    }

    @GET
    @RolesAllowed("user")
    public Response listAllEntrada() {
        return Response.ok(entradaService.listAll()).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("user")
    public Response updateEntrada(@PathParam("id") Long id, UpdateEntradaRequest entradaRequest) {
        Optional<Entrada> updatedEntrada = entradaService.updateEntrada(id, entradaRequest);
        return updatedEntrada.map(entrada -> Response.ok(entrada).build())
                                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("user")
    public Response deleteEntrada(@PathParam("id") Long id) {
        boolean deleted = entradaService.deleteEntrada(id);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/search/dataEntrada/{dataEntrada}")
    @RolesAllowed("user")
    public Response findByDate(@PathParam("dataEntrada") LocalDate dataEntrada) {
        return Response.ok(entradaService.findByDate(dataEntrada)).build();
    }


}