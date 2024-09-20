package io.fundacti.inventario.repo;

import java.util.Optional;

import io.fundacti.inventario.domain.model.Setor;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SetorRepository implements PanacheRepository<Setor> {
        public Optional<Setor> findByName(String nome) {
        return find("nome", nome).firstResultOptional();
    }
}