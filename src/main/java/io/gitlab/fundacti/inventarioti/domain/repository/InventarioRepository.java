package io.gitlab.fundacti.inventarioti.domain.repository;

import io.gitlab.fundacti.inventarioti.domain.model.Inventario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class InventarioRepository implements PanacheRepository<Inventario> {
    // Falta querys
}