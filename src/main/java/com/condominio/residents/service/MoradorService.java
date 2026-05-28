package com.condominio.residents.service;
import com.condominio.common.enums.SimNao;
import com.condominio.common.exception.BusinessException;
import com.condominio.residents.model.Morador;
import com.condominio.residents.repository.MoradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service
public class MoradorService {
    @Autowired
    private MoradorRepository repository;
    public List<Morador> findAll() { return repository.findAll(); }
    public Morador findById(Long id) { return repository.findById(id).orElseThrow(() -> new BusinessException("Morador não encontrado")); }
    @Transactional
    public Morador save(Morador m) {
        if (m.getId() == null) {
            repository.findByCpf(m.getCpf()).ifPresent(x -> { throw new BusinessException("CPF já cadastrado"); });
        } else {
            if (repository.existsByCpfAndIdNot(m.getCpf(), m.getId())) throw new BusinessException("CPF já cadastrado");
        }
        if (m.getResponsavelUnidade() == SimNao.SIM) {
            long cnt = repository.countByUnidadeIdAndResponsavelUnidade(m.getUnidade().getId(), SimNao.SIM);
            if (m.getId() == null && cnt > 0) throw new BusinessException("Já existe um responsável pela unidade");
            else if (m.getId() != null) {
                boolean hasOther = repository.findByUnidadeIdAndResponsavelUnidade(m.getUnidade().getId(), SimNao.SIM)
                        .stream().anyMatch(x -> !x.getId().equals(m.getId()));
                if (hasOther) throw new BusinessException("Já existe outro responsável pela unidade");
            }
        }
        return repository.save(m);
    }
    @Transactional public void deleteById(Long id) { repository.deleteById(id); }
}
