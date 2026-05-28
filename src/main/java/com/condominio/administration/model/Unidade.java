package com.condominio.administration.model;
import com.condominio.administration.enums.SituacaoUnidade;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
@Entity @Table(name = "unidades", uniqueConstraints = @UniqueConstraint(columnNames = {"bloco","numero"}))
public class Unidade {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @NotBlank(message = "Bloco é obrigatório") @Column(nullable = false) private String bloco;
    @NotBlank(message = "Número é obrigatório") @Column(nullable = false) private String numero;
    private String andar;
    private String vaga;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private SituacaoUnidade situacao = SituacaoUnidade.OCUPADA;
    @Column(nullable = false) private String identificacao;
    @PrePersist @PreUpdate private void calcId() { this.identificacao = numero + "-" + bloco; }

    public Unidade() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBloco() { return bloco; }
    public void setBloco(String bloco) { this.bloco = bloco; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getAndar() { return andar; }
    public void setAndar(String andar) { this.andar = andar; }

    public String getVaga() { return vaga; }
    public void setVaga(String vaga) { this.vaga = vaga; }

    public SituacaoUnidade getSituacao() { return situacao; }
    public void setSituacao(SituacaoUnidade situacao) { this.situacao = situacao; }

    public String getIdentificacao() { return identificacao; }
    public void setIdentificacao(String identificacao) { this.identificacao = identificacao; }
}
