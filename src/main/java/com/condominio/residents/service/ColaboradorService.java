package com.condominio.residents.service;

import com.condominio.common.exception.BusinessException;
import com.condominio.residents.model.Colaborador;
import com.condominio.residents.repository.ColaboradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ColaboradorService {

    @Autowired
    private ColaboradorRepository repository;

    public List<Colaborador> findAll() { return repository.findAll(); }

    public List<Colaborador> findAtivos() { return repository.findByAtivoTrue(); }

    public Colaborador findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new BusinessException("Colaborador não encontrado"));
    }

    @Transactional
    public Colaborador save(Colaborador c) { return repository.save(c); }

    @Transactional
    public void desligar(Long id) {
        Colaborador c = findById(id);
        c.setAtivo(false);
        c.setDataDesligamento(java.time.LocalDate.now());
        repository.save(c);
    }

    @Transactional
    public void deleteById(Long id) { repository.deleteById(id); }
}
