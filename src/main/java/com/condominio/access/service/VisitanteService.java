package com.condominio.access.service;
import com.condominio.access.model.Visitante;
import com.condominio.access.repository.VisitanteRepository;
import com.condominio.common.exception.BusinessException;
import com.condominio.residents.model.Morador;
import com.condominio.residents.repository.MoradorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service @RequiredArgsConstructor
public class VisitanteService {
    private final VisitanteRepository repository;
    private final MoradorRepository moradorRepository;
    public List<Visitante> findAll() { return repository.findAll(); }
    public Visitante findById(Long id) { return repository.findById(id).orElseThrow(() -> new BusinessException("Visitante não encontrado")); }
    @Transactional
    public Visitante save(Visitante v) {
        if (v.getAutorizadoPor() != null) {
            Morador auth = moradorRepository.findById(v.getAutorizadoPor().getId())
                    .orElseThrow(() -> new BusinessException("Morador autorizador não encontrado"));
            v.setAutorizadoPor(auth);
            if (!auth.getUnidade().getId().equals(v.getUnidade().getId()))
                throw new BusinessException("O autorizador deve ser morador da mesma unidade");
        }
        return repository.save(v);
    }
    @Transactional public void deleteById(Long id) { repository.deleteById(id); }
}
