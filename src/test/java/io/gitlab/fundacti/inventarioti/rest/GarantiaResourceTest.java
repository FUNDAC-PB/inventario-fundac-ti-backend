package io.gitlab.fundacti.inventarioti.rest;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.gitlab.fundacti.inventarioti.rest.dto.CreateGarantiaRequest;
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


    }

    @Test
    public void testcreateGarantia () {
        CreateGarantiaRequest request = new CreateGarantiaRequest();
        request.setInventarioId(inventarioId);
        request.setDetalhes("Garantia de 2 anos");
        request.setDataInicio(LocalDate.now());
        request.setDataFim(LocalDate.now().plusYears(2));

        given()
          .contentType("application/json")
          .body(request)
          .when().post("/garantia")
          .then()
             .statusCode(201)
             .body("detalhes", is("Garantia Notebook Daten"));
    }

    @Test
    public void testListAllGarantia() {
        given()
          .when().get("/garantia")
          .then()
             .statusCode(200);
    }

}
