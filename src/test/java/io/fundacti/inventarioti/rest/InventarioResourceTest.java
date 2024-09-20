package io.fundacti.inventarioti.rest;

import static org.hamcrest.CoreMatchers.is;
import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.fundacti.inventario.dto.InventarioDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;

@QuarkusTest
public class InventarioResourceTest {

    private Long lotacaoId;
    private Long setorId;
    private Long categoriaId;

    @BeforeEach
    public void setup() {
        lotacaoId = given()
                .contentType("application/json")
                .body("{\"nome\": \"Fundac\"}")
                .when().post("/lotacao")
                .then()
                .statusCode(201)
                .extract().path("id");

        setorId = given()
                .contentType("application/json")
                .body("{\"nome\": \"TI\"}")
                .when().post("/setor")
                .then()
                .statusCode(201)
                .extract().path("id");

        categoriaId = given()
                .contentType("application/json")
                .body("{\"nome\": \"Microcomputador portatil\"}")
                .when().post("/categoria")
                .then()
                .statusCode(201)
                .extract().path("id");
    }

    @Test
    public void testAddItem() {
        InventarioDTO request = new InventarioDTO();
        request.setNome("Notebook Daten");
        request.setDescricao("Descrição do Notebook Daten");
        request.setTipo("equipamento");
        request.setLotacaoId(lotacaoId);
        request.setSetorId(setorId);
        request.setCategoriaId(categoriaId);
        request.setPatrimonio("12345");
        request.setNumeroSerie("67890");

        given()
          .contentType("application/json")
          .body(request)
          .when().post("/api/inventario")
          .then()
             .statusCode(201)
             .body("nome", is("Notebook Daten"));
    }

    @Test
    public void testListAllInventario() {
        given()
          .when().get("/inventario")
          .then()
             .statusCode(200);
    }
}