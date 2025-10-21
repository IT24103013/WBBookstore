package com.ku_26.webbasedbookstore.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "logout", required = false) String logout,
                                Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password!");
        }
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully!");
        }
        return "login";
    }

    @GetMapping("/dashboard")
    public String showDashboard() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

        // Redirect based on role - using your actual role names
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals("ROLE_ADMIN")) {
                return "redirect:/system-admin/dashboard";
            } else if (authority.getAuthority().equals("ROLE_INVENTORY_MANAGER")) {
                return "redirect:/inventory/dashboard";
            } else if (authority.getAuthority().equals("ROLE_BOOKSTORE_MANAGER")) {
                return "redirect:/bookstore/dashboard";
            } else if (authority.getAuthority().equals("ROLE_MARKETING_MANAGER")) {
                return "redirect:/marketing/dashboard";
            } else if (authority.getAuthority().equals("ROLE_DELIVERY_STAFF")) {
                return "redirect:/delivery/dashboard";
            } else if (authority.getAuthority().equals("ROLE_CUSTOMER")) {
                return "redirect:/customer/dashboard";
            }
        }

        return "redirect:/login";
    }
}