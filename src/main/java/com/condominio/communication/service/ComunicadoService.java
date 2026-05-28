package com.condominio.communication.service;

import com.condominio.communication.model.Comunicado;
import com.condominio.communication.repository.ComunicadoRepository;
import com.condominio.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComunicadoService {

    @Autowired
    private ComunicadoRepository repository;

    public List<Comunicado> findAll() {
        return repository.findAll();
    }

    public List<Comunicado> findAtivos() {
        return repository.findByAtivoTrueOrderByPublicadoEmDesc();
    }

    public Comunicado findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BusinessException("Comunicado não encontrado"));
    }

    public Comunicado save(Comunicado comunicado) {
        return repository.save(comunicado);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public long countAtivos() {
        return repository.countByAtivoTrue();
    }
}
