package com.condominio.residents.service;

import com.condominio.administration.model.Unidade;
import com.condominio.common.exception.BusinessException;
import com.condominio.residents.model.Proprietario;
import com.condominio.residents.repository.ProprietarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProprietarioServiceTest {

    @Mock ProprietarioRepository repository;
    @InjectMocks ProprietarioService service;

    private Proprietario proprietario;

    @BeforeEach
    void setUp() {
        Unidade u = new Unidade();
        u.setId(1L);
        proprietario = new Proprietario();
        proprietario.setNome("Carlos Ferreira");
        proprietario.setCpf("987.654.321-00");
        proprietario.setUnidade(u);
    }

    @Test
    @DisplayName("save lança BusinessException quando CPF já existe")
    void save_throwsOnDuplicateCpf() {
        when(repository.findByCpf("987.654.321-00")).thenReturn(Optional.of(proprietario));
        assertThatThrownBy(() -> service.save(proprietario))
            .isInstanceOf(BusinessException.class)
            .hasMessageContaining("CPF");
    }

    @Test
    @DisplayName("save persiste proprietário com CPF único")
    void save_succeeds() {
        when(repository.findByCpf("987.654.321-00")).thenReturn(Optional.empty());
        when(repository.save(proprietario)).thenReturn(proprietario);
        assertThat(service.save(proprietario)).isEqualTo(proprietario);
    }

    @Test
    @DisplayName("findById lança BusinessException quando não encontrado")
    void findById_throws() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(99L)).isInstanceOf(BusinessException.class);
    }
}
