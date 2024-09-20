package io.fundacti.inventario.repo;

import java.time.LocalDate;
import java.util.Optional;

import io.fundacti.inventario.domain.model.Entrada;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EntradaRepository implements PanacheRepository<Entrada> {
        public Optional<Entrada> findByDataFim(LocalDate dataEntrada) {
        return find("dataEntrada", dataEntrada).firstResultOptional();
    }
}