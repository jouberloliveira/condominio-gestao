package com.condominio.gestao.repository;

import com.condominio.gestao.model.Visitante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitanteRepository extends JpaRepository<Visitante, Long> {
    List<Visitante> findByUnidadeId(Long unidadeId);
}
