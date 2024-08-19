package io.gitlab.fundacti.inventarioti.rest.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import io.gitlab.fundacti.inventarioti.domain.model.Inventario;
import io.gitlab.fundacti.inventarioti.domain.model.Saida;
import io.gitlab.fundacti.inventarioti.domain.repository.InventarioRepository;
import io.gitlab.fundacti.inventarioti.domain.repository.SaidaRepository;
import io.gitlab.fundacti.inventarioti.rest.dto.CreateSaidaRequest;
import io.gitlab.fundacti.inventarioti.rest.dto.UpdateSaidaRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class SaidaService {

    @Inject
    SaidaRepository saidaRepository;

    @Inject
    InventarioRepository inventarioRepository;

    @Transactional
    public Saida createSaida(CreateSaidaRequest saidaRequest) {
        Saida saida = new Saida();
        Inventario inventario = inventarioRepository.findById(saidaRequest.getInventarioId());
        saida.setInventario(inventario);
        saida.setTipoSaida(saidaRequest.getTipoSaida());
        saida.setDataSaida(saidaRequest.getDataSaida());
        saida.setQuantidade(saidaRequest.getQuantidade());
        saida.setTermoSaida(saidaRequest.getTermoSaida());

        saidaRepository.persist(saida);
        return saida;
    }

    public List<Saida> listAll() {
        return saidaRepository.listAll();
    }

    @Transactional
    public Optional<Saida> updateSaida(Long id, UpdateSaidaRequest saidaRequest) {
        Optional<Saida> optionalSaida = saidaRepository.findByIdOptional(id);
        if (optionalSaida.isPresent()) {
            Saida saida = optionalSaida.get();
            Inventario inventario = inventarioRepository.findById(saidaRequest.getInventarioId());
            saida.setInventario(inventario);
            saida.setTipoSaida(saidaRequest.getTipoSaida());
            saida.setDataSaida(saidaRequest.getDataSaida());
            saida.setQuantidade(saidaRequest.getQuantidade());
            saida.setTermoSaida(saidaRequest.getTermoSaida());

            return Optional.of(saida);
        }
        return Optional.empty();
    }

    @Transactional
    public boolean deleteSaida(Long id) {
        return saidaRepository.deleteById(id);
    }

    public List<Saida> findByDate(LocalDate dataSaida) {
        return saidaRepository.find("dataSaida", dataSaida).list();
    } 

}
