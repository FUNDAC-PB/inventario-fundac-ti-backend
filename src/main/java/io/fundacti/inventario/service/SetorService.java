package io.fundacti.inventario.service;

import java.util.List;
import java.util.Optional;

import io.fundacti.inventario.repo.SetorRepository;
import io.fundacti.inventario.domain.model.Setor;
import io.fundacti.inventario.dto.SetorDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class SetorService {

    public final SetorRepository setorRepo;

    public SetorService(SetorRepository setorRepo) {
        this.setorRepo = setorRepo;
    }

    @Transactional
    public Setor addSetor(SetorDTO setorRequest) {
        Setor setor = new Setor();
        setor.setNome(setorRequest.getNome());

        setorRepo.persist(setor);
        return setor;
    }

    public List<Setor> listAll() {
        return setorRepo.listAll();
    }

    @Transactional
    public Optional<Setor> updateItem(Long id, SetorDTO setorRequest) {
        Optional<Setor> optionalSetor = setorRepo.findByIdOptional(id);
        if (optionalSetor.isPresent()) {
            Setor setor = optionalSetor.get();
            setor.setNome(setorRequest.getNome());

            return Optional.of(setor);
        }
        return Optional.empty();
    }

    @Transactional
    public boolean deleteItem(Long id) {
        return setorRepo.deleteById(id);
    }

    public List<Setor> findByNome(String nome) {
        return setorRepo.find("nome", nome).list();
    }


}