package com.condominio.gestao.repository;

import com.condominio.gestao.enums.AreaReserva;
import com.condominio.gestao.enums.StatusReserva;
import com.condominio.gestao.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    @Query("SELECT COUNT(r) > 0 FROM Reserva r WHERE r.area = :area " +
           "AND r.status = :status " +
           "AND ((r.inicio < :fim AND r.fim > :inicio)) " +
           "AND (:reservaId IS NULL OR r.id <> :reservaId)")
    boolean existsConflito(@Param("area") AreaReserva area,
                          @Param("status") StatusReserva status,
                          @Param("inicio") LocalDateTime inicio,
                          @Param("fim") LocalDateTime fim,
                          @Param("reservaId") Long reservaId);
}
