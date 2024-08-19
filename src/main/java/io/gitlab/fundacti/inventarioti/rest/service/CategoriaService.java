package io.gitlab.fundacti.inventarioti.rest.service;

import java.util.List;
import java.util.Optional;

import io.gitlab.fundacti.inventarioti.domain.model.Categoria;
import io.gitlab.fundacti.inventarioti.domain.repository.CategoriaRepository;
import io.gitlab.fundacti.inventarioti.rest.dto.CreateCategoriaRequest;
import io.gitlab.fundacti.inventarioti.rest.dto.UpdateCategoriaRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CategoriaService {

    @Inject
    CategoriaRepository categoriaRepository;

    @Transactional
    public Categoria createCategoria(CreateCategoriaRequest categoriaRequest) {
        Categoria categoria = new Categoria();
        categoria.setNome(categoriaRequest.getNome());

        categoriaRepository.persist(categoria);
        return categoria;
    }

    public List<Categoria> listAll() {
        return categoriaRepository.listAll();
    }

    @Transactional
    public Optional<Categoria> updateItem(Long id, UpdateCategoriaRequest categoriaRequest) {
        Optional<Categoria> optionalCategoria = categoriaRepository.findByIdOptional(id);
        if (optionalCategoria.isPresent()) {
            Categoria categoria = optionalCategoria.get();
            categoria.setNome(categoriaRequest.getNome());

            return Optional.of(categoria);
        }
        return Optional.empty();
    }

    @Transactional
    public boolean deleteItem(Long id) {
        return categoriaRepository.deleteById(id);
    }

    public List<Categoria> findByNome(String nome) {
        return categoriaRepository.find("nome", nome).list();
    }
}