package com.agustin.SistemaEstacionamiento.controller;

import org.springframework.web.bind.annotation.*;

import com.agustin.SistemaEstacionamiento.dto.BoxDeEstacionamientoDTO;
import com.agustin.SistemaEstacionamiento.service.BoxDeEstacionamientoService;

import java.util.List;

@RestController
@RequestMapping("/api/boxes") // Define la URL base para los endpoints REST
public class BoxDeEstacionamientoRestController {

    private final BoxDeEstacionamientoService boxService;

    public BoxDeEstacionamientoRestController(BoxDeEstacionamientoService boxService) {
        this.boxService = boxService;
    }

    // Endpoint para obtener todos los boxes de estacionamiento como DTOs
    @GetMapping
    public List<BoxDeEstacionamientoDTO> obtenerTodosLosBoxes() {
        return boxService.obtenerTodos(); // Devuelve una lista de DTOs
    }

    // Endpoint para obtener un box específico por ID como DTO
    @GetMapping("/{id}")
    public BoxDeEstacionamientoDTO obtenerBoxPorId(@PathVariable Long id) {
        return boxService.obtenerPorIdDTO(id); // Devuelve un DTO
    }

    // Endpoint para crear un nuevo box de estacionamiento (recibe y devuelve DTOs)
    @PostMapping
    public BoxDeEstacionamientoDTO crearBox(@RequestBody BoxDeEstacionamientoDTO boxDTO) {
        return boxService.guardarBox(boxDTO); // Convierte DTO a entidad y luego devuelve el DTO
    }

    // Endpoint para actualizar un box de estacionamiento existente (recibe y devuelve DTOs)
    @PutMapping("/{id}")
    public BoxDeEstacionamientoDTO actualizarBox(@PathVariable Long id, @RequestBody BoxDeEstacionamientoDTO boxDTO) {
        return boxService.actualizarBox(id, boxDTO); // Actualiza y devuelve el box como DTO
    }

    // Endpoint para eliminar un box de estacionamiento por ID
    @DeleteMapping("/{id}")
    public String eliminarBox(@PathVariable Long id) {
        boxService.eliminarBox(id); // Llama al servicio para eliminar
        return "Box de estacionamiento eliminado correctamente.";
    }
}
