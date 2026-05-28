package com.condominio.documents.service;

import com.condominio.common.exception.BusinessException;
import com.condominio.documents.model.Documento;
import com.condominio.documents.repository.DocumentoRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class DocumentoService {

    @Value("${app.upload.dir:${user.home}/condominio-uploads}")
    private String uploadDir;

    private final DocumentoRepository repository;

    public DocumentoService(DocumentoRepository repository) {
        this.repository = repository;
    }

    public List<Documento> findAll() {
        return repository.findByAtivoTrueOrderByEnviadoEmDesc();
    }

    public List<Documento> findByCategoria(String categoria) {
        return repository.findByCategoriaAndAtivoTrue(categoria);
    }

    public Documento findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new BusinessException("Documento não encontrado"));
    }

    @Transactional
    public Documento salvar(MultipartFile file, String nome, String descricao, String categoria) throws IOException {
        if (file.isEmpty()) {
            throw new BusinessException("Arquivo não pode estar vazio");
        }

        String extensao = FilenameUtils.getExtension(file.getOriginalFilename());
        String nomeUnico = UUID.randomUUID() + "." + extensao;
        Path destino = Paths.get(uploadDir).resolve(nomeUnico);
        Files.createDirectories(destino.getParent());
        Files.copy(file.getInputStream(), destino);

        Documento doc = new Documento();
        doc.setNome(nome);
        doc.setDescricao(descricao);
        doc.setCategoria(categoria);
        doc.setNomeArquivo(file.getOriginalFilename());
        doc.setTipoConteudo(file.getContentType());
        doc.setTamanhoBytes(file.getSize());
        doc.setCaminhoArquivo(destino.toString());

        return repository.save(doc);
    }

    @Transactional
    public void deleteById(Long id) throws IOException {
        Documento doc = findById(id);
        Path arquivo = Paths.get(doc.getCaminhoArquivo());
        if (Files.exists(arquivo)) {
            Files.delete(arquivo);
        }
        doc.setAtivo(false);
        repository.save(doc);
    }
}
