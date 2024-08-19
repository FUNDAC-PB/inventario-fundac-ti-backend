package io.gitlab.fundacti.inventarioti.rest.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import io.gitlab.fundacti.inventarioti.domain.model.Entrada;
import io.gitlab.fundacti.inventarioti.domain.model.Inventario;
import io.gitlab.fundacti.inventarioti.domain.repository.EntradaRepository;
import io.gitlab.fundacti.inventarioti.domain.repository.InventarioRepository;
import io.gitlab.fundacti.inventarioti.rest.dto.CreateEntradaRequest;
import io.gitlab.fundacti.inventarioti.rest.dto.UpdateEntradaRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class EntradaService {

    @Inject
    EntradaRepository entradaRepository;

    @Inject
    InventarioRepository inventarioRepository;

    @Transactional
    public Entrada createEntrada(CreateEntradaRequest entradaRequest) {
        Entrada entrada = new Entrada();
        Inventario inventario = inventarioRepository.findById(entradaRequest.getInventarioId());
        entrada.setInventario(inventario);
        entrada.setTipoEntrada(entradaRequest.getTipoEntrada());
        entrada.setDataEntrada(entradaRequest.getDataEntrada());
        entrada.setQuantidade(entradaRequest.getQuantidade());
        entrada.setTermoRecebimento(entradaRequest.getTermoRecebimento());

        entradaRepository.persist(entrada);
        return entrada;
    }

    public List<Entrada> listAll() {
        return entradaRepository.listAll();
    }

    @Transactional
    public Optional<Entrada> updateEntrada(Long id, UpdateEntradaRequest entradaRequest) {
        Optional<Entrada> optionalEntrada = entradaRepository.findByIdOptional(id);
        if (optionalEntrada.isPresent()) {
            Entrada entrada = optionalEntrada.get();
            Inventario inventario = inventarioRepository.findById(entradaRequest.getInventarioId());
            entrada.setInventario(inventario);
            entrada.setTipoEntrada(entradaRequest.getTipoEntrada());
            entrada.setDataEntrada(entradaRequest.getDataEntrada());
            entrada.setQuantidade(entradaRequest.getQuantidade());
            entrada.setTermoRecebimento(entradaRequest.getTermoRecebimento());

            return Optional.of(entrada);
        }
        return Optional.empty();
    }

    @Transactional
    public boolean deleteEntrada(Long id) {
        return entradaRepository.deleteById(id);
    }

    public List<Entrada> findByDate(LocalDate dataEntrada) {
        return entradaRepository.find("dataEntrada", dataEntrada).list();
    } 

}
