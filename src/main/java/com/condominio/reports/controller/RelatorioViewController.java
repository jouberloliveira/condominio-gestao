package com.condominio.reports.controller;

import com.condominio.access.service.VisitanteService;
import com.condominio.administration.service.OcorrenciaService;
import com.condominio.administration.service.ReservaService;
import com.condominio.administration.service.UnidadeService;
import com.condominio.communication.service.ComunicadoService;
import com.condominio.residents.service.MoradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/relatorios")
@PreAuthorize("hasRole('ADMIN')")
public class RelatorioViewController {

    @Autowired private UnidadeService unidadeService;
    @Autowired private MoradorService moradorService;
    @Autowired private VisitanteService visitanteService;
    @Autowired private OcorrenciaService ocorrenciaService;
    @Autowired private ReservaService reservaService;
    @Autowired private ComunicadoService comunicadoService;

    @GetMapping
    public String index(Model m) {
        var unidades = unidadeService.findAll();
        var moradores = moradorService.findAll();
        var visitantes = visitanteService.findAll();
        var ocorrencias = ocorrenciaService.findAll();
        var reservas = reservaService.findAll();

        m.addAttribute("unidades", unidades);
        m.addAttribute("moradores", moradores);
        m.addAttribute("visitantes", visitantes);
        m.addAttribute("ocorrencias", ocorrencias);
        m.addAttribute("reservas", reservas);
        m.addAttribute("totalUnidades", unidades.size());
        m.addAttribute("totalMoradores", moradores.size());
        m.addAttribute("totalVisitantes", visitantes.size());
        m.addAttribute("totalOcorrencias", ocorrencias.size());
        m.addAttribute("totalReservas", reservas.size());
        m.addAttribute("totalComunicados", comunicadoService.countAtivos());
        m.addAttribute("currentPage", "relatorios");
        m.addAttribute("title", "Relatórios");
        return "relatorios/index";
    }
}
