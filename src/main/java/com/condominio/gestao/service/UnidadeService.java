package com.condominio.gestao.service;

import com.condominio.gestao.exception.BusinessException;
import com.condominio.gestao.model.Unidade;
import com.condominio.gestao.repository.UnidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnidadeService {

    private final UnidadeRepository repository;

    public List<Unidade> findAll() {
        return repository.findAll();
    }

    public Unidade findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BusinessException("Unidade não encontrada"));
    }

    @Transactional
    public Unidade save(Unidade unidade) {
        if (unidade.getId() == null) {
            repository.findByBlocoAndNumero(unidade.getBloco(), unidade.getNumero())
                    .ifPresent(u -> {
                        throw new BusinessException("Já existe uma unidade com este bloco e número");
                    });
        } else {
            if (repository.existsByBlocoAndNumeroAndIdNot(unidade.getBloco(), unidade.getNumero(), unidade.getId())) {
                throw new BusinessException("Já existe outra unidade com este bloco e número");
            }
        }
        return repository.save(unidade);
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
