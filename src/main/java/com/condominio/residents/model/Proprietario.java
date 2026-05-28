package com.condominio.residents.model;

import com.condominio.administration.model.Unidade;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Table(name = "proprietarios")
public class Proprietario {

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

    private String rg;
    private String profissao;
    private String enderecoPrincipal;

    @Column(nullable = false)
    private Boolean resideNoCondominio = false;

    public Proprietario() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Unidade getUnidade() { return unidade; }
    public void setUnidade(Unidade unidade) { this.unidade = unidade; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRg() { return rg; }
    public void setRg(String rg) { this.rg = rg; }
    public String getProfissao() { return profissao; }
    public void setProfissao(String profissao) { this.profissao = profissao; }
    public String getEnderecoPrincipal() { return enderecoPrincipal; }
    public void setEnderecoPrincipal(String enderecoPrincipal) { this.enderecoPrincipal = enderecoPrincipal; }
    public Boolean getResideNoCondominio() { return resideNoCondominio; }
    public void setResideNoCondominio(Boolean resideNoCondominio) { this.resideNoCondominio = resideNoCondominio; }
}
