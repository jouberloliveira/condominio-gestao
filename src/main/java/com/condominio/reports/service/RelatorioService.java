package com.condominio.reports.service;

import com.condominio.payments.model.ReceitaDespesa;
import com.condominio.payments.model.TaxaCondominial;
import com.condominio.payments.service.PdfService;
import com.condominio.payments.service.ReceitaDespesaService;
import com.condominio.payments.service.TaxaCondominialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class RelatorioService {

    @Autowired
    private TaxaCondominialService taxaService;

    @Autowired
    private ReceitaDespesaService lancamentoService;

    @Autowired
    private PdfService pdfService;

    public byte[] relatorioInadimplencia() {
        List<TaxaCondominial> inadimplentes = taxaService.findInadimplentes();
        return pdfService.gerarRelatorioInadimplencia(inadimplentes);
    }

    public byte[] relatorioFinanceiro(LocalDate inicio, LocalDate fim) {
        List<ReceitaDespesa> lancamentos = lancamentoService.findByPeriodo(inicio, fim);
        return pdfService.gerarRelatorioFinanceiro(lancamentos, "Relatório Financeiro");
    }
}
