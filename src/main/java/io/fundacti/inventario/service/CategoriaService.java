package io.fundacti.inventario.service;

import java.util.List;
import java.util.Optional;

import io.fundacti.inventario.repo.CategoriaRepository;
import io.fundacti.inventario.domain.model.Categoria;
import io.fundacti.inventario.dto.CategoriaDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class CategoriaService {

    public final CategoriaRepository categoriaRepo;

    public CategoriaService(CategoriaRepository categoriaRepo) {
        this.categoriaRepo = categoriaRepo;
    }

    public Categoria addCategoria(CategoriaDTO categoriaDTO) {
        Categoria categoria = new Categoria();
        categoria.setNome(categoriaDTO.getNome());

        categoriaRepo.persist(categoria);
        return categoria;
    }

    public List<Categoria> listAll() {
        return categoriaRepo.listAll();
    }

    @Transactional
    public Optional<Categoria> updateCategoria(Long id, CategoriaDTO categoriaRequest) {
        Optional<Categoria> optionalCategoria = categoriaRepo.findByIdOptional(id);
        if (optionalCategoria.isPresent()) {
            Categoria categoria = optionalCategoria.get();
            categoria.setNome(categoriaRequest.getNome());

            return Optional.of(categoria);
        }

        return Optional.empty();
    }

    @Transactional
    public boolean deleteCategoria(Long id) {
        return categoriaRepo.deleteById(id);
    }

    public List<Categoria> findByNome(String nome) {
        return categoriaRepo.find("nome", nome).list();
    }
}