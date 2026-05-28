package com.condominio.residents.repository;
import com.condominio.common.enums.SimNao;
import com.condominio.residents.model.Morador;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface MoradorRepository extends JpaRepository<Morador, Long> {
    Optional<Morador> findByCpf(String cpf);
    boolean existsByCpfAndIdNot(String cpf, Long id);
    long countByUnidadeIdAndResponsavelUnidade(Long unidadeId, SimNao responsavel);
    List<Morador> findByUnidadeIdAndResponsavelUnidade(Long unidadeId, SimNao responsavel);
}
