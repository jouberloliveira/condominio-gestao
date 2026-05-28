package com.condominio.documents.repository;

import com.condominio.documents.model.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {

    List<Documento> findByAtivoTrueOrderByEnviadoEmDesc();

    List<Documento> findByCategoriaAndAtivoTrue(String categoria);
}
