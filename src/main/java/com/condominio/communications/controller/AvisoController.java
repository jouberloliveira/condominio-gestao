package com.condominio.communications.controller;

import com.condominio.communications.model.Aviso;
import com.condominio.communications.service.AvisoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/avisos")
@Tag(name = "Avisos", description = "Quadro de avisos e comunicados")
public class AvisoController {

    @Autowired
    private AvisoService service;

    @GetMapping
    @Operation(summary = "Listar avisos ativos")
    public List<Aviso> listar() { return service.findAtivos(); }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar aviso por ID")
    public ResponseEntity<Aviso> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Criar aviso")
    public ResponseEntity<Aviso> criar(@Valid @RequestBody Aviso aviso) {
        return ResponseEntity.ok(service.save(aviso));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualizar aviso")
    public ResponseEntity<Aviso> atualizar(@PathVariable Long id, @Valid @RequestBody Aviso aviso) {
        aviso.setId(id);
        return ResponseEntity.ok(service.save(aviso));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Desativar aviso")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
