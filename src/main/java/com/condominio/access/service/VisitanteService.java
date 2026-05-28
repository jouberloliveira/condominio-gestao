package com.condominio.access.service;

import com.condominio.access.model.Visitante;
import com.condominio.access.repository.VisitanteRepository;
import com.condominio.common.enums.SimNao;
import com.condominio.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VisitanteService {

    @Autowired
    private VisitanteRepository repository;

    public List<Visitante> findAll() { return repository.findAll(); }

    public Visitante findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new BusinessException("Visitante não encontrado"));
    }

    public List<Visitante> findByUnidade(Long unidadeId) {
        return repository.findByUnidadeId(unidadeId);
    }

    public List<Visitante> findPresentes() {
        return repository.findByDataHoraSaidaIsNullAndDataHoraEntradaIsNotNull();
    }

    @Transactional
    public Visitante save(Visitante v) { return repository.save(v); }

    @Transactional
    public Visitante registrarEntrada(Long id) {
        Visitante v = findById(id);
        if (v.getDataHoraEntrada() != null && v.getDataHoraSaida() == null) {
            throw new BusinessException("Visitante já está no condomínio");
        }
        v.setDataHoraEntrada(LocalDateTime.now());
        v.setDataHoraSaida(null);
        v.setAtivo(SimNao.SIM);
        return repository.save(v);
    }

    @Transactional
    public Visitante registrarSaida(Long id) {
        Visitante v = findById(id);
        if (v.getDataHoraEntrada() == null) {
            throw new BusinessException("Visitante não registrou entrada");
        }
        if (v.getDataHoraSaida() != null) {
            throw new BusinessException("Saída já foi registrada");
        }
        v.setDataHoraSaida(LocalDateTime.now());
        v.setAtivo(SimNao.NAO);
        return repository.save(v);
    }

    @Transactional
    public void deleteById(Long id) { repository.deleteById(id); }
}
