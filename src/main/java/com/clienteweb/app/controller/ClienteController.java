package com.clienteweb.app.controller;

import com.clienteweb.app.entity.Ciudad;
import com.clienteweb.app.entity.Cliente;
import com.clienteweb.app.service.ICiudadService;
import com.clienteweb.app.service.IClienteService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cliente")
public class ClienteController {
    
    @Autowired
    private IClienteService clienteService;
    
    @Autowired
    private ICiudadService ciudadService;
    
    @Secured("ROLE_USER")
    @GetMapping("/lista")
    public String listarClientes(Model model){
        List<Cliente> listadoClientes = clienteService.listarTodos();
        model.addAttribute("titulo", "Lista de clientes");
        model.addAttribute("clientes", listadoClientes);
        return"listaClientes";
    }
    
    @Secured("ROLE_ADMIN")
    @GetMapping("/registrar")
    public String crear(Model model){
        
        Cliente cliente = new Cliente();
        List<Ciudad> listCiudades = ciudadService.listaCiudades();
        model.addAttribute("titulo", "Formulario: Nuevo Cliente");
        model.addAttribute("cliente", cliente);
        model.addAttribute("ciudades", listCiudades);
        
        return"registroCliente";
    }
    
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Cliente cliente, BindingResult result, Model model, RedirectAttributes attribute){
        
        List<Ciudad> listCiudades = ciudadService.listaCiudades();
        if (result.hasErrors()){
            
             
            model.addAttribute("titulo", "Formulario: Nuevo Cliente");
            model.addAttribute("cliente", cliente);
            model.addAttribute("ciudades", listCiudades);
            System.out.println("EXISTIERON ERRORES EN EL FORMULARIO");
            return "registroCliente";
        }
        
        clienteService.guardar(cliente);
        System.out.println("CLIENTE GUARDADO CON EXITO");
        attribute.addFlashAttribute("success", "CLIENTE GUARDADO CON EXITO");
        return "redirect:/cliente/lista";
    }
    
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/edit/{id}")
    public String editar(@PathVariable("id") Long idCliente, Model model, RedirectAttributes attribute){
        
        Cliente cliente = clienteService.buscarPorId(idCliente);
        List<Ciudad> listCiudades = ciudadService.listaCiudades();
        model.addAttribute("titulo", "Formulario: Editar Cliente");
        model.addAttribute("cliente", cliente);
        model.addAttribute("ciudades", listCiudades);
        
     
        return"registroCliente";
    }
    
    @Secured("ROLE_ADMIN")
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Long idCliente, RedirectAttributes attribute){
        
        clienteService.eliminar(idCliente);
        System.out.println("CLIENTE ELIMINADO CON EXITO");
        attribute.addFlashAttribute("warning", "CLIENTE ELIMINADO CON EXITO");
        return "redirect:/cliente/lista";
    }
    
}
