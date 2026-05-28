package com.condominio.gestao.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.condominio.gestao.exception.BusinessException;
import com.condominio.gestao.model.Visitante;
import com.condominio.gestao.service.VisitanteService;
import com.condominio.gestao.service.UnidadeService;
import com.condominio.gestao.service.MoradorService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/visitantes")
public class VisitanteController {

    @Autowired
    private VisitanteService service;
    @Autowired
    private UnidadeService unidadeService;
    @Autowired
    private MoradorService moradorService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("visitantes", service.findAll());
        return "visitantes/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("visitante", new Visitante());
        model.addAttribute("unidades", unidadeService.findAll());
        model.addAttribute("moradores", moradorService.findAll());
        return "visitantes/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("visitante", service.findById(id));
        model.addAttribute("unidades", unidadeService.findAll());
        model.addAttribute("moradores", moradorService.findAll());
        return "visitantes/form";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute Visitante visitante, BindingResult result,
                      Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("unidades", unidadeService.findAll());
            model.addAttribute("moradores", moradorService.findAll());
            return "visitantes/form";
        }
        try {
            service.save(visitante);
            redirectAttributes.addFlashAttribute("success", "Visitante salvo com sucesso!");
            return "redirect:/visitantes";
        } catch (BusinessException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/visitantes/new";
        }
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            service.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Visitante excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao excluir visitante");
        }
        return "redirect:/visitantes";
    }
}
