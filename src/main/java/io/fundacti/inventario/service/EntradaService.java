package io.fundacti.inventario.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.jboss.logging.Logger;

import io.fundacti.inventario.repo.EntradaRepository;
import io.fundacti.inventario.domain.model.Entrada;
import io.fundacti.inventario.domain.model.Inventario;
import io.fundacti.inventario.dto.EntradaDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class EntradaService {

    private static final Logger LOG = Logger.getLogger(InventarioService.class);

    @Inject
    EntityManager entityManager;

    public final EntradaRepository entradaRepo;

    public EntradaService(EntradaRepository entradaRepo) {
        this.entradaRepo = entradaRepo;
    }

    @Transactional
    public Entrada addEntrada(EntradaDTO entradaDTO) {

        // Log error
        LOG.infof("Adicionando entrada com inventarioId: %d", 
        entradaDTO.getInventarioId());

        if (entradaDTO.getInventarioId() == null) {
            throw new IllegalArgumentException("ID de inventario é obrigatória");
        }
        
        // Buscar entidades existentes
        Inventario inventario = entityManager.find(Inventario.class, entradaDTO.getInventarioId());

        if (inventario == null) {
            throw new IllegalArgumentException("Entidade relacionada ao id não encontrada");
        }

        Entrada entrada = new Entrada(null);
        entrada.setTipoEntrada(entradaDTO.getTipoEntrada());
        entrada.setDataEntrada(entradaDTO.getDataEntrada());
        entrada.setQuantidade(entradaDTO.getQuantidade());
        entrada.setTermoRecebimento(entradaDTO.getTermoRecebimento());
        entrada.setInventario(inventario);

        entradaRepo.persist(entrada);
        return entrada;
    }

    public List<Entrada> listAll() {
        List<Entrada> entradas = entradaRepo.listAll();
        LOG.infof("Listando todas as entradas, total: %d", entradas.size());
        return entradas;
    }

    @Transactional
    public Optional<Entrada> updateEntrada(Long id, EntradaDTO entradaRequest) {
        Optional<Entrada> optionalEntrada = entradaRepo.findByIdOptional(id);
        if (optionalEntrada.isPresent()) {

            // Log error
            LOG.infof("Atualizando entrada com inventarioId: %d", 
            entradaRequest.getInventarioId());

            if (entradaRequest.getInventarioId() == null) {
                throw new IllegalArgumentException("ID de inventario é obrigatória");
            }
            
            // Buscar entidades existentes
            Inventario inventario = entityManager.find(Inventario.class, entradaRequest.getInventarioId());
    
    
            if (inventario == null) {
                throw new IllegalArgumentException("Entidade relacionada ao id não encontrada");
            }

            Entrada entrada = optionalEntrada.get();
            entrada.setTipoEntrada(entradaRequest.getTipoEntrada());
            entrada.setDataEntrada(entradaRequest.getDataEntrada());
            entrada.setQuantidade(entradaRequest.getQuantidade());
            entrada.setTermoRecebimento(entradaRequest.getTermoRecebimento());
            entrada.setInventario(inventario);

            LOG.infof("Entrada atualizada com ID: %d e Tipo: %s", id, entrada.getTipoEntrada());
            return Optional.of(entrada);
        }
        return Optional.empty();
    }

    @Transactional
    public boolean deleteEntrada(Long id) {
        boolean deleted = entradaRepo.deleteById(id);
        if (deleted) {
            LOG.infof("Entrada deletada com ID: %d", id);
        } else {
            LOG.warnf("Entrada com ID: %d não encontrada para exclusão", id);
        }
        return deleted;
    }

    public List<Entrada> findByDate(LocalDate dataEntrada) {
        List<Entrada> entradas = entradaRepo.find("dataEntrada", dataEntrada).list();
        LOG.infof("Entradas encontradas com data: %s, total: %d", dataEntrada, entradas.size());
        return entradas;
    } 

}
