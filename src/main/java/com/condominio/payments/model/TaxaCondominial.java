package com.condominio.payments.model;

import com.condominio.administration.model.Unidade;
import com.condominio.payments.enums.StatusCobranca;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "taxas_condominiais")
public class TaxaCondominial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Unidade é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unidade_id", nullable = false)
    private Unidade unidade;

    @NotNull(message = "Valor é obrigatório")
    @Positive(message = "Valor deve ser positivo")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @NotNull(message = "Competência é obrigatória")
    @Column(nullable = false)
    private Integer competenciaAno;

    @NotNull(message = "Mês de competência é obrigatório")
    @Column(nullable = false)
    private Integer competenciaMes;

    @NotNull(message = "Vencimento é obrigatório")
    @Column(nullable = false)
    private LocalDate vencimento;

    private LocalDate dataPagamento;

    @Column(precision = 10, scale = 2)
    private BigDecimal multa;

    @Column(precision = 10, scale = 2)
    private BigDecimal juros;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusCobranca status = StatusCobranca.PENDENTE;

    @Column(columnDefinition = "TEXT")
    private String observacao;

    @Column(nullable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();

    public TaxaCondominial() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Unidade getUnidade() { return unidade; }
    public void setUnidade(Unidade unidade) { this.unidade = unidade; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public Integer getCompetenciaAno() { return competenciaAno; }
    public void setCompetenciaAno(Integer competenciaAno) { this.competenciaAno = competenciaAno; }
    public Integer getCompetenciaMes() { return competenciaMes; }
    public void setCompetenciaMes(Integer competenciaMes) { this.competenciaMes = competenciaMes; }
    public LocalDate getVencimento() { return vencimento; }
    public void setVencimento(LocalDate vencimento) { this.vencimento = vencimento; }
    public LocalDate getDataPagamento() { return dataPagamento; }
    public void setDataPagamento(LocalDate dataPagamento) { this.dataPagamento = dataPagamento; }
    public BigDecimal getMulta() { return multa; }
    public void setMulta(BigDecimal multa) { this.multa = multa; }
    public BigDecimal getJuros() { return juros; }
    public void setJuros(BigDecimal juros) { this.juros = juros; }
    public StatusCobranca getStatus() { return status; }
    public void setStatus(StatusCobranca status) { this.status = status; }
    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }
}
