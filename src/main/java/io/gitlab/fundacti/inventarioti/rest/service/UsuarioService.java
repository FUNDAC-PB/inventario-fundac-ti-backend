package io.gitlab.fundacti.inventarioti.rest.service;

import org.mindrot.jbcrypt.BCrypt;

import io.gitlab.fundacti.inventarioti.domain.model.Usuario;
import io.gitlab.fundacti.inventarioti.rest.dto.CreateUsuarioRequest;
import io.gitlab.fundacti.inventarioti.rest.dto.UpdateUsuarioRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UsuarioService {

    @Transactional
    public Usuario createUser(CreateUsuarioRequest request) {
        Usuario usuario = new Usuario();
        usuario.username = request.getUsername();
        usuario.password = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());
        usuario.role = request.getRole();
        usuario.persist();
        return usuario;
    }

    @Transactional
    public void updateUser(Long usuarioId, UpdateUsuarioRequest request) {
        Usuario usuario = Usuario.findById(usuarioId);
        if (usuario != null) {
            usuario.username = request.getUsername();
            if (request.getPassword() != null) {
                usuario.password = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());
            }
            if (request.getRole() != null) {
                usuario.role = request.getRole();
            }
            usuario.persist();
        }
    }

    public Usuario findByUsername(String username) {
        return Usuario.find("username", username).firstResult();
    }

    public boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}