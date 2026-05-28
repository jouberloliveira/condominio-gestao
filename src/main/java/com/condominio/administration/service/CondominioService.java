package com.condominio.administration.service;

import com.condominio.administration.model.Condominio;
import com.condominio.administration.repository.CondominioRepository;
import com.condominio.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CondominioService {

    @Autowired
    private CondominioRepository repository;

    public List<Condominio> findAll() { return repository.findAll(); }

    public Condominio findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new BusinessException("Condomínio não encontrado"));
    }

    @Transactional
    public Condominio save(Condominio c) { return repository.save(c); }

    @Transactional
    public void deleteById(Long id) { repository.deleteById(id); }
}
