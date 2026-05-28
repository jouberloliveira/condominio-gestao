package com.condominio.administration.model;
import com.condominio.administration.enums.*;
import com.condominio.residents.model.Morador;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
@Entity @Table(name = "ocorrencias")
public class Ocorrencia {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "unidade_id") private Unidade unidade;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "aberto_por_id") private Morador abertoPor;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private TipoOcorrencia tipo = TipoOcorrencia.MANUTENCAO;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private PrioridadeOcorrencia prioridade = PrioridadeOcorrencia.MEDIA;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private StatusOcorrencia status = StatusOcorrencia.ABERTA;
    @NotBlank(message = "Título é obrigatório") @Column(nullable = false) private String titulo;
    @NotBlank(message = "Descrição é obrigatória") @Column(columnDefinition = "TEXT", nullable = false) private String descricao;
    @Column(nullable = false) private LocalDateTime dataHoraAbertura = LocalDateTime.now();
    private LocalDateTime dataHoraFechamento;

    public Ocorrencia() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Unidade getUnidade() { return unidade; }
    public void setUnidade(Unidade unidade) { this.unidade = unidade; }

    public Morador getAbertoPor() { return abertoPor; }
    public void setAbertoPor(Morador abertoPor) { this.abertoPor = abertoPor; }

    public TipoOcorrencia getTipo() { return tipo; }
    public void setTipo(TipoOcorrencia tipo) { this.tipo = tipo; }

    public PrioridadeOcorrencia getPrioridade() { return prioridade; }
    public void setPrioridade(PrioridadeOcorrencia prioridade) { this.prioridade = prioridade; }

    public StatusOcorrencia getStatus() { return status; }
    public void setStatus(StatusOcorrencia status) { this.status = status; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public LocalDateTime getDataHoraAbertura() { return dataHoraAbertura; }
    public void setDataHoraAbertura(LocalDateTime dataHoraAbertura) { this.dataHoraAbertura = dataHoraAbertura; }

    public LocalDateTime getDataHoraFechamento() { return dataHoraFechamento; }
    public void setDataHoraFechamento(LocalDateTime dataHoraFechamento) { this.dataHoraFechamento = dataHoraFechamento; }
}
