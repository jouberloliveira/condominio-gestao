package com.condominio.payments.service;

import com.condominio.common.exception.BusinessException;
import com.condominio.payments.enums.CategoriaLancamento;
import com.condominio.payments.enums.TipoLancamento;
import com.condominio.payments.model.ReceitaDespesa;
import com.condominio.payments.repository.ReceitaDespesaRepository;
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
class ReceitaDespesaServiceTest {

    @Mock ReceitaDespesaRepository repository;
    @InjectMocks ReceitaDespesaService service;

    private ReceitaDespesa lancamento;

    @BeforeEach
    void setUp() {
        lancamento = new ReceitaDespesa();
        lancamento.setId(1L);
        lancamento.setTipo(TipoLancamento.RECEITA);
        lancamento.setCategoria(CategoriaLancamento.TAXA_CONDOMINIAL);
        lancamento.setDescricao("Taxa maio");
        lancamento.setValor(new BigDecimal("1000.00"));
        lancamento.setData(LocalDate.of(2026, 5, 1));
    }

    @Test
    @DisplayName("findById retorna lançamento")
    void findById_returnsLancamento() {
        when(repository.findById(1L)).thenReturn(Optional.of(lancamento));
        assertThat(service.findById(1L)).isEqualTo(lancamento);
    }

    @Test
    @DisplayName("findById lança BusinessException quando não encontrado")
    void findById_throws() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(99L)).isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("saldo calcula corretamente")
    void saldo_calculatesCorrectly() {
        LocalDate inicio = LocalDate.of(2026, 5, 1);
        LocalDate fim = LocalDate.of(2026, 5, 31);
        when(repository.sumByTipoAndPeriodo(TipoLancamento.RECEITA, inicio, fim))
            .thenReturn(new BigDecimal("3000.00"));
        when(repository.sumByTipoAndPeriodo(TipoLancamento.DESPESA, inicio, fim))
            .thenReturn(new BigDecimal("1200.00"));
        assertThat(service.saldo(inicio, fim)).isEqualByComparingTo(new BigDecimal("1800.00"));
    }

    @Test
    @DisplayName("save persiste lançamento")
    void save_persists() {
        when(repository.save(lancamento)).thenReturn(lancamento);
        assertThat(service.save(lancamento)).isEqualTo(lancamento);
    }
}
