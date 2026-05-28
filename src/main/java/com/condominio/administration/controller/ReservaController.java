package com.condominio.administration.controller;

import com.condominio.administration.enums.AreaReserva;
import com.condominio.administration.enums.StatusReserva;
import com.condominio.administration.model.Reserva;
import com.condominio.administration.service.ReservaService;
import com.condominio.administration.service.UnidadeService;
import com.condominio.common.exception.BusinessException;
import com.condominio.residents.service.MoradorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired private ReservaService service;
    @Autowired private UnidadeService unidadeService;
    @Autowired private MoradorService moradorService;

    private void addEnums(Model m) {
        m.addAttribute("areas", AreaReserva.values());
        m.addAttribute("statusList", StatusReserva.values());
    }

    @GetMapping
    public String list(Model m) {
        m.addAttribute("reservas", service.findAll());
        m.addAttribute("currentPage", "reservas");
        m.addAttribute("title", "Reservas");
        return "reservas/list";
    }

    @GetMapping("/new")
    public String newForm(Model m) {
        m.addAttribute("reserva", new Reserva());
        m.addAttribute("unidades", unidadeService.findAll());
        m.addAttribute("moradores", moradorService.findAll());
        addEnums(m);
        m.addAttribute("currentPage", "reservas");
        m.addAttribute("title", "Nova Reserva");
        return "reservas/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model m) {
        m.addAttribute("reserva", service.findById(id));
        m.addAttribute("unidades", unidadeService.findAll());
        m.addAttribute("moradores", moradorService.findAll());
        addEnums(m);
        m.addAttribute("currentPage", "reservas");
        m.addAttribute("title", "Editar Reserva");
        return "reservas/form";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute Reserva r, BindingResult br,
                       Model m, RedirectAttributes ra) {
        if (br.hasErrors()) {
            m.addAttribute("unidades", unidadeService.findAll());
            m.addAttribute("moradores", moradorService.findAll());
            addEnums(m);
            return "reservas/form";
        }
        try {
            service.save(r);
            ra.addFlashAttribute("success", "Reserva salva com sucesso!");
            return "redirect:/reservas";
        } catch (BusinessException e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/reservas/new";
        }
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        try {
            service.deleteById(id);
            ra.addFlashAttribute("success", "Reserva excluída!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Erro ao excluir reserva");
        }
        return "redirect:/reservas";
    }
}
