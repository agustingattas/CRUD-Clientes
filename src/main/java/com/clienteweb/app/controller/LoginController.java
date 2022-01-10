/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clienteweb.app.controller;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

@Controller
public class LoginController {
    
    @GetMapping("/login")
    public String login(@RequestParam(value="error", required=false) String error,
            @RequestParam(value="logout", required=false) String logout,
            Model model, Principal principal, RedirectAttributes attribute){
        
        if (error!=null){
            model.addAttribute("error", "El Nombre de usuario o contrasena es incorrecto");
        }
        if (logout!=null){
            model.addAttribute("success", "Cerró sesión correctamente");
        }
        
        if (principal!=null){
            attribute.addFlashAttribute("warning", "ATENCION: Ud ya inicio sesión previamente");
            return "redirect:/";
        }
        
        return "login";
    }
    
    
}
