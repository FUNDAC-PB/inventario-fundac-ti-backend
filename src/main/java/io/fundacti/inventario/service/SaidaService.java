package io.fundacti.inventario.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import io.fundacti.inventario.repo.SaidaRepository;
import io.fundacti.inventario.domain.model.Inventario;
import io.fundacti.inventario.domain.model.Saida;
import io.fundacti.inventario.dto.SaidaDTO;
import jakarta.enterprise.context.ApplicationScoped;

import jakarta.transaction.Transactional;

@ApplicationScoped
public class SaidaService {


    public final SaidaRepository saidaRepo;

    public SaidaService(SaidaRepository saidaRepo) {
        this.saidaRepo = saidaRepo;
    }

    @Transactional
    public Saida addSaida(SaidaDTO saidaDTO) {
        Saida saida = new Saida(null);
        saida.setTipoSaida(saidaDTO.getTipoSaida());
        saida.setDataSaida(saidaDTO.getDataSaida());
        saida.setQuantidade(saidaDTO.getQuantidade());
        saida.setTermoSaida(saidaDTO.getTermoSaida());
        saida.setInventario(new Inventario(saidaDTO.getInventarioId()));

        saidaRepo.persist(saida);
        return saida;
    }

    public List<Saida> listAll() {
        return saidaRepo.listAll();
    }

    @Transactional
    public Optional<Saida> updateSaida(Long id, SaidaDTO saidaRequest) {
        Optional<Saida> optionalSaida = saidaRepo.findByIdOptional(id);
        if (optionalSaida.isPresent()) {
            Saida saida = optionalSaida.get();
            saida.setTipoSaida(saidaRequest.getTipoSaida());
            saida.setDataSaida(saidaRequest.getDataSaida());
            saida.setQuantidade(saidaRequest.getQuantidade());
            saida.setTermoSaida(saidaRequest.getTermoSaida());
            saida.setInventario(new Inventario(saidaRequest.getInventarioId()));

            return Optional.of(saida);
        }
        return Optional.empty();
    }

    @Transactional
    public boolean deleteSaida(Long id) {
        return saidaRepo.deleteById(id);
    }

    public List<Saida> findByDate(LocalDate dataSaida) {
        return saidaRepo.find("dataSaida", dataSaida).list();
    } 

}
