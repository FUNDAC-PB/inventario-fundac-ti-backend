package io.fundacti.inventario.repo;

import java.util.Optional;

import io.fundacti.inventario.domain.model.Categoria;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CategoriaRepository implements PanacheRepository<Categoria> {

    public Optional<Categoria> findByName(String nome) {
        return find("nome", nome).firstResultOptional();
    }
}