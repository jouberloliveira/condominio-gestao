package com.condominio.gestao.controller;

import com.condominio.gestao.exception.BusinessException;
import com.condominio.gestao.model.Ocorrencia;
import com.condominio.gestao.service.OcorrenciaService;
import com.condominio.gestao.service.UnidadeService;
import com.condominio.gestao.service.MoradorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ocorrencias")
@RequiredArgsConstructor
public class OcorrenciaController {

    private final OcorrenciaService service;
    private final UnidadeService unidadeService;
    private final MoradorService moradorService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("ocorrencias", service.findAll());
        return "ocorrencias/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("ocorrencia", new Ocorrencia());
        model.addAttribute("unidades", unidadeService.findAll());
        model.addAttribute("moradores", moradorService.findAll());
        return "ocorrencias/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("ocorrencia", service.findById(id));
        model.addAttribute("unidades", unidadeService.findAll());
        model.addAttribute("moradores", moradorService.findAll());
        return "ocorrencias/form";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute Ocorrencia ocorrencia, BindingResult result,
                      Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("unidades", unidadeService.findAll());
            model.addAttribute("moradores", moradorService.findAll());
            return "ocorrencias/form";
        }
        try {
            service.save(ocorrencia);
            redirectAttributes.addFlashAttribute("success", "Ocorrência salva com sucesso!");
            return "redirect:/ocorrencias";
        } catch (BusinessException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/ocorrencias/new";
        }
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            service.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Ocorrência excluída com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao excluir ocorrência");
        }
        return "redirect:/ocorrencias";
    }
}
