package io.fundacti.inventario.repo;

import java.time.LocalDate;
import java.util.Optional;

import io.fundacti.inventario.domain.model.Garantia;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GarantiaRepository implements PanacheRepository<Garantia> {
    public Optional<Garantia> findByDataFim(LocalDate dataFim) {
        return find("dataFim", dataFim).firstResultOptional();
    }
}