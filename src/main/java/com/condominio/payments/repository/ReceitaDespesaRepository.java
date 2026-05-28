package com.condominio.payments.repository;

import com.condominio.payments.enums.TipoLancamento;
import com.condominio.payments.model.ReceitaDespesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReceitaDespesaRepository extends JpaRepository<ReceitaDespesa, Long> {

    List<ReceitaDespesa> findByTipo(TipoLancamento tipo);

    List<ReceitaDespesa> findByDataBetween(LocalDate inicio, LocalDate fim);

    @Query("SELECT COALESCE(SUM(r.valor), 0) FROM ReceitaDespesa r WHERE r.tipo = :tipo AND r.data BETWEEN :inicio AND :fim")
    BigDecimal sumByTipoAndPeriodo(TipoLancamento tipo, LocalDate inicio, LocalDate fim);
}
