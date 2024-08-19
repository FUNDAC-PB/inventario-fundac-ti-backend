package io.gitlab.fundacti.inventarioti.rest.service;

import java.util.List;
import java.util.Optional;

import io.gitlab.fundacti.inventarioti.domain.model.Garantia;
import io.gitlab.fundacti.inventarioti.domain.model.Inventario;
import io.gitlab.fundacti.inventarioti.domain.repository.GarantiaRepository;
import io.gitlab.fundacti.inventarioti.domain.repository.InventarioRepository;
import io.gitlab.fundacti.inventarioti.rest.dto.CreateGarantiaRequest;
import io.gitlab.fundacti.inventarioti.rest.dto.UpdateGarantiaRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class GarantiaService {

    @Inject
    GarantiaRepository garantiaRepository;

    @Inject
    InventarioRepository inventarioRepository;

    @Transactional
    public Garantia createGarantia(CreateGarantiaRequest garantiaRequest) {
        Garantia garantia = new Garantia();
        Inventario inventario = inventarioRepository.findById(garantiaRequest.getInventarioId());
        garantia.setInventario(inventario);
        garantia.setDataInicio(garantiaRequest.getDataInicio());
        garantia.setDataFim(garantiaRequest.getDataFim());
        garantia.setDetalhes(garantiaRequest.getDetalhes());

        garantiaRepository.persist(garantia);
        return garantia;
    }

    public List<Garantia> listAll() {
        return garantiaRepository.listAll();
    }

    @Transactional
    public Optional<Garantia> updateGarantia(Long id, UpdateGarantiaRequest garantiaRequest) {
        Optional<Garantia> optionalGarantia = garantiaRepository.findByIdOptional(id);
        if (optionalGarantia.isPresent()) {
            Garantia garantia = optionalGarantia.get();
            Inventario inventario = inventarioRepository.findById(garantiaRequest.getInventarioId());
            garantia.setInventario(inventario);
            garantia.setDataInicio(garantiaRequest.getDataInicio());
            garantia.setDataFim(garantiaRequest.getDataFim());
            garantia.setDetalhes(garantiaRequest.getDetalhes());

            return Optional.of(garantia);
        }
        return Optional.empty();
    }

    @Transactional
    public boolean deleteGarantia(Long id) {
        return garantiaRepository.deleteById(id);
    }

    public List<Garantia> findByDetail(String detalhes) {
        return garantiaRepository.find("detalhes", detalhes).list();
    } 

}
