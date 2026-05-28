package com.condominio.common.controller;

import com.condominio.access.service.VisitanteService;
import com.condominio.administration.service.OcorrenciaService;
import com.condominio.administration.service.ReservaService;
import com.condominio.administration.service.UnidadeService;
import com.condominio.residents.service.MoradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired private UnidadeService unidadeService;
    @Autowired private MoradorService moradorService;
    @Autowired private VisitanteService visitanteService;
    @Autowired private OcorrenciaService ocorrenciaService;
    @Autowired private ReservaService reservaService;

    @GetMapping("/")
    public String home() {
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("title", "Dashboard");
        model.addAttribute("currentPage", "dashboard");
        try {
            model.addAttribute("totalUnidades", unidadeService.findAll().size());
            model.addAttribute("totalMoradores", moradorService.findAll().size());
            model.addAttribute("totalVisitantes", visitanteService.findAll().size());
            model.addAttribute("totalOcorrencias", ocorrenciaService.findAll().size());
            model.addAttribute("totalReservas", reservaService.findAll().size());
        } catch (Exception ignored) {}
        return "dashboard";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/recuperar-senha")
    public String recuperarSenha() {
        return "recuperar-senha";
    }

    @GetMapping("/acesso-negado")
    public String accessDenied(Model model) {
        model.addAttribute("title", "Acesso Negado");
        return "acesso-negado";
    }
}
