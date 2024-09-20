package io.fundacti.inventario.service;

import java.util.List;
import java.util.Optional;

import io.fundacti.inventario.repo.LotacaoRepository;
import io.fundacti.inventario.domain.model.Lotacao;
import io.fundacti.inventario.dto.LotacaoDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class LotacaoService {

    public final LotacaoRepository lotacaoRepo;

    public LotacaoService(LotacaoRepository lotacaoRepo) {
        this.lotacaoRepo = lotacaoRepo;
    }

    public Lotacao addLotacao(LotacaoDTO lotacaoDTO) {
        Lotacao lotacao = new Lotacao();
        lotacao.setNome(lotacaoDTO.getNome());

        lotacaoRepo.persist(lotacao);
        return lotacao;
    }

    public List<Lotacao> listAll() {
        return lotacaoRepo.listAll();
    }

    @Transactional
    public Optional<Lotacao> updateLotacao(Long id, LotacaoDTO lotacaoRequest) {
        Optional<Lotacao> optionalLotacao = lotacaoRepo.findByIdOptional(id);
        if (optionalLotacao.isPresent()) {
            Lotacao lotacao = optionalLotacao.get();
            lotacao.setNome(lotacaoRequest.getNome());

            return Optional.of(lotacao);
        }
        return Optional.empty();
    }

    @Transactional
    public boolean deleteLotacao(Long id) {
        return lotacaoRepo.deleteById(id);
    }

    public List<Lotacao> findByNome(String nome) {
        return lotacaoRepo.find("nome", nome).list();
    }


}