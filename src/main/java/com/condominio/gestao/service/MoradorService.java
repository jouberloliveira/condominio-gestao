package com.condominio.gestao.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.condominio.gestao.enums.SimNao;
import com.condominio.gestao.exception.BusinessException;
import com.condominio.gestao.model.Morador;
import com.condominio.gestao.repository.MoradorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MoradorService {

    @Autowired
    private MoradorRepository repository;

    public List<Morador> findAll() {
        return repository.findAll();
    }

    public Morador findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BusinessException("Morador não encontrado"));
    }

    @Transactional
    public Morador save(Morador morador) {
        if (morador.getId() == null) {
            repository.findByCpf(morador.getCpf())
                    .ifPresent(m -> {
                        throw new BusinessException("CPF já cadastrado para outro morador");
                    });
        } else {
            if (repository.existsByCpfAndIdNot(morador.getCpf(), morador.getId())) {
                throw new BusinessException("CPF já cadastrado para outro morador");
            }
        }

        if (morador.getResponsavelUnidade() == SimNao.SIM) {
            long count = repository.countByUnidadeIdAndResponsavelUnidade(
                    morador.getUnidade().getId(), SimNao.SIM);
            if (morador.getId() == null && count > 0) {
                throw new BusinessException("Já existe um responsável pela unidade");
            } else if (morador.getId() != null) {
                List<Morador> responsaveis = repository.findByUnidadeIdAndResponsavelUnidade(
                        morador.getUnidade().getId(), SimNao.SIM);
                boolean hasOther = responsaveis.stream()
                        .anyMatch(m -> !m.getId().equals(morador.getId()));
                if (hasOther) {
                    throw new BusinessException("Já existe outro responsável pela unidade");
                }
            }
        }

        return repository.save(morador);
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
