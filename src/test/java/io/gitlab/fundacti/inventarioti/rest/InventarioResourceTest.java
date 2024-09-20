package io.gitlab.fundacti.inventarioti.rest;

import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.gitlab.fundacti.inventarioti.rest.dto.CreateItemRequest;
import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;

// Test Table inventario

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
    public void testCreateItem() {
        CreateItemRequest request = new CreateItemRequest();
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
          .when().post("/inventario")
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