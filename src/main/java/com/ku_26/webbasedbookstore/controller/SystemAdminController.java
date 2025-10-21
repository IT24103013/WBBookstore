package com.ku_26.webbasedbookstore.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/system-admin")
public class SystemAdminController {
    @GetMapping("/dashboard")
    public String systemAdminDashboard(Model model, Authentication authentication) {
        model.addAttribute("username", authentication.getName());
        model.addAttribute("role", "ADMIN");
        return "system-admin/dashboard";
    }
}