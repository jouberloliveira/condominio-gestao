package com.condominio.residents.repository;

import com.condominio.residents.model.Proprietario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProprietarioRepository extends JpaRepository<Proprietario, Long> {
    List<Proprietario> findByUnidadeId(Long unidadeId);
    Optional<Proprietario> findByCpf(String cpf);
}
