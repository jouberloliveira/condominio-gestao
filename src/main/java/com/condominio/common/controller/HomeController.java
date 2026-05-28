package com.condominio.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("title", "Dashboard");
        model.addAttribute("currentPage", "dashboard");
        return "dashboard";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/acesso-negado")
    public String accessDenied(Model model) {
        model.addAttribute("title", "Acesso Negado");
        return "acesso-negado";
    }
}
