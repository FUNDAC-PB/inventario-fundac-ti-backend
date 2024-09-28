package io.fundacti.inventario.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import io.fundacti.inventario.domain.model.Saida;
import io.fundacti.inventario.dto.SaidaDTO;
import io.fundacti.inventario.service.SaidaService;
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

@Path("/api/saida")
@Tag(name = "Saidas", description = "Gerenciamento de Saidas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SaidaResource {

    @Inject
    SaidaService saidaService;

    @POST
	@Operation(summary = "Adiciona uma nova saida",
               description = "Adiciona uma nova saida")
  //  @RolesAllowed("user,admin")
    public Response addSaida(SaidaDTO saidaRequest) {
        Saida saida = saidaService.addSaida(saidaRequest);
        return Response.ok(saida).status(Response.Status.CREATED).build();
    }

    @GET
	@Operation(summary = "Listar todos as saidas",
               description = "Retorna uma lista de todas as saidas")
  //  @RolesAllowed("user,admin")
    public Response listAllSaida() {
        return Response.ok(saidaService.listAll()).build();
    }

    @PUT
    @Path("/{id}")
  //  @RolesAllowed("user,admin")
    public Response updateSaida(@PathParam("id") Long id, SaidaDTO saidaRequest) {
        Optional<Saida> updatedSaida = saidaService.updateSaida(id, saidaRequest);
        return updatedSaida.map(saida -> Response.ok(saida).build())
                                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}")
   // @RolesAllowed("user,admin")
    public Response deleteSaida(@PathParam("id") Long id) {
        boolean deleted = saidaService.deleteSaida(id);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/search/datasaida/{dataSaida}")
   // @RolesAllowed("user,admin")
    public Response findByDate(@PathParam("dataSaida") String dataSaida) {

        try {

            DateTimeFormatter DateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            LocalDate parseDate = LocalDate.parse(dataSaida, DateFormatter);

            return Response.ok(saidaService.findByDate(parseDate)).build();
        } catch (DateTimeParseException e) {

            return Response.status(Response.Status.BAD_REQUEST).entity("Data Invalida" + dataSaida).build();
        }
    }


}