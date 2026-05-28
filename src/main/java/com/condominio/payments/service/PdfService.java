package com.condominio.payments.service;

import com.condominio.payments.model.ReceitaDespesa;
import com.condominio.payments.model.TaxaCondominial;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PdfService {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public byte[] gerarRelatorioInadimplencia(List<TaxaCondominial> taxas) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document doc = new Document(PageSize.A4);
            PdfWriter.getInstance(doc, out);
            doc.open();

            Font titulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Font cabecalho = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
            Font corpo = FontFactory.getFont(FontFactory.HELVETICA, 9);

            doc.add(new Paragraph("Relatório de Inadimplência", titulo));
            doc.add(new Paragraph("Gerado em: " + java.time.LocalDate.now().format(FMT), corpo));
            doc.add(Chunk.NEWLINE);

            Table table = new Table(5);
            table.setWidth(100);
            table.addCell(new Cell(new Phrase("Unidade", cabecalho)));
            table.addCell(new Cell(new Phrase("Competência", cabecalho)));
            table.addCell(new Cell(new Phrase("Vencimento", cabecalho)));
            table.addCell(new Cell(new Phrase("Valor", cabecalho)));
            table.addCell(new Cell(new Phrase("Status", cabecalho)));

            for (TaxaCondominial t : taxas) {
                table.addCell(new Cell(new Phrase(t.getUnidade().getIdentificacao(), corpo)));
                table.addCell(new Cell(new Phrase(t.getCompetenciaMes() + "/" + t.getCompetenciaAno(), corpo)));
                table.addCell(new Cell(new Phrase(t.getVencimento().format(FMT), corpo)));
                table.addCell(new Cell(new Phrase("R$ " + t.getValor(), corpo)));
                table.addCell(new Cell(new Phrase(t.getStatus().name(), corpo)));
            }

            doc.add(table);
            doc.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF de inadimplência", e);
        }
    }

    public byte[] gerarRelatorioFinanceiro(List<ReceitaDespesa> lancamentos, String titulo) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document doc = new Document(PageSize.A4);
            PdfWriter.getInstance(doc, out);
            doc.open();

            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Font cabecalho = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
            Font corpo = FontFactory.getFont(FontFactory.HELVETICA, 9);

            doc.add(new Paragraph(titulo, fontTitulo));
            doc.add(new Paragraph("Gerado em: " + java.time.LocalDate.now().format(FMT), corpo));
            doc.add(Chunk.NEWLINE);

            Table table = new Table(5);
            table.setWidth(100);
            table.addCell(new Cell(new Phrase("Data", cabecalho)));
            table.addCell(new Cell(new Phrase("Tipo", cabecalho)));
            table.addCell(new Cell(new Phrase("Categoria", cabecalho)));
            table.addCell(new Cell(new Phrase("Descrição", cabecalho)));
            table.addCell(new Cell(new Phrase("Valor", cabecalho)));

            for (ReceitaDespesa r : lancamentos) {
                table.addCell(new Cell(new Phrase(r.getData().format(FMT), corpo)));
                table.addCell(new Cell(new Phrase(r.getTipo().name(), corpo)));
                table.addCell(new Cell(new Phrase(r.getCategoria().name(), corpo)));
                table.addCell(new Cell(new Phrase(r.getDescricao(), corpo)));
                table.addCell(new Cell(new Phrase("R$ " + r.getValor(), corpo)));
            }

            doc.add(table);
            doc.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF financeiro", e);
        }
    }
}
