package com.condominio.residents.model;
import com.condominio.administration.model.Unidade;
import com.condominio.common.enums.SimNao;
import com.condominio.residents.enums.TipoMorador;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
@Entity @Table(name = "residents") @Data @NoArgsConstructor
public class Morador {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @NotNull(message = "Unidade é obrigatória") @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "unidade_id", nullable = false) private Unidade unidade;
    @NotBlank(message = "Nome é obrigatório") @Column(nullable = false) private String nome;
    @CPF(message = "CPF inválido") @NotBlank(message = "CPF é obrigatório") @Column(unique = true, nullable = false) private String cpf;
    private String telefone;
    @Email(message = "E-mail inválido") private String email;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private TipoMorador tipoMorador = TipoMorador.PROPRIETARIO;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private SimNao responsavelUnidade = SimNao.NAO;
}
