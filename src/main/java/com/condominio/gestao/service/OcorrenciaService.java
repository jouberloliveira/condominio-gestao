package com.condominio.gestao.service;

import com.condominio.gestao.enums.StatusOcorrencia;
import com.condominio.gestao.exception.BusinessException;
import com.condominio.gestao.model.Ocorrencia;
import com.condominio.gestao.repository.OcorrenciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OcorrenciaService {

    private final OcorrenciaRepository repository;

    public List<Ocorrencia> findAll() {
        return repository.findAll();
    }

    public Ocorrencia findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BusinessException("Ocorrência não encontrada"));
    }

    @Transactional
    public Ocorrencia save(Ocorrencia ocorrencia) {
        if ((ocorrencia.getStatus() == StatusOcorrencia.RESOLVIDA ||
             ocorrencia.getStatus() == StatusOcorrencia.CANCELADA) &&
            ocorrencia.getDataHoraFechamento() == null) {
            throw new BusinessException(
                    "Data/hora de fechamento é obrigatória para ocorrências resolvidas ou canceladas");
        }

        if (ocorrencia.getDataHoraFechamento() != null &&
            ocorrencia.getDataHoraFechamento().isBefore(ocorrencia.getDataHoraAbertura())) {
            throw new BusinessException(
                    "Data/hora de fechamento deve ser posterior ou igual à de abertura");
        }

        return repository.save(ocorrencia);
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
