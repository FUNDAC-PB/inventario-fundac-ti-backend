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
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class InventarioService {

    private final InventarioRepository inventarioRepo;

    public InventarioService(InventarioRepository inventarioRepo) {
        this.inventarioRepo = inventarioRepo;
    }

    public Inventario addItem(InventarioDTO inventarioDTO) {
        Inventario inventario = new Inventario();
        inventario.setNome(inventarioDTO.getNome());
        inventario.setDescricao(inventarioDTO.getDescricao());
        inventario.setTipo(inventarioDTO.getTipo());
        inventario.setPatrimonio(inventarioDTO.getPatrimonio());
        inventario.setNumeroSerie(inventarioDTO.getNumeroSerie());
        inventario.setResponsavel(inventarioDTO.getResponsavel());
        inventario.setStatus(inventarioDTO.getStatus());
        inventario.setLotacao(new Lotacao(inventarioDTO.getLotacaoId()));
        inventario.setSetor(new Setor(inventarioDTO.getSetorId()));
        inventario.setCategoria(new Categoria(inventarioDTO.getCategoriaId()));

        inventarioRepo.persist(inventario);
        return inventario;
    }

    public List<Inventario> listAll() {
        return inventarioRepo.listAll();
    }

    @Transactional
    public Optional<Inventario> updateItem(Long id, InventarioDTO itemRequest) {
        Optional<Inventario> optionalInventario = inventarioRepo.findByIdOptional(id);
        if (optionalInventario.isPresent()) {
            Inventario inventario = optionalInventario.get();
            inventario.setNome(itemRequest.getNome());
            inventario.setDescricao(itemRequest.getDescricao());
            inventario.setTipo(itemRequest.getTipo());
            inventario.setPatrimonio(itemRequest.getPatrimonio());
            inventario.setNumeroSerie(itemRequest.getNumeroSerie());
            inventario.setResponsavel(itemRequest.getResponsavel());
            inventario.setStatus(itemRequest.getStatus());
            inventario.setLotacao(new Lotacao(itemRequest.getLotacaoId()));
            inventario.setSetor(new Setor(itemRequest.getSetorId()));
            inventario.setCategoria(new Categoria(itemRequest.getCategoriaId()));
            
            return Optional.of(inventario);
        }
        return Optional.empty();
    }

    @Transactional
    public boolean deleteItem(Long id) {
        return inventarioRepo.deleteById(id);
    }

    public List<Inventario> findByNome(String nome) {
        return inventarioRepo.find("nome", nome).list();
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