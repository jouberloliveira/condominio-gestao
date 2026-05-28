package com.condominio.administration.service;

import com.condominio.administration.model.Bloco;
import com.condominio.administration.repository.BlocoRepository;
import com.condominio.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class BlocoService {

    @Autowired
    private BlocoRepository repository;

    public List<Bloco> findAll() { return repository.findAll(); }

    public List<Bloco> findByCondominio(Long condominioId) {
        return repository.findByCondominioId(condominioId);
    }

    public Bloco findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new BusinessException("Bloco não encontrado"));
    }

    @Transactional
    public Bloco save(Bloco b) { return repository.save(b); }

    @Transactional
    public void deleteById(Long id) { repository.deleteById(id); }
}
