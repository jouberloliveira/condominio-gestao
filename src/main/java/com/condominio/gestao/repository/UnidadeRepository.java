package com.condominio.gestao.repository;

import com.condominio.gestao.model.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnidadeRepository extends JpaRepository<Unidade, Long> {
    Optional<Unidade> findByBlocoAndNumero(String bloco, String numero);
    boolean existsByBlocoAndNumeroAndIdNot(String bloco, String numero, Long id);
}
