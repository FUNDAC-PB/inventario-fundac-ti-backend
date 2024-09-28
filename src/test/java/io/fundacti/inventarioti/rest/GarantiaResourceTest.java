package io.fundacti.inventarioti.rest;

import java.time.LocalDate;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.fundacti.inventario.dto.GarantiaDTO;
import io.fundacti.inventario.dto.InventarioDTO;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import jakarta.transaction.Transactional;

@QuarkusTest
public class GarantiaResourceTest {

    private static final Logger LOG = Logger.getLogger(InventarioResourceTest.class);
    
    private Integer inventarioId;
    private Long setorId;
    private Long categoriaId;
    private Long lotacaoId;

    @BeforeEach
    public void setup () {
        lotacaoId = createEntity("/api/lotacao", "{\"nome\": \"Fundac\"}");

        setorId = createEntity("/api/setor","{\"nome\": \"TI\"}");

        categoriaId = createEntity("/api/categoria", "{\"nome\": \"Microcomputador portatil\"}");

        // InventarioDTO temporario para persistencia dos IDs
        InventarioDTO inventarioDTO = new InventarioDTO();
        inventarioDTO.setNome("Notebook Daten");
        inventarioDTO.setDescricao("Descrição do Notebook Daten");
        inventarioDTO.setTipo("equipamento");
        inventarioDTO.setPatrimonio(UUID.randomUUID().toString());
        inventarioDTO.setNumeroSerie(UUID.randomUUID().toString());
        inventarioDTO.setResponsavel("João");
        inventarioDTO.setLotacaoId(lotacaoId);
        inventarioDTO.setSetorId(setorId);
        inventarioDTO.setCategoriaId(categoriaId);
        
        inventarioId = given()
               .contentType("application/json")
               .body(inventarioDTO)
               .when().post("/api/inventario")
               .then()
               .statusCode(201)
               .extract().path("id");

        LOG.infof("LotacaoId: %d, SetorId: %d, CategoriaId: %d, Inventario: %d", lotacaoId, setorId, categoriaId, inventarioId);
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
    public void testaddGarantia () {
        GarantiaDTO request = new GarantiaDTO();
        request.setInventarioId(inventarioId.longValue());
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
