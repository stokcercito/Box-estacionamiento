package com.agustin.SistemaEstacionamiento.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.agustin.SistemaEstacionamiento.dto.AutomovilDTO;
import com.agustin.SistemaEstacionamiento.service.AutomovilService;

import java.util.List;

@Controller
@RequestMapping("/autos")
public class AutomovilViewController {

    private final AutomovilService automovilService;

    public AutomovilViewController(AutomovilService automovilService) {
        this.automovilService = automovilService;
    }

    // Método para mostrar la vista de todos los automóviles
    @GetMapping("/listado")
    public String mostrarAutomoviles(Model model) {
        List<AutomovilDTO> automoviles = automovilService.obtenerTodos();
        model.addAttribute("automoviles", automoviles);
        return "automoviles"; // Nombre del archivo HTML
    }
    
    

    // Método para mostrar el formulario de nuevo automóvil
    @GetMapping("/nuevo")
    public String mostrarFormularioDeNuevoAutomovil(Model model) {
        model.addAttribute("automovil", new AutomovilDTO());
        return "nuevo_automovil";
    }

    // Método para guardar un nuevo automóvil
    @PostMapping("/guardar_auto")
    public String guardarAutomovil(AutomovilDTO automovilDTO) {
        automovilService.guardarAutomovil(automovilDTO);
        return "redirect:/autos/listado"; // Redirige a la vista después de guardar
    }
    
}
