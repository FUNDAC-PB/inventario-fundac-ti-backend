package io.fundacti.inventario.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import io.fundacti.inventario.domain.model.Garantia;
import io.fundacti.inventario.dto.GarantiaDTO;
import io.fundacti.inventario.service.GarantiaService;
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

@Path("/api/garantia")
@Tag(name = "Garantias", description = "Gerenciamento de Garantias")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GarantiaResource {
    
    @Inject
    GarantiaService garantiaService;

    @POST
	@Operation(summary = "Adiciona uma nova garantia",
               description = "Adiciona uma nova garantia")
  //  @RolesAllowed("user,admin")
    public Response addGarantia(GarantiaDTO garantiaRequest) {
        Garantia garantia = garantiaService.addGarantia(garantiaRequest);
        return Response.ok(garantia).status(Response.Status.CREATED).build();
    }

    @GET
	@Operation(summary = "Listar todos as garantias",
               description = "Retorna uma lista de todas as garantias")
   // @RolesAllowed("user,admin")
    public Response listAllGarantia(){
        return Response.ok(garantiaService.listAll()).build();
    }
    
    @PUT
    @Path("/{id}")
    // @RolesAllowed("user,admin")
    public Response updateGarantia(@PathParam("id") Long id, GarantiaDTO garantiaRequest) {
        Optional<Garantia> updatedGarantia = garantiaService.updateGarantia(id, garantiaRequest);
        return updatedGarantia.map(garantia -> Response.ok(garantia).build())
                                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}")
    // @RolesAllowed("user,admin")
    public Response deleteGarantia(@PathParam("id") Long id) {
        boolean deleted = garantiaService.deleteGarantia(id);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/search/datagarantia/{dataFim}")
    // @RolesAllowed("user,admin")
    public Response findByDataFim(@PathParam("dataFim") String dataFim) {
         try {

            DateTimeFormatter DateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            LocalDate parseDate = LocalDate.parse(dataFim, DateFormatter);

            return Response.ok(garantiaService.findByDataFim(parseDate)).build();
        } catch (DateTimeParseException e) {

            return Response.status(Response.Status.BAD_REQUEST).entity("Data Invalida" + dataFim).build();
        }
    }
}
