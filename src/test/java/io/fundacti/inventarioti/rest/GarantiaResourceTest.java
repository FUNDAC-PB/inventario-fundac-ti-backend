package io.fundacti.inventarioti.rest;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.fundacti.inventario.dto.GarantiaDTO;
import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;

@QuarkusTest
public class GarantiaResourceTest {
    
    private Long inventarioId;
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


    }

    @Test
    public void testaddGarantia () {
        GarantiaDTO request = new GarantiaDTO();
        request.setInventarioId(inventarioId);
        request.setDetalhes("Garantia de 2 anos");
        request.setDataInicio(LocalDate.now());
        request.setDataFim(LocalDate.now().plusYears(2));

        given()
          .contentType("application/json")
          .body(request)
          .when().post("/api/garantia")
          .then()
             .statusCode(201)
             .body("detalhes", is("Garantia Notebook Daten"));
    }

    @Test
    public void testListAllGarantia() {
        given()
          .when().get("/api/garantia")
          .then()
             .statusCode(200);
    }

}
