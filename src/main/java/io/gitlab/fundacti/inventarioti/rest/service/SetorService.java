package io.gitlab.fundacti.inventarioti.rest.service;

import java.util.List;
import java.util.Optional;

import io.gitlab.fundacti.inventarioti.domain.model.Setor;
import io.gitlab.fundacti.inventarioti.domain.repository.SetorRepository;
import io.gitlab.fundacti.inventarioti.rest.dto.CreateSetorRequest;
import io.gitlab.fundacti.inventarioti.rest.dto.UpdateSetorRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class SetorService {

    @Inject
    SetorRepository setorRepository;

    @Transactional
    public Setor createSetor(CreateSetorRequest setorRequest) {
        Setor setor = new Setor();
        setor.setNome(setorRequest.getNome());

        setorRepository.persist(setor);
        return setor;
    }

    public List<Setor> listAll() {
        return setorRepository.listAll();
    }

    @Transactional
    public Optional<Setor> updateItem(Long id, UpdateSetorRequest setorRequest) {
        Optional<Setor> optionalSetor = setorRepository.findByIdOptional(id);
        if (optionalSetor.isPresent()) {
            Setor setor = optionalSetor.get();
            setor.setNome(setorRequest.getNome());

            return Optional.of(setor);
        }
        return Optional.empty();
    }

    @Transactional
    public boolean deleteItem(Long id) {
        return setorRepository.deleteById(id);
    }

    public List<Setor> findByNome(String nome) {
        return setorRepository.find("nome", nome).list();
    }


}