package com.condominio.administration.model;
import com.condominio.administration.enums.*;
import com.condominio.residents.model.Morador;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
@Entity @Table(name = "ocorrencias") @Data @NoArgsConstructor
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
}
