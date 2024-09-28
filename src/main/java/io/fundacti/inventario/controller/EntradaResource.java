package io.fundacti.inventario.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import io.fundacti.inventario.domain.model.Entrada;
import io.fundacti.inventario.dto.EntradaDTO;
import io.fundacti.inventario.service.EntradaService;
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

@Path("/api/entrada")
@Tag(name = "Entradas", description = "Gerenciamento de Entradas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EntradaResource {

    @Inject
    EntradaService entradaService;

    @POST
	@Operation(summary = "Adiciona uma nova entrada",
               description = "Adiciona uma nova entrada")
    // @RolesAllowed("user,admin")
    public Response addEntrada(EntradaDTO entradaRequest) {
        Entrada entrada = entradaService.addEntrada(entradaRequest);
        return Response.ok(entrada).status(Response.Status.CREATED).build();
    }

    @GET
	@Operation(summary = "Listar todos as entradas",
               description = "Retorna uma lista de todas as entradas")
    // @RolesAllowed("user,admin")
    public Response listAllEntrada() {
        return Response.ok(entradaService.listAll()).build();
    }

    @PUT
    @Path("/{id}")
    // @RolesAllowed("user,admin")
    public Response updateEntrada(@PathParam("id") Long id, EntradaDTO entradaRequest) {
        Optional<Entrada> updatedEntrada = entradaService.updateEntrada(id, entradaRequest);
        return updatedEntrada.map(entrada -> Response.ok(entrada).build())
                                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}")
    // @RolesAllowed("user,admin")
    public Response deleteEntrada(@PathParam("id") Long id) {
        boolean deleted = entradaService.deleteEntrada(id);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/search/dataentrada/{dataEntrada}")
    // @RolesAllowed("user,admin")
    public Response findByDate(@PathParam("dataEntrada") String dataEntrada) {

        try {

            DateTimeFormatter DateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            LocalDate parseDate = LocalDate.parse(dataEntrada, DateFormatter);

            return Response.ok(entradaService.findByDate(parseDate)).build();
        } catch (DateTimeParseException e) {

            return Response.status(Response.Status.BAD_REQUEST).entity("Data Invalida" + dataEntrada).build();
        }
    }


}