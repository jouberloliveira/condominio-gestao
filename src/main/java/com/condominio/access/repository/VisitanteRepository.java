package com.condominio.access.repository;
import com.condominio.access.model.Visitante;
import org.springframework.data.jpa.repository.JpaRepository;
public interface VisitanteRepository extends JpaRepository<Visitante, Long> {}
