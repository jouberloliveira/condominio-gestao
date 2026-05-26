package com.condominio.gestao.controller;

import com.condominio.gestao.exception.BusinessException;
import com.condominio.gestao.model.Morador;
import com.condominio.gestao.service.MoradorService;
import com.condominio.gestao.service.UnidadeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/moradores")
@RequiredArgsConstructor
public class MoradorController {

    private final MoradorService service;
    private final UnidadeService unidadeService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("moradores", service.findAll());
        return "moradores/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("morador", new Morador());
        model.addAttribute("unidades", unidadeService.findAll());
        return "moradores/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("morador", service.findById(id));
        model.addAttribute("unidades", unidadeService.findAll());
        return "moradores/form";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute Morador morador, BindingResult result,
                      Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("unidades", unidadeService.findAll());
            return "moradores/form";
        }
        try {
            service.save(morador);
            redirectAttributes.addFlashAttribute("success", "Morador salvo com sucesso!");
            return "redirect:/moradores";
        } catch (BusinessException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/moradores/new";
        }
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            service.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Morador excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao excluir morador");
        }
        return "redirect:/moradores";
    }
}
