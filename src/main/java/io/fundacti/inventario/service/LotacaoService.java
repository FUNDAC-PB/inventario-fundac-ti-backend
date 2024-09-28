package io.fundacti.inventario.service;

import java.util.List;
import java.util.Optional;

import org.jboss.logging.Logger;

import io.fundacti.inventario.repo.LotacaoRepository;
import io.fundacti.inventario.domain.model.Lotacao;
import io.fundacti.inventario.dto.LotacaoDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class LotacaoService {

    private static final Logger LOG = Logger.getLogger(InventarioService.class);

    public final LotacaoRepository lotacaoRepo;

    public LotacaoService(LotacaoRepository lotacaoRepo) {
        this.lotacaoRepo = lotacaoRepo;
    }

    @Transactional
    public Lotacao addLotacao(LotacaoDTO lotacaoDTO) {
        Lotacao lotacao = new Lotacao();
        lotacao.setNome(lotacaoDTO.getNome());

        LOG.infof("Lotacao adicionada com ID: %d e Nome: %s", lotacao.getId(), lotacao.getNome());

        lotacaoRepo.persist(lotacao);
        return lotacao;
    }

    public List<Lotacao> listAll() {
        List<Lotacao> lotacoes = lotacaoRepo.listAll();
        LOG.infof("Listando todas as lotacaos, total: %d", lotacoes.size());
        return lotacoes;
    }

    @Transactional
    public Optional<Lotacao> updateLotacao(Long id, LotacaoDTO lotacaoRequest) {
        Optional<Lotacao> optionalLotacao = lotacaoRepo.findByIdOptional(id);
        if (optionalLotacao.isPresent()) {
            Lotacao lotacao = optionalLotacao.get();
            lotacao.setNome(lotacaoRequest.getNome());

            LOG.infof("Lotacao atualizada com ID: %d e Novo Nome: %s", id, lotacao.getNome());
            return Optional.of(lotacao);
        }
        return Optional.empty();
    }

    @Transactional
    public boolean deleteLotacao(Long id) {
        boolean deleted = lotacaoRepo.deleteById(id);
        if (deleted) {
            LOG.infof("Lotacao deletada com ID: %d", id);
        } else {
            LOG.warnf("Lotacao com ID: %d não encontrada para exclusão", id);
        }
        return deleted;
    }

    public List<Lotacao> findByNome(String nome) {
        return lotacaoRepo.find("nome", nome).list();
    }


}