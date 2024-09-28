package io.fundacti.inventarioti.rest;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.fundacti.inventario.dto.InventarioDTO;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import jakarta.transaction.Transactional;

@QuarkusTest
public class InventarioResourceTest {

    private static final Logger LOG = Logger.getLogger(InventarioResourceTest.class);

    private Long lotacaoId;
    private Long setorId;
    private Long categoriaId;

    @BeforeEach
    public void setup() {
        lotacaoId = createEntity("/api/lotacao", "{\"nome\": \"Fundac\"}");
        setorId = createEntity("/api/setor", "{\"nome\": \"TI\"}");
        categoriaId = createEntity("/api/categoria", "{\"nome\": \"Microcomputador portatil\"}");

        LOG.infof("LotacaoId: %d, SetorId: %d, CategoriaId: %d", lotacaoId, setorId, categoriaId);
    }

    @Transactional
    protected Long createEntity(String datapoint, String body) {
        Integer id = given()
               .contentType("application/json")
               .body(body)
               .when().post(datapoint)
               .then()
               .statusCode(201)
               .extract().path("id");

               if (id == null) {
                LOG.errorf("Falha ao criar entidades no endpoint %s com body: %s", datapoint, body);
              } else {
                LOG.infof("Entidade criada no endpoint %s com ID: %d", datapoint, id);
              }
        
        return id != null ? id.longValue() : null; 
    }

    @TestTransaction
    @Test
    public void testAddItem() {
        InventarioDTO request = new InventarioDTO();
        request.setNome("Notebook Daten");
        request.setDescricao("Descrição do Notebook Daten");
        request.setTipo("equipamento");
        request.setLotacaoId(lotacaoId);
        request.setSetorId(setorId);
        request.setCategoriaId(categoriaId);
        request.setPatrimonio(UUID.randomUUID().toString());
        request.setNumeroSerie(UUID.randomUUID().toString());

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
          .when().get("/api/inventario")
          .then()
             .statusCode(200);
    }
}