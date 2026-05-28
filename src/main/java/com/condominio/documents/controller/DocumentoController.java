package com.condominio.documents.controller;

import com.condominio.documents.model.Documento;
import com.condominio.documents.service.DocumentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/documentos")
@Tag(name = "Documentos", description = "Upload e organização de documentos")
public class DocumentoController {

    @Autowired
    private DocumentoService service;

    @GetMapping
    @Operation(summary = "Listar documentos")
    public List<Documento> listar() { return service.findAll(); }

    @GetMapping("/categoria/{categoria}")
    @Operation(summary = "Documentos por categoria")
    public List<Documento> porCategoria(@PathVariable String categoria) {
        return service.findByCategoria(categoria);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar documento por ID")
    public ResponseEntity<Documento> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/{id}/download")
    @Operation(summary = "Download do arquivo")
    public ResponseEntity<Resource> download(@PathVariable Long id) {
        Documento doc = service.findById(id);
        Resource resource = new PathResource(Paths.get(doc.getCaminhoArquivo()));
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + doc.getNomeArquivo() + "\"")
            .contentType(MediaType.parseMediaType(doc.getTipoConteudo()))
            .body(resource);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Upload de documento")
    public ResponseEntity<Documento> upload(
            @RequestParam MultipartFile file,
            @RequestParam String nome,
            @RequestParam(required = false) String descricao,
            @RequestParam String categoria) throws IOException {
        return ResponseEntity.ok(service.salvar(file, nome, descricao, categoria));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Remover documento")
    public ResponseEntity<Void> deletar(@PathVariable Long id) throws IOException {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
