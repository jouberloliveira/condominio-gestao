package com.condominio.payments.service;

import com.condominio.administration.model.Unidade;
import com.condominio.common.exception.BusinessException;
import com.condominio.payments.enums.StatusCobranca;
import com.condominio.payments.model.TaxaCondominial;
import com.condominio.payments.repository.TaxaCondominialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaxaCondominialServiceTest {

    @Mock TaxaCondominialRepository repository;
    @InjectMocks TaxaCondominialService service;

    private Unidade unidade;
    private TaxaCondominial taxa;

    @BeforeEach
    void setUp() {
        unidade = new Unidade();
        unidade.setId(1L);
        unidade.setBloco("A");
        unidade.setNumero("101");
        unidade.setIdentificacao("101-A");

        taxa = new TaxaCondominial();
        taxa.setId(1L);
        taxa.setUnidade(unidade);
        taxa.setValor(new BigDecimal("500.00"));
        taxa.setCompetenciaAno(2026);
        taxa.setCompetenciaMes(5);
        taxa.setVencimento(LocalDate.of(2026, 5, 10));
        taxa.setStatus(StatusCobranca.PENDENTE);
    }

    @Test
    @DisplayName("findAll retorna lista de taxas")
    void findAll_returnsAllTaxas() {
        when(repository.findAll()).thenReturn(List.of(taxa));
        assertThat(service.findAll()).hasSize(1).contains(taxa);
    }

    @Test
    @DisplayName("findById retorna taxa existente")
    void findById_returnsTaxa() {
        when(repository.findById(1L)).thenReturn(Optional.of(taxa));
        assertThat(service.findById(1L)).isEqualTo(taxa);
    }

    @Test
    @DisplayName("findById lança BusinessException quando não encontrado")
    void findById_throwsWhenNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(99L))
            .isInstanceOf(BusinessException.class)
            .hasMessageContaining("não encontrada");
    }

    @Test
    @DisplayName("save lança BusinessException quando competência duplicada")
    void save_throwsOnDuplicateCompetencia() {
        when(repository.existsByUnidadeIdAndCompetenciaAnoAndCompetenciaMes(1L, 2026, 5)).thenReturn(true);
        taxa.setId(null);
        assertThatThrownBy(() -> service.save(taxa))
            .isInstanceOf(BusinessException.class)
            .hasMessageContaining("competência");
    }

    @Test
    @DisplayName("save persiste nova taxa com sucesso")
    void save_persistsNewTaxa() {
        taxa.setId(null);
        when(repository.existsByUnidadeIdAndCompetenciaAnoAndCompetenciaMes(1L, 2026, 5)).thenReturn(false);
        when(repository.save(taxa)).thenReturn(taxa);
        assertThat(service.save(taxa)).isEqualTo(taxa);
    }

    @Test
    @DisplayName("registrarPagamento atualiza status para PAGO")
    void registrarPagamento_updateStatus() {
        when(repository.findById(1L)).thenReturn(Optional.of(taxa));
        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));
        TaxaCondominial result = service.registrarPagamento(1L, LocalDate.now());
        assertThat(result.getStatus()).isEqualTo(StatusCobranca.PAGO);
        assertThat(result.getDataPagamento()).isNotNull();
    }

    @Test
    @DisplayName("registrarPagamento lança BusinessException quando já pago")
    void registrarPagamento_throwsWhenAlreadyPaid() {
        taxa.setStatus(StatusCobranca.PAGO);
        when(repository.findById(1L)).thenReturn(Optional.of(taxa));
        assertThatThrownBy(() -> service.registrarPagamento(1L, LocalDate.now()))
            .isInstanceOf(BusinessException.class)
            .hasMessageContaining("já foi paga");
    }

    @Test
    @DisplayName("findInadimplentes retorna taxas vencidas")
    void findInadimplentes_returnsOverdue() {
        when(repository.findInadimplentes(any(LocalDate.class))).thenReturn(List.of(taxa));
        assertThat(service.findInadimplentes()).hasSize(1);
    }
}
