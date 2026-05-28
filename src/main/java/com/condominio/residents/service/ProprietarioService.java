package com.condominio.residents.service;

import com.condominio.common.exception.BusinessException;
import com.condominio.residents.model.Proprietario;
import com.condominio.residents.repository.ProprietarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ProprietarioService {

    @Autowired
    private ProprietarioRepository repository;

    public List<Proprietario> findAll() { return repository.findAll(); }

    public Proprietario findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new BusinessException("Proprietário não encontrado"));
    }

    public List<Proprietario> findByUnidade(Long unidadeId) {
        return repository.findByUnidadeId(unidadeId);
    }

    @Transactional
    public Proprietario save(Proprietario p) {
        if (p.getId() == null) {
            repository.findByCpf(p.getCpf()).ifPresent(e -> {
                throw new BusinessException("CPF já cadastrado para outro proprietário");
            });
        }
        return repository.save(p);
    }

    @Transactional
    public void deleteById(Long id) { repository.deleteById(id); }
}
