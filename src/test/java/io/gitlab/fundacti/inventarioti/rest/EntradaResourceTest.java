package io.gitlab.fundacti.inventarioti.rest;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.gitlab.fundacti.inventarioti.rest.dto.CreateEntradaRequest;
import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;

// Test table entrada

@QuarkusTest
public class EntradaResourceTest {

    private Long inventarioId;
    private Long quantidade;
    private Long setorId;
    private Long categoriaId;
    private Long lotacaoId;

    @BeforeEach
    public void setup () {
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
        
        inventarioId = given()
                    .contentType("application/json")
                    .body("{\"nome\": \"Notebook Daten\", \"descricao\": \"Descrição do Notebook Daten\", \"tipo\": \"equipamento\", \"lotacao_id\": " + lotacaoId + ", \"setor_id\": " + setorId + ", \"patrimonio\": \"456789\", \"numero_serie\": \"Null\", \"responsavel\": \"João\", \"categoria_id\": " + categoriaId + "}")
                    .when().post("/inventario")
                    .then()
                    .statusCode(201)
                    .extract().path("id");

        quantidade = 10L;

    }

    @Test
    public void testCreateEntrada() {
        
        CreateEntradaRequest request = new CreateEntradaRequest();
        request.setInventarioId(inventarioId);
        request.setDataEntrada(LocalDate.now());
        request.setQuantidade(quantidade);
        request.setTermoRecebimento("Recebido dendo do esperado");

        given()
          .contentType("application/json")
          .body(request)
          .when().post("/entrada")
          .then()
             .statusCode(201)
             .body("inventario", is(inventarioId.intValue()));
    }

    @Test
    public void testListAllEntrada() {
        given()
          .when().get("/entrada")
          .then()
             .statusCode(200);
    }
}