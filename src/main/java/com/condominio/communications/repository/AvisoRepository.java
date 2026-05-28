package com.condominio.communications.repository;

import com.condominio.communications.model.Aviso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface AvisoRepository extends JpaRepository<Aviso, Long> {

    List<Aviso> findByAtivoTrueOrderByPublicadoEmDesc();

    @Query("SELECT a FROM Aviso a WHERE a.ativo = true AND (a.expiracaoEm IS NULL OR a.expiracaoEm >= :hoje) ORDER BY a.publicadoEm DESC")
    List<Aviso> findAtivosNaoExpirados(LocalDate hoje);
}
