package io.fundacti.inventario.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import io.fundacti.inventario.repo.EntradaRepository;
import io.fundacti.inventario.domain.model.Entrada;
import io.fundacti.inventario.domain.model.Inventario;
import io.fundacti.inventario.dto.EntradaDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class EntradaService {

    public final EntradaRepository entradaRepo;

    public EntradaService(EntradaRepository entradaRepo) {
        this.entradaRepo = entradaRepo;
    }

    @Transactional
    public Entrada addEntrada(EntradaDTO entradaDTO) {
        Entrada entrada = new Entrada(null);
        entrada.setTipoEntrada(entradaDTO.getTipoEntrada());
        entrada.setDataEntrada(entradaDTO.getDataEntrada());
        entrada.setQuantidade(entradaDTO.getQuantidade());
        entrada.setTermoRecebimento(entradaDTO.getTermoRecebimento());
        entrada.setInventario(new Inventario(entradaDTO.getInventarioId()));

        entradaRepo.persist(entrada);
        return entrada;
    }

    public List<Entrada> listAll() {
        return entradaRepo.listAll();
    }

    @Transactional
    public Optional<Entrada> updateEntrada(Long id, EntradaDTO entradaRequest) {
        Optional<Entrada> optionalEntrada = entradaRepo.findByIdOptional(id);
        if (optionalEntrada.isPresent()) {
            Entrada entrada = optionalEntrada.get();
            entrada.setTipoEntrada(entradaRequest.getTipoEntrada());
            entrada.setDataEntrada(entradaRequest.getDataEntrada());
            entrada.setQuantidade(entradaRequest.getQuantidade());
            entrada.setTermoRecebimento(entradaRequest.getTermoRecebimento());
            entrada.setInventario(new Inventario(entradaRequest.getInventarioId()));

            return Optional.of(entrada);
        }
        return Optional.empty();
    }

    @Transactional
    public boolean deleteEntrada(Long id) {
        return entradaRepo.deleteById(id);
    }

    public List<Entrada> findByDate(LocalDate dataEntrada) {
        return entradaRepo.find("dataEntrada", dataEntrada).list();
    } 

}
