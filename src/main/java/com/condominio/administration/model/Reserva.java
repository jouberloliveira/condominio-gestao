package com.condominio.administration.model;
import com.condominio.administration.enums.*;
import com.condominio.residents.model.Morador;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
@Entity @Table(name = "reservas") @Data @NoArgsConstructor
public class Reserva {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @NotNull @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "unidade_id", nullable = false) private Unidade unidade;
    @NotNull @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "solicitante_id", nullable = false) private Morador solicitante;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private AreaReserva area;
    @NotNull @Column(nullable = false) private LocalDateTime inicio;
    @NotNull @Column(nullable = false) private LocalDateTime fim;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private StatusReserva status = StatusReserva.SOLICITADA;
    @Column(columnDefinition = "TEXT") private String observacao;
}
