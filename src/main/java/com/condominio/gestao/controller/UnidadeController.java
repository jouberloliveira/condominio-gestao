package com.condominio.gestao.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.condominio.gestao.exception.BusinessException;
import com.condominio.gestao.model.Unidade;
import com.condominio.gestao.service.UnidadeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/unidades")
public class UnidadeController {

    @Autowired
    private UnidadeService service;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("unidades", service.findAll());
        return "unidades/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("unidade", new Unidade());
        return "unidades/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("unidade", service.findById(id));
        return "unidades/form";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute Unidade unidade, BindingResult result,
                      RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "unidades/form";
        }
        try {
            service.save(unidade);
            redirectAttributes.addFlashAttribute("success", "Unidade salva com sucesso!");
            return "redirect:/unidades";
        } catch (BusinessException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/unidades/new";
        }
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            service.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Unidade excluída com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao excluir unidade");
        }
        return "redirect:/unidades";
    }
}
