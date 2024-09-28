package io.fundacti.inventario.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.jboss.logging.Logger;

import io.fundacti.inventario.repo.GarantiaRepository;
import io.fundacti.inventario.domain.model.Garantia;
import io.fundacti.inventario.domain.model.Inventario;
import io.fundacti.inventario.dto.GarantiaDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class GarantiaService {

    private static final Logger LOG = Logger.getLogger(InventarioService.class);

    @Inject
    EntityManager entityManager;

    public final GarantiaRepository garantiaRepo;

    public GarantiaService(GarantiaRepository garantiaRepo) {
        this.garantiaRepo = garantiaRepo;
    }

    @Transactional
    public Garantia addGarantia(GarantiaDTO garantiaDTO) {

        // Log error
        LOG.infof("Adicionando garantia com inventarioId: %d", 
                  garantiaDTO.getInventarioId());

        if (garantiaDTO.getInventarioId() == null) {
            throw new IllegalArgumentException("ID de inventario é obrigatória");
        }

        // Buscar entidades existentes
        Inventario inventario = entityManager.find(Inventario.class, garantiaDTO.getInventarioId());


        if (inventario == null) {
            throw new IllegalArgumentException("Entidade relacionada ao id não encontrada");
        }

        Garantia garantia = new Garantia();
        garantia.setDataInicio(garantiaDTO.getDataInicio());
        garantia.setDataFim(garantiaDTO.getDataFim());
        garantia.setDetalhes(garantiaDTO.getDetalhes());
        garantia.setInventario(inventario);

        garantiaRepo.persist(garantia);
        return garantia;
    }

    public List<Garantia> listAll() {
        List<Garantia> garantias = garantiaRepo.listAll();
        LOG.infof("Listando todas as garantias, total: %d", garantias.size());
        return garantias;
    }

    @Transactional
    public Optional<Garantia> updateGarantia(Long id, GarantiaDTO garantiaRequest) {
        Optional<Garantia> optionalGarantia = garantiaRepo.findByIdOptional(id);
        if (optionalGarantia.isPresent()) {

            // Log error
            LOG.infof("Atualizando garantia com inventarioId: %d", 
            garantiaRequest.getInventarioId());

            if (garantiaRequest.getInventarioId() == null) {
                throw new IllegalArgumentException("ID de inventario é obrigatória");
            }

            // Buscar entidade existente
            Inventario inventario = entityManager.find(Inventario.class, garantiaRequest.getInventarioId());

            if (inventario == null) {
                throw new IllegalArgumentException("Entidade relacionada ao id não encontrada");
            }

            Garantia garantia = optionalGarantia.get();
            garantia.setDataInicio(garantiaRequest.getDataInicio());
            garantia.setDataFim(garantiaRequest.getDataFim());
            garantia.setDetalhes(garantiaRequest.getDetalhes());
            garantia.setInventario(inventario);

            LOG.infof("Garantia atualizada com ID: %d e Detalhes: %s", id, garantia.getDetalhes());
            return Optional.of(garantia);
        }

        return Optional.empty();
    }

    @Transactional
    public boolean deleteGarantia(Long id) {
        boolean deleted = garantiaRepo.deleteById(id);
        if (deleted) {
            LOG.infof("Garantia deletada com ID: %d", id);
        } else {
            LOG.warnf("Garantia com ID: %d não encontrada para exclusão", id);
        }
        return deleted;
    }

    public List<Garantia> findByDataFim(LocalDate dataFim) {
        List<Garantia> garantias = garantiaRepo.find("dataFim", dataFim).list();
        LOG.infof("Garantias encontradas com data: %s, total: %d", dataFim, garantias.size());
        return garantias;
    }

}
