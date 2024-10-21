package io.fundacti.inventarioti.rest;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import org.jboss.logging.Logger;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.fundacti.inventario.dto.EntradaDTO;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import jakarta.transaction.Transactional;

@QuarkusTest
public class EntradaResourceTest {

    private static final Logger LOG = Logger.getLogger(EntradaResourceTest.class);

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
    public void testCreateEntrada() {
        // Verificar se o inventarioId foi corretamente atribuído
        assertNotNull(inventarioId, "O Inventario ID não deveria ser nulo.");

        EntradaDTO request = new EntradaDTO();
        request.setInventarioId(inventarioId);
        request.setDataEntrada(LocalDate.now());
        request.setTipoEntrada("doacao");
        request.setQuantidade(10);
        request.setTermoRecebimento("Recebido dentro do esperado");

        given()
          .contentType("application/json")
          .body(request)
          .log().all() 
          .when().post("/api/entrada")
          .then()
             .statusCode(201)
             .body("termoRecebimento", is("Recebido dentro do esperado"));
    }

    @Test
    public void testListAllEntrada() {
        given()
          .when().get("/api/entrada")
          .then()
             .statusCode(200);
    }
}