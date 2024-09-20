package io.fundacti.inventario.repo;

import java.time.LocalDate;
import java.util.Optional;

import io.fundacti.inventario.domain.model.Saida;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SaidaRepository implements PanacheRepository<Saida> {
    public Optional<Saida> findByDataFim(LocalDate dataSaida) {
        return find("dataSaida", dataSaida).firstResultOptional();
    }
}