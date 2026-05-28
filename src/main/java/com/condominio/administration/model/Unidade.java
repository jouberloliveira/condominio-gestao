package com.condominio.administration.model;
import com.condominio.administration.enums.SituacaoUnidade;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity @Table(name = "units", uniqueConstraints = @UniqueConstraint(columnNames = {"bloco","numero"})) @Data @NoArgsConstructor
public class Unidade {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @NotBlank(message = "Bloco é obrigatório") @Column(nullable = false) private String bloco;
    @NotBlank(message = "Número é obrigatório") @Column(nullable = false) private String numero;
    private String andar;
    private String vaga;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private SituacaoUnidade situacao = SituacaoUnidade.OCUPADA;
    @Column(nullable = false) private String identificacao;
    @PrePersist @PreUpdate private void calcId() { this.identificacao = numero + "-" + bloco; }
}
