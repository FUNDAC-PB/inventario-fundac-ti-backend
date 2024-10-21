package io.fundacti.inventario.service;

import java.util.List;
import java.util.Optional;

import io.fundacti.inventario.domain.model.Categoria;
import io.fundacti.inventario.domain.model.Inventario;
import io.fundacti.inventario.domain.model.Lotacao;
import io.fundacti.inventario.domain.model.Setor;
import io.fundacti.inventario.dto.InventarioDTO;
import io.fundacti.inventario.repo.InventarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import org.jboss.logging.Logger;

@ApplicationScoped
@Transactional
public class InventarioService {

    private static final Logger LOG = Logger.getLogger(InventarioService.class);

    @Inject
    EntityManager entityManager;

    private final InventarioRepository inventarioRepo;

    public InventarioService(InventarioRepository inventarioRepo) {
        this.inventarioRepo = inventarioRepo;
    }

    @Transactional
    public Inventario addItem(InventarioDTO inventarioDTO) {
        // Log error
        LOG.infof("Adicionando item com LotacaoId: %d, SetorId: %d, CategoriaId: %d", 
                  inventarioDTO.getLotacaoId(), inventarioDTO.getSetorId(), inventarioDTO.getCategoriaId());

        if (inventarioDTO.getLotacaoId() == null || inventarioDTO.getSetorId() == null || inventarioDTO.getCategoriaId() == null) {
            throw new IllegalArgumentException("IDs de Lotacao, Setor e Categoria são obrigatórios (" + inventarioDTO.getLotacaoId() + inventarioDTO.getSetorId() + inventarioDTO.getCategoriaId() + "}");
        }
        
        Lotacao lotacao = entityManager.find(Lotacao.class, inventarioDTO.getLotacaoId());
        Setor setor = entityManager.find(Setor.class, inventarioDTO.getSetorId());
        Categoria categoria = entityManager.find(Categoria.class, inventarioDTO.getCategoriaId());

        // Test error
        if (lotacao == null || setor == null || categoria == null) {
            throw new IllegalArgumentException("Entidade relacionada ao id não encontrada");
        }

        Inventario inventario = new Inventario();
        inventario.setNome(inventarioDTO.getNome());
        inventario.setDescricao(inventarioDTO.getDescricao());
        inventario.setTipo(inventarioDTO.getTipo());
        inventario.setPatrimonio(inventarioDTO.getPatrimonio());
        inventario.setNumeroSerie(inventarioDTO.getNumeroSerie());
        inventario.setResponsavel(inventarioDTO.getResponsavel());
        inventario.setStatus(inventarioDTO.getStatus());
        inventario.setLotacao(lotacao);
        inventario.setSetor(setor);
        inventario.setCategoria(categoria);

        inventarioRepo.persist(inventario);
        return inventario;
    }

    public List<Inventario> listAll() {
        List<Inventario> items = inventarioRepo.listAll();
        LOG.infof("Listando todas as itens, total: %d", items.size());
        return items;
    }

    @Transactional
    public Optional<Inventario> updateItem(Long id, InventarioDTO itemRequest) {
        Optional<Inventario> optionalInventario = inventarioRepo.findByIdOptional(id);
        if (optionalInventario.isPresent()) {

            // Log error
            LOG.infof("Atualizando item com LotacaoId: %d, SetorId: %d, CategoriaId: %d", 
            itemRequest.getLotacaoId(), itemRequest.getSetorId(), itemRequest.getCategoriaId());

            if (itemRequest.getLotacaoId() == null || itemRequest.getSetorId() == null || itemRequest.getCategoriaId() == null) {
                throw new IllegalArgumentException("IDs de Lotacao, Setor e Categoria são obrigatórios");
            }

            // Buscar entidades existentes
            Lotacao lotacao = entityManager.find(Lotacao.class, itemRequest.getLotacaoId());
            Setor setor = entityManager.find(Setor.class, itemRequest.getSetorId());
            Categoria categoria = entityManager.find(Categoria.class, itemRequest.getCategoriaId());
    
            if (lotacao == null || setor == null || categoria == null) {
                throw new IllegalArgumentException("Entidades relacionada ao id não encontrada");
            }
            
            Inventario inventario = optionalInventario.get();
            inventario.setNome(itemRequest.getNome());
            inventario.setDescricao(itemRequest.getDescricao());
            inventario.setTipo(itemRequest.getTipo());
            inventario.setPatrimonio(itemRequest.getPatrimonio());
            inventario.setNumeroSerie(itemRequest.getNumeroSerie());
            inventario.setResponsavel(itemRequest.getResponsavel());
            inventario.setStatus(itemRequest.getStatus());

            inventario.setLotacao(lotacao);
            inventario.setSetor(setor);
            inventario.setCategoria(categoria);

            LOG.infof("Item atualizada com ID: %d e Nome: %s", id, inventario.getNome());
            return Optional.of(inventario);
        }
        return Optional.empty();
    }

    @Transactional
    public boolean deleteItem(Long id) {
        boolean deleted = inventarioRepo.deleteById(id);
        if (deleted) {
            LOG.infof("Item deletada com ID: %d", id);
        } else {
            LOG.warnf("Item com ID: %d não encontrada para exclusão", id);
        }
        return deleted;
    }

    public List<Inventario> findByNome(String nome) {
        List<Inventario> items = inventarioRepo.find("Nome", nome).list();
        LOG.infof("Items encontradas com Nome: %s, total: %d", nome, items.size());
        return items;
    }

    public List<Inventario> findByTipo(String tipo) {
        return inventarioRepo.find("tipo", tipo).list();
    }

    public List<Inventario> findByPatrimonio(String patrimonio) {
        return inventarioRepo.find("patrimonio", patrimonio).list();
    }

    public List<Inventario> findBySerie(String numeroSerie) {
        return inventarioRepo.find("numeroSerie", numeroSerie).list();
    }
}