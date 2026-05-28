package com.condominio.payments.service;

import com.condominio.common.exception.BusinessException;
import com.condominio.payments.enums.StatusCobranca;
import com.condominio.payments.model.TaxaCondominial;
import com.condominio.payments.repository.TaxaCondominialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class TaxaCondominialService {

    @Autowired
    private TaxaCondominialRepository repository;

    public List<TaxaCondominial> findAll() { return repository.findAll(); }

    public TaxaCondominial findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new BusinessException("Taxa condominial não encontrada"));
    }

    public List<TaxaCondominial> findByUnidade(Long unidadeId) {
        return repository.findByUnidadeId(unidadeId);
    }

    public List<TaxaCondominial> findInadimplentes() {
        return repository.findInadimplentes(LocalDate.now());
    }

    public List<TaxaCondominial> findByCompetencia(Integer ano, Integer mes) {
        return repository.findByCompetenciaAnoAndCompetenciaMes(ano, mes);
    }

    @Transactional
    public TaxaCondominial save(TaxaCondominial taxa) {
        if (taxa.getId() == null && repository.existsByUnidadeIdAndCompetenciaAnoAndCompetenciaMes(
                taxa.getUnidade().getId(), taxa.getCompetenciaAno(), taxa.getCompetenciaMes())) {
            throw new BusinessException("Já existe taxa lançada para esta unidade nesta competência");
        }
        return repository.save(taxa);
    }

    @Transactional
    public TaxaCondominial registrarPagamento(Long id, LocalDate dataPagamento) {
        TaxaCondominial taxa = findById(id);
        if (taxa.getStatus() == StatusCobranca.PAGO) {
            throw new BusinessException("Taxa já foi paga");
        }
        taxa.setDataPagamento(dataPagamento);
        taxa.setStatus(StatusCobranca.PAGO);
        return repository.save(taxa);
    }

    @Transactional
    public void marcarVencidas() {
        List<TaxaCondominial> vencidas = repository.findInadimplentes(LocalDate.now());
        vencidas.forEach(t -> {
            if (t.getStatus() == StatusCobranca.PENDENTE) {
                t.setStatus(StatusCobranca.VENCIDO);
            }
        });
        repository.saveAll(vencidas);
    }

    @Transactional
    public void deleteById(Long id) { repository.deleteById(id); }
}
