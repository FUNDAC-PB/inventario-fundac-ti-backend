package io.gitlab.fundacti.inventarioti.domain.repository;

import io.gitlab.fundacti.inventarioti.domain.model.Entrada;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EntradaRepository implements PanacheRepository<Entrada> {
    // more querys
}