package com.condominio.gestao.model;

import com.condominio.gestao.enums.AreaReserva;
import com.condominio.gestao.enums.StatusReserva;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Unidade é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unidade_id", nullable = false)
    private Unidade unidade;

    @NotNull(message = "Solicitante é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitante_id", nullable = false)
    private Morador solicitante;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AreaReserva area;

    @NotNull(message = "Data/hora de início é obrigatória")
    @Column(nullable = false)
    private LocalDateTime inicio;

    @NotNull(message = "Data/hora de fim é obrigatória")
    @Column(nullable = false)
    private LocalDateTime fim;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusReserva status = StatusReserva.SOLICITADA;

    @Column(columnDefinition = "TEXT")
    private String observacao;

    public Reserva() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    public Morador getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(Morador solicitante) {
        this.solicitante = solicitante;
    }

    public AreaReserva getArea() {
        return area;
    }

    public void setArea(AreaReserva area) {
        this.area = area;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalDateTime inicio) {
        this.inicio = inicio;
    }

    public LocalDateTime getFim() {
        return fim;
    }

    public void setFim(LocalDateTime fim) {
        this.fim = fim;
    }

    public StatusReserva getStatus() {
        return status;
    }

    public void setStatus(StatusReserva status) {
        this.status = status;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
