package io.gitlab.fundacti.inventarioti.rest.service;

import java.util.HashSet;
import java.util.List;

import io.gitlab.fundacti.inventarioti.domain.model.Usuario;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;


@ApplicationScoped
public class LoginService {

    @Inject
    UsuarioService usuarioService;

    @Transactional
    public Usuario authenticate(String username, String password) {
        Usuario usuario = usuarioService.findByUsername(username);
        if (usuario != null && usuarioService.verifyPassword(password, usuario.password)) {
            return usuario;
        }
        return null;
    }

    public String generateToken(Usuario usuario) {
        return Jwt.issuer("alice-lock")
                  .upn(usuario.username)
                  .groups(new HashSet<>(List.of(usuario.role)))
                  .sign();
    }
}