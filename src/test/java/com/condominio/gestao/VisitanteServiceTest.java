package com.condominio.gestao.service;

import com.condominio.gestao.exception.BusinessException;
import com.condominio.gestao.model.Morador;
import com.condominio.gestao.model.Unidade;
import com.condominio.gestao.model.Visitante;
import com.condominio.gestao.repository.MoradorRepository;
import com.condominio.gestao.repository.VisitanteRepository;
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

    @Mock
    private VisitanteRepository visitanteRepository;

    @Mock
    private MoradorRepository moradorRepository;

    @InjectMocks
    private VisitanteService service;

    private Unidade unidade1;
    private Unidade unidade2;
    private Morador moradorUnidade1;
    private Morador moradorUnidade2;

    @BeforeEach
    void setUp() {
        unidade1 = new Unidade();
        unidade1.setId(1L);

        unidade2 = new Unidade();
        unidade2.setId(2L);

        moradorUnidade1 = new Morador();
        moradorUnidade1.setId(10L);
        moradorUnidade1.setUnidade(unidade1);

        moradorUnidade2 = new Morador();
        moradorUnidade2.setId(20L);
        moradorUnidade2.setUnidade(unidade2);
    }

    @Test
    void save_autorizadoPorMesmaUnidade_salvaComSucesso() {
        Morador stub = new Morador();
        stub.setId(10L);

        Visitante visitante = new Visitante();
        visitante.setUnidade(unidade1);
        visitante.setAutorizadoPor(stub);

        when(moradorRepository.findById(10L)).thenReturn(Optional.of(moradorUnidade1));
        when(visitanteRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        service.save(visitante);

        verify(visitanteRepository).save(visitante);
    }

    @Test
    void save_autorizadoPorOutraUnidade_lancaBusinessException() {
        // CEN-48: morador vem sem unidade carregada (só id), causava NPE antes do fix
        Morador stub = new Morador();
        stub.setId(20L);

        Visitante visitante = new Visitante();
        visitante.setUnidade(unidade1);
        visitante.setAutorizadoPor(stub);

        when(moradorRepository.findById(20L)).thenReturn(Optional.of(moradorUnidade2));

        assertThatThrownBy(() -> service.save(visitante))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("mesma unidade");

        verify(visitanteRepository, never()).save(any());
    }

    @Test
    void save_autorizadoPorInexistente_lancaBusinessException() {
        Morador stub = new Morador();
        stub.setId(99L);

        Visitante visitante = new Visitante();
        visitante.setUnidade(unidade1);
        visitante.setAutorizadoPor(stub);

        when(moradorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.save(visitante))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("autorizador não encontrado");

        verify(visitanteRepository, never()).save(any());
    }

    @Test
    void save_semAutorizadoPor_salvaComSucesso() {
        Visitante visitante = new Visitante();
        visitante.setUnidade(unidade1);

        when(visitanteRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        service.save(visitante);

        verify(moradorRepository, never()).findById(any());
        verify(visitanteRepository).save(visitante);
    }
}
