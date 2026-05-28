package com.condominio.administration.service;
import com.condominio.administration.model.Unidade;
import com.condominio.administration.repository.UnidadeRepository;
import com.condominio.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service
public class UnidadeService {
    @Autowired
    private UnidadeRepository repository;
    public List<Unidade> findAll() { return repository.findAll(); }
    public Unidade findById(Long id) { return repository.findById(id).orElseThrow(() -> new BusinessException("Unidade não encontrada")); }
    @Transactional public Unidade save(Unidade u) { return repository.save(u); }
    @Transactional public void deleteById(Long id) { repository.deleteById(id); }
}
