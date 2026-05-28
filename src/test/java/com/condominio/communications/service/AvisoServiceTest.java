package com.condominio.communications.service;

import com.condominio.common.exception.BusinessException;
import com.condominio.communications.enums.TipoAviso;
import com.condominio.communications.model.Aviso;
import com.condominio.communications.repository.AvisoRepository;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AvisoServiceTest {

    @Mock AvisoRepository repository;
    @InjectMocks AvisoService service;

    private Aviso aviso;

    @BeforeEach
    void setUp() {
        aviso = new Aviso();
        aviso.setId(1L);
        aviso.setTitulo("Manutenção elevador");
        aviso.setConteudo("Elevador fora de operação amanhã.");
        aviso.setTipo(TipoAviso.MANUTENCAO);
        aviso.setAtivo(true);
    }

    @Test
    @DisplayName("findAtivos retorna apenas avisos não expirados")
    void findAtivos_returnsActive() {
        when(repository.findAtivosNaoExpirados(any())).thenReturn(List.of(aviso));
        assertThat(service.findAtivos()).hasSize(1).contains(aviso);
    }

    @Test
    @DisplayName("findById retorna aviso")
    void findById_returnsAviso() {
        when(repository.findById(1L)).thenReturn(Optional.of(aviso));
        assertThat(service.findById(1L)).isEqualTo(aviso);
    }

    @Test
    @DisplayName("findById lança BusinessException quando não encontrado")
    void findById_throwsWhenNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(99L))
            .isInstanceOf(BusinessException.class)
            .hasMessageContaining("não encontrado");
    }

    @Test
    @DisplayName("save persiste aviso")
    void save_persists() {
        when(repository.save(aviso)).thenReturn(aviso);
        assertThat(service.save(aviso)).isEqualTo(aviso);
    }

    @Test
    @DisplayName("deleteById faz soft-delete")
    void deleteById_softDeletes() {
        when(repository.findById(1L)).thenReturn(Optional.of(aviso));
        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));
        service.deleteById(1L);
        verify(repository).save(argThat(a -> !a.getAtivo()));
    }
}
