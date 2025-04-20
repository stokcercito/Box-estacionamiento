package com.agustin.SistemaEstacionamiento.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.agustin.SistemaEstacionamiento.dto.AutomovilDTO;
import com.agustin.SistemaEstacionamiento.dto.BoxDeEstacionamientoDTO;
import com.agustin.SistemaEstacionamiento.dto.ProcesoBoxAutomovilDTO;
import com.agustin.SistemaEstacionamiento.mapper.ProcesoBoxAutomovilMapper;
import com.agustin.SistemaEstacionamiento.model.Automovil;
import com.agustin.SistemaEstacionamiento.model.BoxDeEstacionamiento;
import com.agustin.SistemaEstacionamiento.model.ProcesoBoxAutomovil;
import com.agustin.SistemaEstacionamiento.service.AutomovilService;
import com.agustin.SistemaEstacionamiento.service.BoxDeEstacionamientoService;
import com.agustin.SistemaEstacionamiento.service.ProcesoBoxAutomovilService;

import java.util.List;

@Controller
@RequestMapping("/boxes")
public class BoxDeEstacionamientoViewController {

	 private final BoxDeEstacionamientoService boxService;
	 private final AutomovilService automovilService; // Declarar AutomovilService
	 private final ProcesoBoxAutomovilService procesoBoxAutomovilService; // Declarar el servicio
	 private final ProcesoBoxAutomovilMapper procesoBoxAutomovilMapper;
	 
	 public BoxDeEstacionamientoViewController(BoxDeEstacionamientoService boxService, AutomovilService automovilService, ProcesoBoxAutomovilService procesoBoxAutomovilService, ProcesoBoxAutomovilMapper procesoBoxAutomovilMapper) {
	        this.boxService = boxService;
	        this.automovilService = automovilService; // Inyección de AutomovilService
	        this.procesoBoxAutomovilService = procesoBoxAutomovilService; // Inicializar el servicio
	        this.procesoBoxAutomovilMapper = procesoBoxAutomovilMapper; // Inicializar el mapeador
	    }

    // Método para mostrar la vista de todos los boxes de estacionamiento
    @GetMapping("/listado")
    public String mostrarBoxes(Model model) {
        List<BoxDeEstacionamientoDTO> boxes = boxService.obtenerTodos();
        model.addAttribute("boxes", boxes);
        return "boxes"; // Nombre del archivo HTML
    }

    // Método para mostrar el formulario de nuevo box de estacionamiento
    @GetMapping("/nuevo")
    public String mostrarFormularioDeNuevoBox(Model model) {
        model.addAttribute("box", new BoxDeEstacionamientoDTO());
        return "nuevo_box";
    }

   // @GetMapping("/seleccionar_auto/{id}")
   // public String seleccionarAutomovil(@PathVariable("id") Long id, Model model) {
        // Obtener detalles del box por ID
   //     BoxDeEstacionamientoDTO box = boxService.obtenerPorIdDTO(id);
  //      if (box == null) {
   //         throw new IllegalArgumentException("El box con el ID especificado no existe.");
   //     }

        // Obtener la lista de automóviles disponibles
    //    List<AutomovilDTO> automoviles = boxService.obtenerAutomoviles();

        // Pasar datos al modelo
   //     model.addAttribute("box", box);
    //    model.addAttribute("boxId", id);
   //     model.addAttribute("automoviles", automoviles);

     //   return "seleccionar_automovil"; // Carga la vista correspondiente
   // }


   
    
    // Método para guardar un nuevo box de estacionamiento
       
    @PostMapping("/guardar_box")
    public String guardarBox(BoxDeEstacionamientoDTO boxDTO) {
        boxService.guardarBox(boxDTO);
        return "redirect:/boxes/listado"; // Redirige a la vista después de guardar
    }
}
