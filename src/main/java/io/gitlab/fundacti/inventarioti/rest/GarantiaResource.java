package io.gitlab.fundacti.inventarioti.rest;

import java.util.Optional;

import io.gitlab.fundacti.inventarioti.domain.model.Garantia;
import io.gitlab.fundacti.inventarioti.rest.dto.CreateGarantiaRequest;
import io.gitlab.fundacti.inventarioti.rest.dto.UpdateGarantiaRequest;
import io.gitlab.fundacti.inventarioti.rest.service.GarantiaService;
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

@Path("/garantia")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GarantiaResource {
    
    @Inject
    GarantiaService garantiaService;

    @POST
    @RolesAllowed("user")
    public Response createGarantia(CreateGarantiaRequest garantiaRequest) {
        Garantia garantia = garantiaService.createGarantia(garantiaRequest);
        return Response.ok(garantia).status(Response.Status.CREATED).build();
    }

    @GET
    @RolesAllowed("user")
    public Response listAllGarantia(){
        return Response.ok(garantiaService.listAll()).build();
    }
    
    @PUT
    @Path("/{id}")
    @RolesAllowed("user")
    public Response updateGarantia(@PathParam("id") Long id, UpdateGarantiaRequest garantiaRequest) {
        Optional<Garantia> updatedGarantia = garantiaService.updateGarantia(id, garantiaRequest);
        return updatedGarantia.map(garantia -> Response.ok(garantia).build())
                                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("user")
    public Response deleteGarantia(@PathParam("id") Long id) {
        boolean deleted = garantiaService.deleteGarantia(id);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
