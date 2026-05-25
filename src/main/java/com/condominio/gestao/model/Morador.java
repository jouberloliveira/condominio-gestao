package com.condominio.gestao.model;

import com.condominio.gestao.enums.SimNao;
import com.condominio.gestao.enums.TipoMorador;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Table(name = "moradores")
public class Morador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Unidade é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unidade_id", nullable = false)
    private Unidade unidade;

    @NotBlank(message = "Nome é obrigatório")
    @Column(nullable = false)
    private String nome;

    @CPF(message = "CPF inválido")
    @NotBlank(message = "CPF é obrigatório")
    @Column(unique = true, nullable = false)
    private String cpf;

    private String telefone;

    @Email(message = "E-mail inválido")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMorador tipoMorador = TipoMorador.PROPRIETARIO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SimNao responsavelUnidade = SimNao.NAO;

    public Morador() {
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TipoMorador getTipoMorador() {
        return tipoMorador;
    }

    public void setTipoMorador(TipoMorador tipoMorador) {
        this.tipoMorador = tipoMorador;
    }

    public SimNao getResponsavelUnidade() {
        return responsavelUnidade;
    }

    public void setResponsavelUnidade(SimNao responsavelUnidade) {
        this.responsavelUnidade = responsavelUnidade;
    }
}
