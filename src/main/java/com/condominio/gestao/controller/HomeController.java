package com.condominio.gestao.controller;

import com.condominio.gestao.enums.SimNao;
import com.condominio.gestao.enums.StatusOcorrencia;
import com.condominio.gestao.repository.MoradorRepository;
import com.condominio.gestao.repository.OcorrenciaRepository;
import com.condominio.gestao.repository.ReservaRepository;
import com.condominio.gestao.repository.UnidadeRepository;
import com.condominio.gestao.repository.VisitanteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UnidadeRepository unidadeRepository;
    private final MoradorRepository moradorRepository;
    private final OcorrenciaRepository ocorrenciaRepository;
    private final VisitanteRepository visitanteRepository;
    private final ReservaRepository reservaRepository;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("activePage", "dashboard");
        model.addAttribute("title", "Dashboard");

        model.addAttribute("totalUnidades", unidadeRepository.count());
        model.addAttribute("totalMoradores", moradorRepository.count());
        model.addAttribute("ocorrenciasAbertas",
                ocorrenciaRepository.findAll().stream()
                        .filter(o -> o.getStatus() == StatusOcorrencia.ABERTA
                                || o.getStatus() == StatusOcorrencia.EM_ANDAMENTO)
                        .count());
        model.addAttribute("visitantesAtivos",
                visitanteRepository.findAll().stream()
                        .filter(v -> v.getAtivo() == SimNao.SIM)
                        .count());

        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);
        model.addAttribute("reservasHoje",
                reservaRepository.findAll().stream()
                        .filter(r -> r.getInicio() != null
                                && !r.getInicio().isBefore(startOfDay)
                                && r.getInicio().isBefore(endOfDay))
                        .toList());

        return "index";
    }
}
