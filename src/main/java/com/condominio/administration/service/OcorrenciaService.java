package com.condominio.administration.service;
import com.condominio.administration.model.Ocorrencia;
import com.condominio.administration.repository.OcorrenciaRepository;
import com.condominio.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service @RequiredArgsConstructor
public class OcorrenciaService {
    private final OcorrenciaRepository repository;
    public List<Ocorrencia> findAll() { return repository.findAll(); }
    public Ocorrencia findById(Long id) { return repository.findById(id).orElseThrow(() -> new BusinessException("Ocorrência não encontrada")); }
    @Transactional public Ocorrencia save(Ocorrencia o) { return repository.save(o); }
    @Transactional public void deleteById(Long id) { repository.deleteById(id); }
}
