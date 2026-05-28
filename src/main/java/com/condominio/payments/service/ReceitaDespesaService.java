package com.condominio.payments.service;

import com.condominio.common.exception.BusinessException;
import com.condominio.payments.enums.TipoLancamento;
import com.condominio.payments.model.ReceitaDespesa;
import com.condominio.payments.repository.ReceitaDespesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReceitaDespesaService {

    @Autowired
    private ReceitaDespesaRepository repository;

    public List<ReceitaDespesa> findAll() { return repository.findAll(); }

    public ReceitaDespesa findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new BusinessException("Lançamento não encontrado"));
    }

    public List<ReceitaDespesa> findByPeriodo(LocalDate inicio, LocalDate fim) {
        return repository.findByDataBetween(inicio, fim);
    }

    public BigDecimal totalReceitas(LocalDate inicio, LocalDate fim) {
        return repository.sumByTipoAndPeriodo(TipoLancamento.RECEITA, inicio, fim);
    }

    public BigDecimal totalDespesas(LocalDate inicio, LocalDate fim) {
        return repository.sumByTipoAndPeriodo(TipoLancamento.DESPESA, inicio, fim);
    }

    public BigDecimal saldo(LocalDate inicio, LocalDate fim) {
        return totalReceitas(inicio, fim).subtract(totalDespesas(inicio, fim));
    }

    @Transactional
    public ReceitaDespesa save(ReceitaDespesa lancamento) {
        return repository.save(lancamento);
    }

    @Transactional
    public void deleteById(Long id) { repository.deleteById(id); }
}
