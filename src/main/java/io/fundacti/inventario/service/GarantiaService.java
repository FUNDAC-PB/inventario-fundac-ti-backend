package io.fundacti.inventario.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import io.fundacti.inventario.repo.GarantiaRepository;
import io.fundacti.inventario.domain.model.Garantia;
import io.fundacti.inventario.domain.model.Inventario;
import io.fundacti.inventario.dto.GarantiaDTO;
import jakarta.enterprise.context.ApplicationScoped;

import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class GarantiaService {

    public final GarantiaRepository garantiaRepo;

    public GarantiaService(GarantiaRepository garantiaRepo) {
        this.garantiaRepo = garantiaRepo;
    }

    public Garantia addGarantia(GarantiaDTO garantiaDTO) {
        Garantia garantia = new Garantia();
        garantia.setDataInicio(garantiaDTO.getDataInicio());
        garantia.setDataFim(garantiaDTO.getDataFim());
        garantia.setDetalhes(garantiaDTO.getDetalhes());
        garantia.setInventario(new Inventario(garantiaDTO.getInventarioId()));

        garantiaRepo.persist(garantia);
        return garantia;
    }

    public List<Garantia> listAll() {
        return garantiaRepo.listAll();
    }

    @Transactional
    public Optional<Garantia> updateGarantia(Long id, GarantiaDTO garantiaRequest) {
        Optional<Garantia> optionalGarantia = garantiaRepo.findByIdOptional(id);
        if (optionalGarantia.isPresent()) {
            Garantia garantia = optionalGarantia.get();
            garantia.setDataInicio(garantiaRequest.getDataInicio());
            garantia.setDataFim(garantiaRequest.getDataFim());
            garantia.setDetalhes(garantiaRequest.getDetalhes());
            garantia.setInventario(new Inventario(garantiaRequest.getInventarioId()));

            return Optional.of(garantia);
        }

        return Optional.empty();
    }

    @Transactional
    public boolean deleteGarantia(Long id) {
        return garantiaRepo.deleteById(id);
    }

    public List<Garantia> findByDataFim(LocalDate dataFim) {
        return garantiaRepo.find("dataFim", dataFim).list();
    }

}
