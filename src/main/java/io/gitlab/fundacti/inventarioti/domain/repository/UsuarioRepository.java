package io.gitlab.fundacti.inventarioti.domain.repository;

import io.gitlab.fundacti.inventarioti.domain.model.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> {
    // Falta querys
}