package com.condominio.administration.controller;
import com.condominio.administration.model.Reserva;
import com.condominio.administration.service.ReservaService;
import com.condominio.administration.service.UnidadeService;
import com.condominio.common.exception.BusinessException;
import com.condominio.residents.service.MoradorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller @RequestMapping("/reservas") @RequiredArgsConstructor
public class ReservaController {
    private final ReservaService service;
    private final UnidadeService unidadeService;
    private final MoradorService moradorService;
    @GetMapping public String list(Model m) { m.addAttribute("reservas", service.findAll()); return "reservas/list"; }
    @GetMapping("/new") public String newForm(Model m) { m.addAttribute("reserva", new Reserva()); m.addAttribute("unidades", unidadeService.findAll()); m.addAttribute("moradores", moradorService.findAll()); return "reservas/form"; }
    @GetMapping("/{id}/edit") public String editForm(@PathVariable Long id, Model m) { m.addAttribute("reserva", service.findById(id)); m.addAttribute("unidades", unidadeService.findAll()); m.addAttribute("moradores", moradorService.findAll()); return "reservas/form"; }
    @PostMapping
    public String save(@Valid @ModelAttribute Reserva r, BindingResult br, Model m, RedirectAttributes ra) {
        if (br.hasErrors()) { m.addAttribute("unidades", unidadeService.findAll()); m.addAttribute("moradores", moradorService.findAll()); return "reservas/form"; }
        try { service.save(r); ra.addFlashAttribute("success","Reserva salva!"); return "redirect:/reservas"; }
        catch (BusinessException e) { ra.addFlashAttribute("error",e.getMessage()); return "redirect:/reservas/new"; }
    }
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        try { service.deleteById(id); ra.addFlashAttribute("success","Reserva excluída!"); }
        catch (Exception e) { ra.addFlashAttribute("error","Erro ao excluir"); }
        return "redirect:/reservas";
    }
}
