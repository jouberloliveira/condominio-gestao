package com.condominio.administration.controller;

import com.condominio.administration.enums.PrioridadeOcorrencia;
import com.condominio.administration.enums.StatusOcorrencia;
import com.condominio.administration.enums.TipoOcorrencia;
import com.condominio.administration.model.Ocorrencia;
import com.condominio.administration.service.OcorrenciaService;
import com.condominio.administration.service.UnidadeService;
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
@RequestMapping("/ocorrencias")
public class OcorrenciaController {

    @Autowired private OcorrenciaService service;
    @Autowired private UnidadeService unidadeService;
    @Autowired private MoradorService moradorService;

    private void addEnums(Model m) {
        m.addAttribute("tipos", TipoOcorrencia.values());
        m.addAttribute("prioridades", PrioridadeOcorrencia.values());
        m.addAttribute("statusList", StatusOcorrencia.values());
    }

    @GetMapping
    public String list(Model m) {
        m.addAttribute("ocorrencias", service.findAll());
        m.addAttribute("currentPage", "ocorrencias");
        m.addAttribute("title", "Ocorrências");
        return "ocorrencias/list";
    }

    @GetMapping("/new")
    public String newForm(Model m) {
        m.addAttribute("ocorrencia", new Ocorrencia());
        m.addAttribute("unidades", unidadeService.findAll());
        m.addAttribute("moradores", moradorService.findAll());
        addEnums(m);
        m.addAttribute("currentPage", "ocorrencias");
        m.addAttribute("title", "Nova Ocorrência");
        return "ocorrencias/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model m) {
        m.addAttribute("ocorrencia", service.findById(id));
        m.addAttribute("unidades", unidadeService.findAll());
        m.addAttribute("moradores", moradorService.findAll());
        addEnums(m);
        m.addAttribute("currentPage", "ocorrencias");
        m.addAttribute("title", "Editar Ocorrência");
        return "ocorrencias/form";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute Ocorrencia o, BindingResult r,
                       Model m, RedirectAttributes ra) {
        if (r.hasErrors()) {
            m.addAttribute("unidades", unidadeService.findAll());
            m.addAttribute("moradores", moradorService.findAll());
            addEnums(m);
            return "ocorrencias/form";
        }
        try {
            service.save(o);
            ra.addFlashAttribute("success", "Ocorrência salva com sucesso!");
            return "redirect:/ocorrencias";
        } catch (BusinessException e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/ocorrencias/new";
        }
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        try {
            service.deleteById(id);
            ra.addFlashAttribute("success", "Ocorrência excluída!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Erro ao excluir ocorrência");
        }
        return "redirect:/ocorrencias";
    }
}
