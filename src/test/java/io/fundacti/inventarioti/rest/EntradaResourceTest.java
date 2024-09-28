package io.fundacti.inventarioti.rest;

import java.time.LocalDate;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import org.jboss.logging.Logger;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.fundacti.inventario.dto.EntradaDTO;
import io.fundacti.inventario.dto.InventarioDTO;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import jakarta.transaction.Transactional;

// Test table entrada

@QuarkusTest
public class EntradaResourceTest {

    private static final Logger LOG = Logger.getLogger(InventarioResourceTest.class);

    private Integer inventarioId;
    private Long quantidade;
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
        
        quantidade = 10L;

        LOG.infof("LotacaoId: %d, SetorId: %d, CategoriaId: %d, Inventario: %d, Quantidade: %d", lotacaoId, setorId, categoriaId, inventarioId, quantidade);
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
    public void testCreateEntrada() {
       // Verificar se o inventarioId foi corretamente atribuído
        assertNotNull(inventarioId, "O Inventario ID não deveria ser nulo.");

        EntradaDTO request = new EntradaDTO();
        request.setInventarioId(inventarioId.longValue());
        request.setDataEntrada(LocalDate.now());
        request.setQuantidade(quantidade);
        request.setTermoRecebimento("Recebido dentro do esperado");

        given()
          .contentType("application/json")
          .body(request)
          .when().post("/api/entrada")
          .then()
             .statusCode(201)
             .body("inventario.id", is(inventarioId));
    }

    @Test
    public void testListAllEntrada() {
        given()
          .when().get("/api/entrada")
          .then()
             .statusCode(200);
    }
}