package io.gitlab.fundacti.inventarioti.domain.repository;

import io.gitlab.fundacti.inventarioti.domain.model.Setor;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SetorRepository implements PanacheRepository<Setor> {
    // more querys
}