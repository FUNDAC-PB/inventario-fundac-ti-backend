package io.gitlab.fundacti.inventarioti.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.gitlab.fundacti.inventarioti.domain.model.Usuario;
import io.gitlab.fundacti.inventarioti.rest.dto.CreateUsuarioRequest;
import io.gitlab.fundacti.inventarioti.rest.dto.LoginRequest;
import io.gitlab.fundacti.inventarioti.rest.service.LoginService;
import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@QuarkusTest
public class LoginResourceTest {

    @Inject
    LoginService loginService;

    @BeforeEach
    @Transactional // Pre register
    public void setup() {
        
        CreateUsuarioRequest request = new CreateUsuarioRequest();
            request.setUsername("testusuario");
            request.setPassword("$2a$10$DowJj8QK1K8jvP1F6Y2c8eJf9m6A9NwZkV/9e11uyZpFQd6ix5GzG");
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
                .body("password", notNullValue());

    }

    @Test // if okok
    @Transactional
    public void testLogin() {
        Usuario usuario = new Usuario();
        usuario.username = "testusuario";
        usuario.password = "$2a$10$DowJj8QK1K8jvP1F6Y2c8eJf9m6A9NwZkV/9e11uyZpFQd6ix5GzG"; // hash maroto
        usuario.role = "user";
        usuario.persist();

        LoginRequest request = new LoginRequest();
        request.setUsername("testusuario");
        request.setPassword("password");

        given()
            .contentType("application/json")
            .body(request)
            .when()
            .post("/login")
            .then()
            .statusCode(200)
            .body("token", notNullValue());
    }

    @Test // if pau
    public void testLoginFailure() {
        LoginRequest request = new LoginRequest();
        request.setUsername("nonexistentusuario");
        request.setPassword("wrongpassword");

        given()
            .contentType("application/json")
            .body(request)
            .when()
            .post("/login")
            .then()
            .statusCode(401);
    }
}