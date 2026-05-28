package com.condominio.payments.repository;

import com.condominio.payments.enums.StatusCobranca;
import com.condominio.payments.model.TaxaCondominial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaxaCondominialRepository extends JpaRepository<TaxaCondominial, Long> {

    List<TaxaCondominial> findByUnidadeId(Long unidadeId);

    List<TaxaCondominial> findByStatus(StatusCobranca status);

    List<TaxaCondominial> findByCompetenciaAnoAndCompetenciaMes(Integer ano, Integer mes);

    @Query("SELECT t FROM TaxaCondominial t WHERE t.status = 'PENDENTE' AND t.vencimento < :hoje")
    List<TaxaCondominial> findInadimplentes(LocalDate hoje);

    boolean existsByUnidadeIdAndCompetenciaAnoAndCompetenciaMes(Long unidadeId, Integer ano, Integer mes);
}
