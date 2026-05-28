package com.condominio.payments.controller;

import com.condominio.payments.model.TaxaCondominial;
import com.condominio.payments.service.TaxaCondominialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/taxas")
@Tag(name = "Taxas Condominiais", description = "Gestão de cobranças condominiais")
public class TaxaCondominialController {

    @Autowired
    private TaxaCondominialService service;

    @GetMapping
    @Operation(summary = "Listar todas as taxas")
    public List<TaxaCondominial> listar() { return service.findAll(); }

    @GetMapping("/inadimplentes")
    @Operation(summary = "Listar unidades inadimplentes")
    public List<TaxaCondominial> inadimplentes() { return service.findInadimplentes(); }

    @GetMapping("/unidade/{unidadeId}")
    @Operation(summary = "Taxas por unidade")
    public List<TaxaCondominial> porUnidade(@PathVariable Long unidadeId) {
        return service.findByUnidade(unidadeId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar taxa por ID")
    public ResponseEntity<TaxaCondominial> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Lançar nova taxa")
    public ResponseEntity<TaxaCondominial> criar(@Valid @RequestBody TaxaCondominial taxa) {
        return ResponseEntity.ok(service.save(taxa));
    }

    @PutMapping("/{id}/pagar")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Registrar pagamento")
    public ResponseEntity<TaxaCondominial> pagar(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataPagamento) {
        return ResponseEntity.ok(service.registrarPagamento(id, dataPagamento));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Remover taxa")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
