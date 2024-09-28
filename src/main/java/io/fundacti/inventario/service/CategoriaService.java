package io.fundacti.inventario.service;

import java.util.List;
import java.util.Optional;

import org.jboss.logging.Logger;

import io.fundacti.inventario.repo.CategoriaRepository;
import io.fundacti.inventario.domain.model.Categoria;
import io.fundacti.inventario.dto.CategoriaDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class CategoriaService {

    private static final Logger LOG = Logger.getLogger(InventarioService.class);

    public final CategoriaRepository categoriaRepo;

    public CategoriaService(CategoriaRepository categoriaRepo) {
        this.categoriaRepo = categoriaRepo;
    }

    @Transactional
    public Categoria addCategoria(CategoriaDTO categoriaDTO) {

        Categoria categoria = new Categoria();
        categoria.setNome(categoriaDTO.getNome());

        categoriaRepo.persist(categoria);

        LOG.infof("Categoria adicionada com ID: %d e Nome: %s", categoria.getId(), categoria.getNome());

        return categoria;
    }

    public List<Categoria> listAll() {
        List<Categoria> categorias = categoriaRepo.listAll();
        LOG.infof("Listando todas as categorias, total: %d", categorias.size());
        return categorias;
    }

    @Transactional
    public Optional<Categoria> updateCategoria(Long id, CategoriaDTO categoriaRequest) {
        Optional<Categoria> optionalCategoria = categoriaRepo.findByIdOptional(id);
        if (optionalCategoria.isPresent()) {
            Categoria categoria = optionalCategoria.get();
            categoria.setNome(categoriaRequest.getNome());

            LOG.infof("Categoria atualizada com ID: %d e Novo Nome: %s", id, categoria.getNome());
            return Optional.of(categoria);
        }

        return Optional.empty();
    }

    @Transactional
    public boolean deleteCategoria(Long id) {
        boolean deleted = categoriaRepo.deleteById(id);
        if (deleted) {
            LOG.infof("Categoria deletada com ID: %d", id);
        } else {
            LOG.warnf("Categoria com ID: %d não encontrada para exclusão", id);
        }
        return deleted;
    }

    public List<Categoria> findByNome(String nome) {
        List<Categoria> categorias = categoriaRepo.find("nome", nome).list();
        LOG.infof("Categorias encontradas com Nome: %s, total: %d", nome, categorias.size());
        return categorias;
    }
}