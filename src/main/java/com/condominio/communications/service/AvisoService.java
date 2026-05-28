package com.condominio.communications.service;

import com.condominio.common.exception.BusinessException;
import com.condominio.communications.model.Aviso;
import com.condominio.communications.repository.AvisoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class AvisoService {

    @Autowired
    private AvisoRepository repository;

    public List<Aviso> findAll() { return repository.findAll(); }

    public List<Aviso> findAtivos() {
        return repository.findAtivosNaoExpirados(LocalDate.now());
    }

    public Aviso findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new BusinessException("Aviso não encontrado"));
    }

    @Transactional
    public Aviso save(Aviso aviso) { return repository.save(aviso); }

    @Transactional
    public void deleteById(Long id) {
        Aviso aviso = findById(id);
        aviso.setAtivo(false);
        repository.save(aviso);
    }
}
