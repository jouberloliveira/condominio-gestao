package com.condominio.access.repository;

import com.condominio.access.model.Visitante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VisitanteRepository extends JpaRepository<Visitante, Long> {
    List<Visitante> findByUnidadeId(Long unidadeId);
    List<Visitante> findByDataHoraSaidaIsNullAndDataHoraEntradaIsNotNull();
}
