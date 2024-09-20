package io.fundacti.inventario.repo;

import java.util.Optional;

import io.fundacti.inventario.domain.model.Lotacao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LotacaoRepository implements PanacheRepository<Lotacao> {
        public Optional<Lotacao> findByName(String nome) {
        return find("nome", nome).firstResultOptional();
    }
}