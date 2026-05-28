package com.condominio.administration.controller;
import com.condominio.administration.model.Unidade;
import com.condominio.administration.service.UnidadeService;
import com.condominio.common.exception.BusinessException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller @RequestMapping("/unidades")
public class UnidadeController {
    @Autowired
    private UnidadeService service;
    @GetMapping public String list(Model m) { m.addAttribute("unidades", service.findAll()); m.addAttribute("currentPage","unidades"); m.addAttribute("title","Unidades"); return "unidades/list"; }
    @GetMapping("/new") public String newForm(Model m) { m.addAttribute("unidade", new Unidade()); m.addAttribute("currentPage","unidades"); m.addAttribute("title","Nova Unidade"); return "unidades/form"; }
    @GetMapping("/{id}/edit") public String editForm(@PathVariable Long id, Model m) { m.addAttribute("unidade", service.findById(id)); m.addAttribute("currentPage","unidades"); m.addAttribute("title","Editar Unidade"); return "unidades/form"; }
    @PostMapping
    public String save(@Valid @ModelAttribute Unidade u, BindingResult r, RedirectAttributes ra) {
        if (r.hasErrors()) return "unidades/form";
        try { service.save(u); ra.addFlashAttribute("success","Unidade salva!"); return "redirect:/unidades"; }
        catch (BusinessException e) { ra.addFlashAttribute("error",e.getMessage()); return "redirect:/unidades/new"; }
    }
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        try { service.deleteById(id); ra.addFlashAttribute("success","Unidade excluída!"); }
        catch (Exception e) { ra.addFlashAttribute("error","Erro ao excluir"); }
        return "redirect:/unidades";
    }
}
