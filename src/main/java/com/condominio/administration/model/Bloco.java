package com.condominio.administration.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "blocos", uniqueConstraints = @UniqueConstraint(columnNames = {"condominio_id", "nome"}))
public class Bloco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Condomínio é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "condominio_id", nullable = false)
    private Condominio condominio;

    @NotBlank(message = "Nome é obrigatório")
    @Column(nullable = false)
    private String nome;

    private String descricao;

    @Column(nullable = false)
    private Integer totalAndares = 1;

    @Column(nullable = false)
    private Integer totalUnidades = 0;

    public Bloco() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Condominio getCondominio() { return condominio; }
    public void setCondominio(Condominio condominio) { this.condominio = condominio; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Integer getTotalAndares() { return totalAndares; }
    public void setTotalAndares(Integer totalAndares) { this.totalAndares = totalAndares; }
    public Integer getTotalUnidades() { return totalUnidades; }
    public void setTotalUnidades(Integer totalUnidades) { this.totalUnidades = totalUnidades; }
}
