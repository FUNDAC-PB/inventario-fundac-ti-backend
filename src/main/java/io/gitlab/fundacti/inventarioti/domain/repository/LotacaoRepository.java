package io.gitlab.fundacti.inventarioti.domain.repository;

import io.gitlab.fundacti.inventarioti.domain.model.Lotacao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LotacaoRepository implements PanacheRepository<Lotacao> {
    // more querys
}