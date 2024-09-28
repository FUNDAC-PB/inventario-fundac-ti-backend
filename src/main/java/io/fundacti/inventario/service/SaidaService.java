package io.fundacti.inventario.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.jboss.logging.Logger;

import io.fundacti.inventario.repo.SaidaRepository;
import io.fundacti.inventario.domain.model.Inventario;
import io.fundacti.inventario.domain.model.Saida;
import io.fundacti.inventario.dto.SaidaDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class SaidaService {

    private static final Logger LOG = Logger.getLogger(InventarioService.class);

    @Inject
    EntityManager entityManager;

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
        
        if (saidaDTO.getInventarioId() == null) {
            throw new IllegalArgumentException("ID de inventario é obrigatória");
        }
        
        // Buscar entidades existentes
        Inventario inventario = entityManager.find(Inventario.class, saidaDTO.getInventarioId());


        if (inventario == null) {
            throw new IllegalArgumentException("Entidade relacionada ao id não encontrada");
        }
        
        saida.setInventario(inventario);

        saidaRepo.persist(saida);
        return saida;
    }

    public List<Saida> listAll() {
        List<Saida> saidas = saidaRepo.listAll();
        LOG.infof("Listando todas as saidas, total: %d", saidas.size());
        return saidas;
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
            
            if (saidaRequest.getInventarioId() == null) {
                throw new IllegalArgumentException("ID de inventario é obrigatória");
            }
            
            // Buscar entidades existentes
            Inventario inventario = entityManager.find(Inventario.class, saidaRequest.getInventarioId());
    
    
            if (inventario == null) {
                throw new IllegalArgumentException("Entidade relacionada ao id não encontrada");
            }
            
            saida.setInventario(inventario);

            LOG.infof("Saida atualizada com ID: %d e Data: %s", id, saida.getDataSaida());
            return Optional.of(saida);
        }
        return Optional.empty();
    }

    @Transactional
    public boolean deleteSaida(Long id) {
        boolean deleted = saidaRepo.deleteById(id);
        if (deleted) {
            LOG.infof("Saida deletada com ID: %d", id);
        } else {
            LOG.warnf("Saida com ID: %d não encontrada para exclusão", id);
        }
        return deleted;
    }

    public List<Saida> findByDate(LocalDate dataSaida) {
        List<Saida> saidas = saidaRepo.find("dataSaida", dataSaida).list();
        LOG.infof("Saidas encontradas com data: %s, total: %d", dataSaida, saidas.size());
        return saidas;
    } 

}
