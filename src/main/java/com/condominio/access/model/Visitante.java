package com.condominio.access.model;
import com.condominio.access.enums.TipoVisitante;
import com.condominio.administration.model.Unidade;
import com.condominio.common.enums.SimNao;
import com.condominio.residents.model.Morador;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity @Table(name = "visitantes") @Data @NoArgsConstructor
public class Visitante {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @NotNull(message = "Unidade é obrigatória") @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "unidade_id", nullable = false) private Unidade unidade;
    @NotBlank(message = "Nome é obrigatório") @Column(nullable = false) private String nome;
    private String documento;
    private String telefone;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private TipoVisitante tipoVisitante = TipoVisitante.VISITA;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "autorizado_por_id") private Morador autorizadoPor;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private SimNao ativo = SimNao.SIM;
}
