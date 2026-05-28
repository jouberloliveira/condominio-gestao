package com.condominio.administration.service;

import com.condominio.administration.enums.AreaReserva;
import com.condominio.administration.model.Reserva;
import com.condominio.administration.model.Unidade;
import com.condominio.administration.repository.ReservaRepository;
import com.condominio.common.exception.BusinessException;
import com.condominio.residents.model.Morador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

    @Mock ReservaRepository repository;
    @InjectMocks ReservaService service;

    private Reserva reserva;

    @BeforeEach
    void setUp() {
        Unidade u = new Unidade();
        u.setId(1L);
        Morador m = new Morador();
        m.setId(1L);

        reserva = new Reserva();
        reserva.setUnidade(u);
        reserva.setSolicitante(m);
        reserva.setArea(AreaReserva.SALAO_DE_FESTAS);
        reserva.setInicio(LocalDateTime.of(2026, 6, 1, 10, 0));
        reserva.setFim(LocalDateTime.of(2026, 6, 1, 14, 0));
    }

    @Test
    @DisplayName("save lança BusinessException quando há conflito de horário")
    void save_throwsOnConflict() {
        when(repository.findConflitos(any(), any(), any(), any())).thenReturn(List.of(reserva));
        assertThatThrownBy(() -> service.save(reserva))
            .isInstanceOf(BusinessException.class)
            .hasMessageContaining("horário");
    }

    @Test
    @DisplayName("save lança BusinessException quando fim <= início")
    void save_throwsWhenInvalidPeriod() {
        reserva.setFim(reserva.getInicio());
        assertThatThrownBy(() -> service.save(reserva))
            .isInstanceOf(BusinessException.class)
            .hasMessageContaining("anterior");
    }

    @Test
    @DisplayName("save persiste reserva sem conflito")
    void save_succeedsWithoutConflict() {
        when(repository.findConflitos(any(), any(), any(), any())).thenReturn(List.of());
        when(repository.save(reserva)).thenReturn(reserva);
        assertThat(service.save(reserva)).isEqualTo(reserva);
    }

    @Test
    @DisplayName("findById lança BusinessException quando não encontrado")
    void findById_throwsWhenNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(99L)).isInstanceOf(BusinessException.class);
    }
}
