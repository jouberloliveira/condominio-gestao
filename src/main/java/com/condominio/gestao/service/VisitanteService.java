package com.condominio.gestao.service;

import com.condominio.gestao.exception.BusinessException;
import com.condominio.gestao.model.Visitante;
import com.condominio.gestao.repository.VisitanteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitanteService {

    private final VisitanteRepository repository;

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
            if (!visitante.getAutorizadoPor().getUnidade().getId()
                    .equals(visitante.getUnidade().getId())) {
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
