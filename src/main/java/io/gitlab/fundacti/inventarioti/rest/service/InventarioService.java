package io.gitlab.fundacti.inventarioti.rest.service;

import java.util.List;
import java.util.Optional;

import io.gitlab.fundacti.inventarioti.domain.model.Categoria;
import io.gitlab.fundacti.inventarioti.domain.model.Inventario;
import io.gitlab.fundacti.inventarioti.domain.model.Lotacao;
import io.gitlab.fundacti.inventarioti.domain.model.Setor;
import io.gitlab.fundacti.inventarioti.domain.repository.InventarioRepository;
import io.gitlab.fundacti.inventarioti.rest.dto.CreateItemRequest;
import io.gitlab.fundacti.inventarioti.rest.dto.UpdateItemRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class InventarioService {

    @Inject
    InventarioRepository inventarioRepository;

    @Transactional
    public Inventario createItem(CreateItemRequest itemRequest) {
        Inventario inventario = new Inventario();
        inventario.setNome(itemRequest.getNome());
        inventario.setDescricao(itemRequest.getDescricao());
        inventario.setTipo(itemRequest.getTipo());
        inventario.setPatrimonio(itemRequest.getPatrimonio());
        inventario.setNumeroSerie(itemRequest.getNumeroSerie());
        inventario.setResponsavel(itemRequest.getResponsavel());
        inventario.setStatus(itemRequest.getStatus());
            //Lotacao lotacao = new Lotacao(itemRequest.getLotacaoId());
            inventario.setLotacao(new Lotacao(itemRequest.getLotacaoId())); 
            //Setor setor = new Setor(itemRequest.getSetorId());
            inventario.setSetor(new Setor(itemRequest.getSetorId())); 
            //Categoria categoria = new Categoria(itemRequest.getCategoriaId());
            inventario.setCategoria(new Categoria(itemRequest.getCategoriaId())); 

        inventarioRepository.persist(inventario);
        return inventario;
    }

    public List<Inventario> listAll() {
        return inventarioRepository.listAll();
    }

    @Transactional
    public Optional<Inventario> updateItem(Long id, UpdateItemRequest itemRequest) {
        Optional<Inventario> optionalInventario = inventarioRepository.findByIdOptional(id);
        if (optionalInventario.isPresent()) {
            Inventario inventario = optionalInventario.get();
            inventario.setNome(itemRequest.getNome());
            inventario.setDescricao(itemRequest.getDescricao());
            inventario.setTipo(itemRequest.getTipo());
            inventario.setPatrimonio(itemRequest.getPatrimonio());
            inventario.setNumeroSerie(itemRequest.getNumeroSerie());
            inventario.setResponsavel(itemRequest.getResponsavel());
            inventario.setStatus(itemRequest.getStatus());
                //Lotacao lotacao = new Lotacao(itemRequest.getLotacaoId());
                inventario.setLotacao(new Lotacao(itemRequest.getLotacaoId())); 
                //Setor setor = new Setor(itemRequest.getSetorId());
                inventario.setSetor(new Setor(itemRequest.getSetorId())); 
                //Categoria categoria = new Categoria(itemRequest.getCategoriaId());
                inventario.setCategoria(new Categoria(itemRequest.getCategoriaId())); 
            
            return Optional.of(inventario);
        }
        return Optional.empty();
    }

    @Transactional
    public boolean deleteItem(Long id) {
        return inventarioRepository.deleteById(id);
    }

    public List<Inventario> findByNome(String nome) {
        return inventarioRepository.find("nome", nome).list();
    }

    public List<Inventario> findByTipo(String tipo) {
        return inventarioRepository.find("tipo", tipo).list();
    }

    public List<Inventario> findByPatrimonio(String patrimonio) {
        return inventarioRepository.find("patrimonio", patrimonio).list();
    }

    public List<Inventario> findBySerie(String numeroSerie) {
        return inventarioRepository.find("numeroSerie", numeroSerie).list();
    }
}