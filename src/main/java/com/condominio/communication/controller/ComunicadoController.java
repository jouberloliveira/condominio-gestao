package com.condominio.communication.controller;

import com.condominio.communication.enums.TipoComunicado;
import com.condominio.communication.model.Comunicado;
import com.condominio.communication.service.ComunicadoService;
import com.condominio.common.exception.BusinessException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/comunicados")
public class ComunicadoController {

    @Autowired
    private ComunicadoService service;

    @GetMapping
    public String list(Model m) {
        m.addAttribute("comunicados", service.findAtivos());
        m.addAttribute("currentPage", "comunicados");
        m.addAttribute("title", "Comunicados");
        return "comunicados/list";
    }

    @GetMapping("/new")
    public String newForm(Model m) {
        m.addAttribute("comunicado", new Comunicado());
        m.addAttribute("tipos", TipoComunicado.values());
        m.addAttribute("currentPage", "comunicados");
        m.addAttribute("title", "Novo Comunicado");
        return "comunicados/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model m) {
        m.addAttribute("comunicado", service.findById(id));
        m.addAttribute("tipos", TipoComunicado.values());
        m.addAttribute("currentPage", "comunicados");
        m.addAttribute("title", "Editar Comunicado");
        return "comunicados/form";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute Comunicado c, BindingResult r,
                       Authentication auth, Model m, RedirectAttributes ra) {
        if (r.hasErrors()) {
            m.addAttribute("tipos", TipoComunicado.values());
            m.addAttribute("currentPage", "comunicados");
            return "comunicados/form";
        }
        if (c.getId() == null) {
            c.setAutor(auth.getName());
        }
        try {
            service.save(c);
            ra.addFlashAttribute("success", "Comunicado publicado com sucesso!");
            return "redirect:/comunicados";
        } catch (BusinessException e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/comunicados/new";
        }
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        try {
            service.deleteById(id);
            ra.addFlashAttribute("success", "Comunicado removido!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Erro ao remover comunicado");
        }
        return "redirect:/comunicados";
    }
}
