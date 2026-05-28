package com.condominio.access.model;
import com.condominio.access.enums.TipoVisitante;
import com.condominio.administration.model.Unidade;
import com.condominio.common.enums.SimNao;
import com.condominio.residents.model.Morador;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
@Entity @Table(name = "visitantes")
public class Visitante {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @NotNull(message = "Unidade é obrigatória") @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "unidade_id", nullable = false) private Unidade unidade;
    @NotBlank(message = "Nome é obrigatório") @Column(nullable = false) private String nome;
    private String documento;
    private String telefone;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private TipoVisitante tipoVisitante = TipoVisitante.VISITA;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "autorizado_por_id") private Morador autorizadoPor;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private SimNao ativo = SimNao.SIM;

    public Visitante() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Unidade getUnidade() { return unidade; }
    public void setUnidade(Unidade unidade) { this.unidade = unidade; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public TipoVisitante getTipoVisitante() { return tipoVisitante; }
    public void setTipoVisitante(TipoVisitante tipoVisitante) { this.tipoVisitante = tipoVisitante; }

    public Morador getAutorizadoPor() { return autorizadoPor; }
    public void setAutorizadoPor(Morador autorizadoPor) { this.autorizadoPor = autorizadoPor; }

    public SimNao getAtivo() { return ativo; }
    public void setAtivo(SimNao ativo) { this.ativo = ativo; }
}
