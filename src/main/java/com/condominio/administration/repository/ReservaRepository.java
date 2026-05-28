package com.condominio.administration.repository;

import com.condominio.administration.enums.AreaReserva;
import com.condominio.administration.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByUnidadeId(Long unidadeId);

    @Query("SELECT r FROM Reserva r WHERE r.area = :area AND r.status NOT IN ('CANCELADA') " +
           "AND ((r.inicio < :fim AND r.fim > :inicio)) AND (:excludeId IS NULL OR r.id <> :excludeId)")
    List<Reserva> findConflitos(AreaReserva area, LocalDateTime inicio, LocalDateTime fim, Long excludeId);
}
