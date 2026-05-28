package com.condominio.access.controller;

import com.condominio.access.enums.TipoVisitante;
import com.condominio.access.model.Visitante;
import com.condominio.access.service.VisitanteService;
import com.condominio.administration.service.UnidadeService;
import com.condominio.common.enums.SimNao;
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
@RequestMapping("/visitantes")
public class VisitanteController {

    @Autowired private VisitanteService service;
    @Autowired private UnidadeService unidadeService;
    @Autowired private MoradorService moradorService;

    private void addEnums(Model m) {
        m.addAttribute("tiposVisitante", TipoVisitante.values());
        m.addAttribute("simNaoList", SimNao.values());
    }

    @GetMapping
    public String list(Model m) {
        m.addAttribute("visitantes", service.findAll());
        m.addAttribute("currentPage", "visitantes");
        m.addAttribute("title", "Visitantes");
        return "visitantes/list";
    }

    @GetMapping("/new")
    public String newForm(Model m) {
        m.addAttribute("visitante", new Visitante());
        m.addAttribute("unidades", unidadeService.findAll());
        m.addAttribute("moradores", moradorService.findAll());
        addEnums(m);
        m.addAttribute("currentPage", "visitantes");
        m.addAttribute("title", "Novo Visitante");
        return "visitantes/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model m) {
        m.addAttribute("visitante", service.findById(id));
        m.addAttribute("unidades", unidadeService.findAll());
        m.addAttribute("moradores", moradorService.findAll());
        addEnums(m);
        m.addAttribute("currentPage", "visitantes");
        m.addAttribute("title", "Editar Visitante");
        return "visitantes/form";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute Visitante v, BindingResult r,
                       Model m, RedirectAttributes ra) {
        if (r.hasErrors()) {
            m.addAttribute("unidades", unidadeService.findAll());
            m.addAttribute("moradores", moradorService.findAll());
            addEnums(m);
            return "visitantes/form";
        }
        try {
            service.save(v);
            ra.addFlashAttribute("success", "Visitante salvo com sucesso!");
            return "redirect:/visitantes";
        } catch (BusinessException e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/visitantes/new";
        }
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        try {
            service.deleteById(id);
            ra.addFlashAttribute("success", "Visitante excluído!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Erro ao excluir visitante");
        }
        return "redirect:/visitantes";
    }
}
