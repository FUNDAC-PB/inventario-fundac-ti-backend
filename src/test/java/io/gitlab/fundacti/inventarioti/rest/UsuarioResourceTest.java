package io.gitlab.fundacti.inventarioti.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import org.junit.jupiter.api.Test;

import io.gitlab.fundacti.inventarioti.domain.model.Usuario;
import io.gitlab.fundacti.inventarioti.rest.dto.CreateUsuarioRequest;
import io.gitlab.fundacti.inventarioti.rest.dto.UpdateUsuarioRequest;
import io.gitlab.fundacti.inventarioti.rest.service.UsuarioService;
import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@QuarkusTest
public class UsuarioResourceTest {
    
        @Inject
        UsuarioService usuarioService;
    
        @Test
        public void testRegistroUsuario() {
            CreateUsuarioRequest request = new CreateUsuarioRequest();
            request.setUsername("testusuario");
            request.setPassword("password");
            request.setRole("user");
    
            given()
                .contentType("application/json")
                .body(request)
                .when()
                .post("/usuarios/registro")
                .then()
                .statusCode(201)
                .body("username", is("testusuario"))
                .body("role", is("user"))
                .body("password", notNullValue()); // Confirms password is set
        }
    
        @Test
        @Transactional
        public void testUpdateUsuario() {
            Usuario usuario = new Usuario();
            usuario.username = "testusuario";
            usuario.password = "password";
            usuario.role = "user";
            usuario.persist();
    
            UpdateUsuarioRequest request = new UpdateUsuarioRequest();
            request.setUsername("updatedusuario");
            request.setPassword("newpassword");
            request.setRole("admin");
    
            given()
                .contentType("application/json")
                .body(request)
                .when()
                .put("/usuarios/" + usuario.id)
                .then()
                .statusCode(204);
    
            Usuario updatedUsuario = Usuario.findById(usuario.id);
            assert updatedUsuario != null;
            assert updatedUsuario.username.equals("updatedusuario");
            assert updatedUsuario.role.equals("admin");
            assert usuarioService.verifyPassword("newpassword", updatedUsuario.password); // check password update
        }

        @Test
        public void testRegistroUsuarioAdmin() {
            CreateUsuarioRequest request = new CreateUsuarioRequest();
            request.setUsername("testusuario");
            request.setPassword("password");
            request.setRole("admin");
    
            given()
                .contentType("application/json")
                .body(request)
                .when()
                .post("/usuarios/registro")
                .then()
                .statusCode(201)
                .body("username", is("testusuario"))
                .body("role", is("admin"))
                .body("password", notNullValue()); // Confirms password is set
        }
    
        @Test
        @Transactional
        public void testUpdateUsuarioAdmin() {
            Usuario usuario = new Usuario();
            usuario.username = "testusuario";
            usuario.password = "password";
            usuario.role = "admin";
            usuario.persist();
    
            UpdateUsuarioRequest request = new UpdateUsuarioRequest();
            request.setUsername("updatedusuario");
            request.setPassword("newpassword");
            request.setRole("admin");
    
            given()
                .contentType("application/json")
                .body(request)
                .when()
                .put("/usuarios/" + usuario.id)
                .then()
                .statusCode(204);
    
            Usuario updatedUsuario = Usuario.findById(usuario.id);
            assert updatedUsuario != null;
            assert updatedUsuario.username.equals("updatedusuario");
            assert updatedUsuario.role.equals("admin");
            assert usuarioService.verifyPassword("newpassword", updatedUsuario.password); // check password update
        }
    }