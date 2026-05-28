package com.condominio.access.service;

import com.condominio.access.model.Visitante;
import com.condominio.access.repository.VisitanteRepository;
import com.condominio.administration.model.Unidade;
import com.condominio.common.enums.SimNao;
import com.condominio.common.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitanteServiceTest {

    @Mock VisitanteRepository repository;
    @InjectMocks VisitanteService service;

    private Visitante visitante;

    @BeforeEach
    void setUp() {
        Unidade u = new Unidade();
        u.setId(1L);
        visitante = new Visitante();
        visitante.setId(1L);
        visitante.setNome("João Silva");
        visitante.setUnidade(u);
    }

    @Test
    @DisplayName("registrarEntrada define dataHoraEntrada")
    void registrarEntrada_setsTimestamp() {
        when(repository.findById(1L)).thenReturn(Optional.of(visitante));
        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));
        Visitante result = service.registrarEntrada(1L);
        assertThat(result.getDataHoraEntrada()).isNotNull();
        assertThat(result.getAtivo()).isEqualTo(SimNao.SIM);
    }

    @Test
    @DisplayName("registrarEntrada lança BusinessException quando visitante já está presente")
    void registrarEntrada_throwsWhenAlreadyInside() {
        visitante.setDataHoraEntrada(java.time.LocalDateTime.now());
        when(repository.findById(1L)).thenReturn(Optional.of(visitante));
        assertThatThrownBy(() -> service.registrarEntrada(1L))
            .isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("registrarSaida define dataHoraSaida")
    void registrarSaida_setsTimestamp() {
        visitante.setDataHoraEntrada(java.time.LocalDateTime.now());
        when(repository.findById(1L)).thenReturn(Optional.of(visitante));
        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));
        Visitante result = service.registrarSaida(1L);
        assertThat(result.getDataHoraSaida()).isNotNull();
        assertThat(result.getAtivo()).isEqualTo(SimNao.NAO);
    }

    @Test
    @DisplayName("registrarSaida lança BusinessException quando não registrou entrada")
    void registrarSaida_throwsWithoutEntry() {
        when(repository.findById(1L)).thenReturn(Optional.of(visitante));
        assertThatThrownBy(() -> service.registrarSaida(1L))
            .isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("findById lança BusinessException quando não encontrado")
    void findById_throwsWhenNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(99L))
            .isInstanceOf(BusinessException.class)
            .hasMessageContaining("não encontrado");
    }
}
