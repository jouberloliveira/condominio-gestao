package com.condominio.payments.controller;

import com.condominio.payments.model.ReceitaDespesa;
import com.condominio.payments.service.ReceitaDespesaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lancamentos")
@Tag(name = "Receitas e Despesas", description = "Gestão financeira geral")
@PreAuthorize("hasRole('ADMIN')")
public class ReceitaDespesaController {

    @Autowired
    private ReceitaDespesaService service;

    @GetMapping
    @Operation(summary = "Listar todos os lançamentos")
    public List<ReceitaDespesa> listar() { return service.findAll(); }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar lançamento por ID")
    public ResponseEntity<ReceitaDespesa> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/periodo")
    @Operation(summary = "Lançamentos por período")
    public List<ReceitaDespesa> porPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        return service.findByPeriodo(inicio, fim);
    }

    @GetMapping("/saldo")
    @Operation(summary = "Saldo do período")
    public Map<String, BigDecimal> saldo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        return Map.of(
            "receitas", service.totalReceitas(inicio, fim),
            "despesas", service.totalDespesas(inicio, fim),
            "saldo", service.saldo(inicio, fim)
        );
    }

    @PostMapping
    @Operation(summary = "Registrar lançamento")
    public ResponseEntity<ReceitaDespesa> criar(@Valid @RequestBody ReceitaDespesa lancamento) {
        return ResponseEntity.ok(service.save(lancamento));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover lançamento")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
