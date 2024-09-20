package io.fundacti.inventarioti.rest;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.fundacti.inventario.dto.SaidaDTO;
import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;

// Test table saida 

@QuarkusTest
public class SaidaResourceTest {

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
                    .when().post("/api/lotacao")
                    .then()
                    .statusCode(201)
                    .extract().path("id");

        setorId = given()
                    .contentType("application/json")
                    .body("{\"nome\": \"TI\"}")
                    .when().post("/api/setor")
                    .then()
                    .statusCode(201)
                    .extract().path("id");

        categoriaId = given()
                    .contentType("application/json")
                    .body("{\"nome\": \"Microcomputador portatil\"}")
                    .when().post("/api/categoria")
                    .then()
                    .statusCode(201)
                    .extract().path("id");
        
        inventarioId = given()
                    .contentType("application/json")
                    .body("{\"nome\": \"Notebook Daten\", \"descricao\": \"Descrição do Notebook Daten\", \"tipo\": \"equipamento\", \"lotacao_id\": " + lotacaoId + ", \"setor_id\": " + setorId + ", \"patrimonio\": \"456789\", \"numero_serie\": \"Null\", \"responsavel\": \"João\", \"categoria_id\": " + categoriaId + "}")
                    .when().post("/api/inventario")
                    .then()
                    .statusCode(201)
                    .extract().path("id");

        quantidade = 10L;

    }

    @Test
    public void testCreateSaida() {
        
        SaidaDTO request = new SaidaDTO();
        request.setInventarioId(inventarioId);
        request.setDataSaida(LocalDate.now().plusDays(30));
        request.setQuantidade(quantidade);
        request.setTermoSaida("Dado baixa");

        given()
          .contentType("application/json")
          .body(request)
          .when().post("/api/saida")
          .then()
             .statusCode(201)
             .body("inventario", is(inventarioId.intValue()));
    }

    @Test
    public void testListAllSaida() {
        given()
          .when().get("/api/saida")
          .then()
             .statusCode(200);
    }
}