package com.agustin.SistemaEstacionamiento.controller;

import org.springframework.web.bind.annotation.*;

import com.agustin.SistemaEstacionamiento.dto.AutomovilDTO;
import com.agustin.SistemaEstacionamiento.service.AutomovilService;

import java.util.List;

@RestController
@RequestMapping("/api/automoviles") // Base URL para los endpoints REST
public class AutomovilRestController {

    private final AutomovilService automovilService;

    public AutomovilRestController(AutomovilService automovilService) {
        this.automovilService = automovilService;
    }

    // Endpoint para obtener todos los automóviles como DTOs
    @GetMapping
    public List<AutomovilDTO> obtenerTodosLosAutomoviles() {
        return automovilService.obtenerTodos(); // Devuelve una lista de DTOs
    }

    // Endpoint para crear un nuevo automóvil (recibe y devuelve DTOs)
    @PostMapping
    public AutomovilDTO crearAutomovil(@RequestBody AutomovilDTO automovilDTO) {
        return automovilService.guardarAutomovil(automovilDTO); // Trabaja con DTOs
    }

    // Endpoint para eliminar un automóvil por su ID
    @DeleteMapping("/{id}")
    public String eliminarAutomovil(@PathVariable Long id) {
        automovilService.eliminarAutomovil(id);
        return "Automóvil eliminado correctamente.";
    }
}