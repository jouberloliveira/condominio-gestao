package com.condominio.access.service;

import com.condominio.access.model.Visitante;
import com.condominio.access.repository.VisitanteRepository;
import com.condominio.administration.model.Unidade;
import com.condominio.common.exception.BusinessException;
import com.condominio.residents.model.Morador;
import com.condominio.residents.repository.MoradorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitanteServiceTest {

    @Mock private VisitanteRepository visitanteRepository;
    @Mock private MoradorRepository moradorRepository;
    @InjectMocks private VisitanteService service;

    private Unidade unidade1;
    private Unidade unidade2;
    private Morador moradorUnidade1;
    private Morador moradorUnidade2;

    @BeforeEach
    void setUp() {
        unidade1 = new Unidade(); unidade1.setId(1L);
        unidade2 = new Unidade(); unidade2.setId(2L);
        moradorUnidade1 = new Morador(); moradorUnidade1.setId(10L); moradorUnidade1.setUnidade(unidade1);
        moradorUnidade2 = new Morador(); moradorUnidade2.setId(20L); moradorUnidade2.setUnidade(unidade2);
    }

    @Test
    void save_autorizadoPorMesmaUnidade_salvaComSucesso() {
        Morador stub = new Morador(); stub.setId(10L);
        Visitante v = new Visitante(); v.setUnidade(unidade1); v.setAutorizadoPor(stub);
        when(moradorRepository.findById(10L)).thenReturn(Optional.of(moradorUnidade1));
        when(visitanteRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        service.save(v);
        verify(visitanteRepository).save(v);
    }

    @Test
    void save_autorizadoPorOutraUnidade_lancaBusinessException() {
        Morador stub = new Morador(); stub.setId(20L);
        Visitante v = new Visitante(); v.setUnidade(unidade1); v.setAutorizadoPor(stub);
        when(moradorRepository.findById(20L)).thenReturn(Optional.of(moradorUnidade2));
        assertThatThrownBy(() -> service.save(v))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("mesma unidade");
        verify(visitanteRepository, never()).save(any());
    }

    @Test
    void save_autorizadoPorInexistente_lancaBusinessException() {
        Morador stub = new Morador(); stub.setId(99L);
        Visitante v = new Visitante(); v.setUnidade(unidade1); v.setAutorizadoPor(stub);
        when(moradorRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.save(v))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("autorizador não encontrado");
        verify(visitanteRepository, never()).save(any());
    }

    @Test
    void save_semAutorizadoPor_salvaComSucesso() {
        Visitante v = new Visitante(); v.setUnidade(unidade1);
        when(visitanteRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        service.save(v);
        verify(moradorRepository, never()).findById(any());
        verify(visitanteRepository).save(v);
    }
}
