package com.condominio.gestao.service;

import com.condominio.gestao.enums.StatusReserva;
import com.condominio.gestao.exception.BusinessException;
import com.condominio.gestao.model.Reserva;
import com.condominio.gestao.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository repository;

    public List<Reserva> findAll() {
        return repository.findAll();
    }

    public Reserva findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BusinessException("Reserva não encontrada"));
    }

    @Transactional
    public Reserva save(Reserva reserva) {
        if (!reserva.getFim().isAfter(reserva.getInicio())) {
            throw new BusinessException("A data/hora de término deve ser posterior à de início");
        }

        if (!reserva.getSolicitante().getUnidade().getId()
                .equals(reserva.getUnidade().getId())) {
            throw new BusinessException(
                    "O solicitante deve ser morador da unidade para a qual está reservando");
        }

        Long reservaId = reserva.getId();
        if (repository.existsConflito(reserva.getArea(), StatusReserva.APROVADA,
                reserva.getInicio(), reserva.getFim(), reservaId)) {
            throw new BusinessException(
                    "Já existe uma reserva aprovada para esta área no horário solicitado");
        }

        return repository.save(reserva);
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
