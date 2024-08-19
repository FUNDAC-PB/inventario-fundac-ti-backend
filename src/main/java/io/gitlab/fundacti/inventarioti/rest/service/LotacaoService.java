package io.gitlab.fundacti.inventarioti.rest.service;

import java.util.List;
import java.util.Optional;

import io.gitlab.fundacti.inventarioti.domain.model.Lotacao;
import io.gitlab.fundacti.inventarioti.domain.repository.LotacaoRepository;
import io.gitlab.fundacti.inventarioti.rest.dto.CreateLotacaoRequest;
import io.gitlab.fundacti.inventarioti.rest.dto.UpdateLotacaoRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class LotacaoService {

    @Inject
    LotacaoRepository lotacaoRepository;

    @Transactional
    public Lotacao createLotacao(CreateLotacaoRequest lotacaoRequest) {
        Lotacao lotacao = new Lotacao();
        lotacao.setNome(lotacaoRequest.getNome());

        lotacaoRepository.persist(lotacao);
        return lotacao;
    }

    public List<Lotacao> listAll() {
        return lotacaoRepository.listAll();
    }

    @Transactional
    public Optional<Lotacao> updateItem(Long id, UpdateLotacaoRequest lotacaoRequest) {
        Optional<Lotacao> optionalLotacao = lotacaoRepository.findByIdOptional(id);
        if (optionalLotacao.isPresent()) {
            Lotacao lotacao = optionalLotacao.get();
            lotacao.setNome(lotacaoRequest.getNome());

            return Optional.of(lotacao);
        }
        return Optional.empty();
    }

    @Transactional
    public boolean deleteItem(Long id) {
        return lotacaoRepository.deleteById(id);
    }

    public List<Lotacao> findByNome(String nome) {
        return lotacaoRepository.find("nome", nome).list();
    }


}