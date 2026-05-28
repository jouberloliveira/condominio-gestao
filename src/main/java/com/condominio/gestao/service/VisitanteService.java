package com.condominio.gestao.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.condominio.gestao.exception.BusinessException;
import com.condominio.gestao.model.Morador;
import com.condominio.gestao.model.Visitante;
import com.condominio.gestao.repository.MoradorRepository;
import com.condominio.gestao.repository.VisitanteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VisitanteService {

    @Autowired
    private VisitanteRepository repository;
    @Autowired
    private MoradorRepository moradorRepository;

    public List<Visitante> findAll() {
        return repository.findAll();
    }

    public Visitante findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BusinessException("Visitante não encontrado"));
    }

    @Transactional
    public Visitante save(Visitante visitante) {
        if (visitante.getAutorizadoPor() != null) {
            Morador autorizador = moradorRepository.findById(visitante.getAutorizadoPor().getId())
                    .orElseThrow(() -> new BusinessException("Morador autorizador não encontrado"));
            visitante.setAutorizadoPor(autorizador);
            if (!autorizador.getUnidade().getId().equals(visitante.getUnidade().getId())) {
                throw new BusinessException(
                        "O autorizador deve ser morador da mesma unidade do visitante");
            }
        }
        return repository.save(visitante);
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
