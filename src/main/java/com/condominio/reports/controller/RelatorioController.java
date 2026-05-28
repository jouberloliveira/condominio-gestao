package com.condominio.reports.controller;

import com.condominio.reports.service.RelatorioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/relatorios")
@Tag(name = "Relatórios", description = "Geração de relatórios em PDF")
@PreAuthorize("hasRole('ADMIN')")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @GetMapping("/inadimplencia")
    @Operation(summary = "Relatório de inadimplência em PDF")
    public ResponseEntity<byte[]> inadimplencia() {
        byte[] pdf = relatorioService.relatorioInadimplencia();
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=inadimplencia.pdf")
            .contentType(MediaType.APPLICATION_PDF)
            .body(pdf);
    }

    @GetMapping("/financeiro")
    @Operation(summary = "Relatório financeiro por período em PDF")
    public ResponseEntity<byte[]> financeiro(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        byte[] pdf = relatorioService.relatorioFinanceiro(inicio, fim);
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=financeiro.pdf")
            .contentType(MediaType.APPLICATION_PDF)
            .body(pdf);
    }
}
