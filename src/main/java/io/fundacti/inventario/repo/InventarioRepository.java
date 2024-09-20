package io.fundacti.inventario.repo;

import java.util.Optional;

import io.fundacti.inventario.domain.model.Inventario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class InventarioRepository implements PanacheRepository<Inventario> {

    public Optional<Inventario> findByNumeroSerie(String numeroSerie) {
        return find("numeroSerie", numeroSerie).firstResultOptional();
    }

    public Optional<Inventario> findByPatrimonio(String patrimonio) {
        return find("patrimonio", patrimonio).firstResultOptional();
    }
}