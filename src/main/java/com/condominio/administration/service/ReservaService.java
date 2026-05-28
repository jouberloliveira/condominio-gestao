package com.condominio.administration.service;

import com.condominio.administration.model.Reserva;
import com.condominio.administration.repository.ReservaRepository;
import com.condominio.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository repository;

    public List<Reserva> findAll() { return repository.findAll(); }

    public Reserva findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new BusinessException("Reserva não encontrada"));
    }

    @Transactional
    public Reserva save(Reserva r) {
        if (r.getInicio() != null && r.getFim() != null) {
            if (!r.getInicio().isBefore(r.getFim())) {
                throw new BusinessException("Início deve ser anterior ao fim da reserva");
            }
            Long excludeId = r.getId();
            List<Reserva> conflitos = repository.findConflitos(r.getArea(), r.getInicio(), r.getFim(), excludeId);
            if (!conflitos.isEmpty()) {
                throw new BusinessException("Já existe reserva neste horário para a área selecionada");
            }
        }
        return repository.save(r);
    }

    @Transactional
    public void deleteById(Long id) { repository.deleteById(id); }
}
