package com.condominio.residents.controller;
import com.condominio.administration.service.UnidadeService;
import com.condominio.common.exception.BusinessException;
import com.condominio.residents.model.Morador;
import com.condominio.residents.service.MoradorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller @RequestMapping("/moradores") @RequiredArgsConstructor
public class MoradorController {
    private final MoradorService service;
    private final UnidadeService unidadeService;
    @GetMapping public String list(Model m) { m.addAttribute("moradores", service.findAll()); m.addAttribute("currentPage","moradores"); m.addAttribute("title","Moradores"); return "moradores/list"; }
    @GetMapping("/new") public String newForm(Model m) { m.addAttribute("morador", new Morador()); m.addAttribute("unidades", unidadeService.findAll()); m.addAttribute("currentPage","moradores"); m.addAttribute("title","Novo Morador"); return "moradores/form"; }
    @GetMapping("/{id}/edit") public String editForm(@PathVariable Long id, Model m) { m.addAttribute("morador", service.findById(id)); m.addAttribute("unidades", unidadeService.findAll()); m.addAttribute("currentPage","moradores"); m.addAttribute("title","Editar Morador"); return "moradores/form"; }
    @PostMapping
    public String save(@Valid @ModelAttribute Morador mo, BindingResult r, Model m, RedirectAttributes ra) {
        if (r.hasErrors()) { m.addAttribute("unidades", unidadeService.findAll()); return "moradores/form"; }
        try { service.save(mo); ra.addFlashAttribute("success","Morador salvo!"); return "redirect:/moradores"; }
        catch (BusinessException e) { ra.addFlashAttribute("error",e.getMessage()); return "redirect:/moradores/new"; }
    }
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        try { service.deleteById(id); ra.addFlashAttribute("success","Morador excluído!"); }
        catch (Exception e) { ra.addFlashAttribute("error","Erro ao excluir"); }
        return "redirect:/moradores";
    }
}
