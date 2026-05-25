package com.condominio.gestao.repository;

import com.condominio.gestao.enums.SimNao;
import com.condominio.gestao.model.Morador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MoradorRepository extends JpaRepository<Morador, Long> {
    Optional<Morador> findByCpf(String cpf);
    boolean existsByCpfAndIdNot(String cpf, Long id);
    List<Morador> findByUnidadeIdAndResponsavelUnidade(Long unidadeId, SimNao responsavel);
    long countByUnidadeIdAndResponsavelUnidade(Long unidadeId, SimNao responsavel);
}
