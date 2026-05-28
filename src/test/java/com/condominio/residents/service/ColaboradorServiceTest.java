package com.condominio.residents.service;

import com.condominio.common.exception.BusinessException;
import com.condominio.residents.enums.CargoColaborador;
import com.condominio.residents.model.Colaborador;
import com.condominio.residents.repository.ColaboradorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ColaboradorServiceTest {

    @Mock ColaboradorRepository repository;
    @InjectMocks ColaboradorService service;

    private Colaborador colaborador;

    @BeforeEach
    void setUp() {
        colaborador = new Colaborador();
        colaborador.setId(1L);
        colaborador.setNome("Maria Santos");
        colaborador.setCpf("123.456.789-09");
        colaborador.setCargo(CargoColaborador.PORTEIRO);
        colaborador.setAtivo(true);
    }

    @Test
    @DisplayName("findAtivos retorna apenas colaboradores ativos")
    void findAtivos_returnsActiveOnly() {
        when(repository.findByAtivoTrue()).thenReturn(List.of(colaborador));
        assertThat(service.findAtivos()).hasSize(1).allMatch(Colaborador::getAtivo);
    }

    @Test
    @DisplayName("desligar marca colaborador como inativo")
    void desligar_setsInactive() {
        when(repository.findById(1L)).thenReturn(Optional.of(colaborador));
        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));
        service.desligar(1L);
        verify(repository).save(argThat(c -> !c.getAtivo() && c.getDataDesligamento() != null));
    }

    @Test
    @DisplayName("findById lança BusinessException quando não encontrado")
    void findById_throwsWhenNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(99L)).isInstanceOf(BusinessException.class);
    }
}
