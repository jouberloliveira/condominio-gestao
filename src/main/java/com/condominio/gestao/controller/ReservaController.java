package com.condominio.gestao.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.condominio.gestao.exception.BusinessException;
import com.condominio.gestao.model.Reserva;
import com.condominio.gestao.service.ReservaService;
import com.condominio.gestao.service.UnidadeService;
import com.condominio.gestao.service.MoradorService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService service;
    @Autowired
    private UnidadeService unidadeService;
    @Autowired
    private MoradorService moradorService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("reservas", service.findAll());
        return "reservas/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("reserva", new Reserva());
        model.addAttribute("unidades", unidadeService.findAll());
        model.addAttribute("moradores", moradorService.findAll());
        return "reservas/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("reserva", service.findById(id));
        model.addAttribute("unidades", unidadeService.findAll());
        model.addAttribute("moradores", moradorService.findAll());
        return "reservas/form";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute Reserva reserva, BindingResult result,
                      Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("unidades", unidadeService.findAll());
            model.addAttribute("moradores", moradorService.findAll());
            return "reservas/form";
        }
        try {
            service.save(reserva);
            redirectAttributes.addFlashAttribute("success", "Reserva salva com sucesso!");
            return "redirect:/reservas";
        } catch (BusinessException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/reservas/new";
        }
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            service.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Reserva excluída com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao excluir reserva");
        }
        return "redirect:/reservas";
    }
}
