package io.fundacti.inventario.service;

import java.util.List;
import java.util.Optional;

import org.jboss.logging.Logger;

import io.fundacti.inventario.repo.SetorRepository;
import io.fundacti.inventario.domain.model.Setor;
import io.fundacti.inventario.dto.SetorDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class SetorService {

    private static final Logger LOG = Logger.getLogger(InventarioService.class);

    public final SetorRepository setorRepo;

    public SetorService(SetorRepository setorRepo) {
        this.setorRepo = setorRepo;
    }

    @Transactional
    public Setor addSetor(SetorDTO setorRequest) {
        Setor setor = new Setor();
        setor.setNome(setorRequest.getNome());

        LOG.infof("Setor adicionada com ID: %d e Nome: %s", setor.getId(), setor.getNome());
        setorRepo.persist(setor);
        return setor;
    }

    public List<Setor> listAll() {
        List<Setor> setores = setorRepo.listAll();
        LOG.infof("Listando todas as setores, total: %d", setores.size());
        return setores;
    }

    @Transactional
    public Optional<Setor> updateItem(Long id, SetorDTO setorRequest) {
        Optional<Setor> optionalSetor = setorRepo.findByIdOptional(id);
        if (optionalSetor.isPresent()) {
            Setor setor = optionalSetor.get();
            setor.setNome(setorRequest.getNome());

            LOG.infof("Setor atualizada com ID: %d e Novo Nome: %s", id, setor.getNome());
            return Optional.of(setor);
        }
        return Optional.empty();
    }

    @Transactional
    public boolean deleteItem(Long id) {
        boolean deleted = setorRepo.deleteById(id);
        if (deleted) {
            LOG.infof("Setor deletada com ID: %d", id);
        } else {
            LOG.warnf("Setor com ID: %d não encontrada para exclusão", id);
        }
        return deleted;
    }

    public List<Setor> findByNome(String nome) {
        List<Setor> setores = setorRepo.find("nome", nome).list();
        LOG.infof("Setores encontradas com Nome: %s, total: %d", nome, setores.size());
        return setores;
    }


}