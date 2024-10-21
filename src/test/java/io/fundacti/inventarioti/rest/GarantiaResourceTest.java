package io.fundacti.inventarioti.rest;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import org.jboss.logging.Logger;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.fundacti.inventario.dto.GarantiaDTO;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import jakarta.transaction.Transactional;

@QuarkusTest
public class GarantiaResourceTest {

    private static final Logger LOG = Logger.getLogger(GarantiaResourceTest.class);
    
    private Long inventarioId;

    @BeforeEach
    public void setup() {
        // Buscar um inventarioId existente no banco
        inventarioId = getDisponivelId("/api/inventario");
        assertNotNull(inventarioId, "O ID do inventário não deveria ser nulo, god please.");

        LOG.infof("InventarioId: %d", inventarioId);
    }

    // Método que busca o ID disponível no datapoint
    @Transactional
    protected Long getDisponivelId(String datapoint) {
        List<Integer> ids = given()
               .contentType("application/json")
               .when().get(datapoint)
               .then()
               .statusCode(200)
               .extract().jsonPath().getList("id", Integer.class);

        if (ids.isEmpty()) {
            LOG.errorf("Nenhum ID disponível no datapoint: %s", datapoint);
            return null;
        } else {
            LOG.infof("ID encontrado no datapoint %s: %d", datapoint, ids.get(0));
            return ids.get(0).longValue();
        }
    }

    @TestTransaction
    @Test
    public void testaddGarantia () {
        GarantiaDTO request = new GarantiaDTO();
        request.setInventarioId(inventarioId);
        request.setDetalhes("Garantia de 2 anos Notebook Daten");
        request.setDataInicio(LocalDate.now());
        request.setDataFim(LocalDate.now().plusYears(2));

        given()
          .contentType("application/json")
          .body(request)
          .when().post("/api/garantia")
          .then()
             .statusCode(201)
             .body("detalhes", is("Garantia de 2 anos Notebook Daten"));
    }

    @Test
    public void testListAllGarantia() {
        given()
          .when().get("/api/garantia")
          .then()
             .statusCode(200);
    }

}
