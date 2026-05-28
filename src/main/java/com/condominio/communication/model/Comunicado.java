package com.condominio.communication.model;

import com.condominio.communication.enums.TipoComunicado;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "comunicados")
public class Comunicado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Título é obrigatório")
    @Column(nullable = false)
    private String titulo;

    @NotBlank(message = "Conteúdo é obrigatório")
    @Column(columnDefinition = "TEXT", nullable = false)
    private String corpo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoComunicado tipo = TipoComunicado.COMUNICADO;

    @Column(nullable = false)
    private String autor;

    @Column(nullable = false)
    private LocalDateTime publicadoEm = LocalDateTime.now();

    @Column(nullable = false)
    private boolean ativo = true;

    public Comunicado() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getCorpo() { return corpo; }
    public void setCorpo(String corpo) { this.corpo = corpo; }

    public TipoComunicado getTipo() { return tipo; }
    public void setTipo(TipoComunicado tipo) { this.tipo = tipo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public LocalDateTime getPublicadoEm() { return publicadoEm; }
    public void setPublicadoEm(LocalDateTime publicadoEm) { this.publicadoEm = publicadoEm; }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}
