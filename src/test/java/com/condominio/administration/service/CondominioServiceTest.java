package com.condominio.administration.service;

import com.condominio.administration.model.Condominio;
import com.condominio.administration.repository.CondominioRepository;
import com.condominio.common.exception.BusinessException;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CondominioServiceTest {

    @Mock CondominioRepository repository;
    @InjectMocks CondominioService service;

    private Condominio condominio;

    @BeforeEach
    void setUp() {
        condominio = new Condominio();
        condominio.setId(1L);
        condominio.setNome("Residencial Parque");
    }

    @Test
    @DisplayName("findAll retorna lista de condomínios")
    void findAll_returnsList() {
        when(repository.findAll()).thenReturn(List.of(condominio));
        assertThat(service.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("findById retorna condomínio")
    void findById_returns() {
        when(repository.findById(1L)).thenReturn(Optional.of(condominio));
        assertThat(service.findById(1L)).isEqualTo(condominio);
    }

    @Test
    @DisplayName("findById lança BusinessException quando não encontrado")
    void findById_throws() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(99L))
            .isInstanceOf(BusinessException.class)
            .hasMessageContaining("não encontrado");
    }

    @Test
    @DisplayName("save persiste condomínio")
    void save_persists() {
        when(repository.save(condominio)).thenReturn(condominio);
        assertThat(service.save(condominio)).isEqualTo(condominio);
    }
}
