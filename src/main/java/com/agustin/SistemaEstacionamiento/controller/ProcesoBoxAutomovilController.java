package com.agustin.SistemaEstacionamiento.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.agustin.SistemaEstacionamiento.dto.AutomovilDTO;
import com.agustin.SistemaEstacionamiento.dto.BoxDeEstacionamientoDTO;
import com.agustin.SistemaEstacionamiento.dto.ProcesoBoxAutomovilDTO;
import com.agustin.SistemaEstacionamiento.mapper.ProcesoBoxAutomovilMapper;
import com.agustin.SistemaEstacionamiento.model.Automovil;
import com.agustin.SistemaEstacionamiento.model.Disponibilidad;
import com.agustin.SistemaEstacionamiento.service.AutomovilService;
import com.agustin.SistemaEstacionamiento.service.BoxDeEstacionamientoService;
import com.agustin.SistemaEstacionamiento.service.ProcesoBoxAutomovilService;

import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/procesos")
@SessionAttributes({"procesoSeleccionado", "boxn", "auto"})

public class ProcesoBoxAutomovilController {

	private final ProcesoBoxAutomovilService servicio;
    private final BoxDeEstacionamientoService boxService;
	private final AutomovilService automovilService; // Declarar AutomovilService
	private final ProcesoBoxAutomovilMapper procesoBoxAutomovilMapper;
	
	
    public ProcesoBoxAutomovilController(ProcesoBoxAutomovilService servicio, BoxDeEstacionamientoService boxService, AutomovilService automovilService, ProcesoBoxAutomovilMapper procesoBoxAutomovilMapper) {
        this.servicio = servicio;
        this.boxService = boxService;
        this.automovilService = automovilService;
        this.procesoBoxAutomovilMapper = procesoBoxAutomovilMapper;
    }
    
    @ModelAttribute("boxn")
    public BoxDeEstacionamientoDTO iniciarBox() {
        // Inicializamos un nuevo objeto para usarlo en el formulario
        return new BoxDeEstacionamientoDTO();
    }
    
    @ModelAttribute("auto")
    public AutomovilDTO iniciarAuto() {
        // Inicializamos un nuevo objeto para usarlo en el formulario
        return new AutomovilDTO();
    }

    
   
 // Inicializar el atributo de sesión si no existe
   
    @ModelAttribute("procesoSeleccionado")
    public ProcesoBoxAutomovilDTO iniciarProcesoSeleccionado() {
        return new ProcesoBoxAutomovilDTO(); // Inicializamos un nuevo objeto vacío
    }

    // Mostrar todos los procesos como DTOs
 
    @GetMapping
    public String mostrarProcesos(Model model) {
        return listarProcesos(model);
    }

    @GetMapping("/listado")
    public String listarProcesos(Model model) {
        List<ProcesoBoxAutomovilDTO> procesos = servicio.obtenerTodos();
        if (procesos == null || procesos.isEmpty()) {
            model.addAttribute("mensaje", "No hay procesos disponibles.");
        } else {
            model.addAttribute("procesos", procesos);
        }
     // Obtiene todos los Boxes
        List<BoxDeEstacionamientoDTO> boxes = boxService.obtenerTodos(); // Método en tu servicio
        if (boxes == null || boxes.isEmpty()) {
            model.addAttribute("mensajeBoxes", "No hay Boxes registrados.");
        }
        
        model.addAttribute("procesos", procesos);

        List<AutomovilDTO> automoviles = automovilService.obtenerTodos();
        List<AutomovilDTO> automovilesFiltradosT = automovilService.obtenerAutomovilesPorEstadoTrue();
        List<AutomovilDTO> automovilesFiltradosF = automovilService.obtenerAutomovilesPorEstadoFalse();

        model.addAttribute("automoviles", automoviles);
        model.addAttribute("automovilesFiltradosT", automovilesFiltradosT);
        model.addAttribute("automovilesFiltradosF", automovilesFiltradosF);

        model.addAttribute("boxn", new BoxDeEstacionamientoDTO());
        model.addAttribute("auto", new AutomovilDTO());

        return "proceso";
    }
    
     
    // Eliminar un proceso por ID
    @GetMapping("/eliminar/{id}")
    public String eliminarProceso(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        ProcesoBoxAutomovilDTO proceso = servicio.obtenerPorId(id);
        if (proceso != null) {
            BoxDeEstacionamientoDTO box = boxService.obtenerPorIdDTO(proceso.getBoxId());
            servicio.eliminarProceso(id);
            boxService.eliminarBox(box.getId());
            redirectAttributes.addFlashAttribute("mensaje", "Proceso eliminado exitosamente.");
        } else {
            redirectAttributes.addFlashAttribute("error", "El proceso especificado no existe.");
        }
        return "redirect:/procesos/listado";
    }
    
   
    
    // Actualizar un proceso por ID
    @GetMapping("/actualizar/{id}")
    public String actualizarProceso(@PathVariable Long id, Model model) {
    	ProcesoBoxAutomovilDTO proceso = servicio.obtenerPorId(id);
    	if (proceso != null) {
            // Actualizar el estado del box a "libre"
            BoxDeEstacionamientoDTO box = boxService.obtenerPorIdDTO(proceso.getBoxId());
            box.setDisponibilidad(Disponibilidad.libre);
            boxService.guardarBox(box);
         // Actualizar el estado del Automovil a "true"
            AutomovilDTO auto = automovilService.obtenerPorId(proceso.getAutomovilId());
            auto.setEstado(true);
            automovilService.guardarAutomovil(auto);
            AutomovilDTO autoFicticio = automovilService.obtenerPorId(1L);
         // Actualizar campos del proceso
            proceso.setBoxId(box.getId());
            proceso.setAutomovilId(autoFicticio.getId());
            proceso.setHoras_de_uso(null);
            proceso.setFecha_hora(null);
            proceso.setCosto_total(0.0);
         // Guardando Proceso Actualizado   
            servicio.guardarProceso(proceso);
            model.addAttribute("mensaje", "Proceso Actualizado exitosamente.");
    	} else {
            model.addAttribute("error", "El proceso especificado no existe.");
        }
        return "redirect:/procesos/listado"; // Redirige a la lista después de eliminar
    }
 
     
   // Método para mostrar el formulario de nuevo automóvil
   @GetMapping("/nuevo_auto")
   public String mostrarFormularioDeNuevoAutomovil(Model model) {
       model.addAttribute("automovil", new AutomovilDTO());
       return "nuevo_automovil";
   }
   
   @PostMapping("/eliminar_auto")
   public String eliminarAuto(@RequestParam Long automovilId, RedirectAttributes redirectAttributes) {
       AutomovilDTO autoEliminar = automovilService.obtenerPorId(automovilId);
       if (autoEliminar != null) {
           automovilService.eliminarAutomovil(autoEliminar.getId());
           redirectAttributes.addFlashAttribute("mensaje", "Auto eliminado exitosamente.");
       } else {
           redirectAttributes.addFlashAttribute("error", "El Auto especificado no existe.");
       }
       return "redirect:/procesos/listado";
   }

   
  
   @PostMapping("/guardar_box")
   public String guardarBox(@Valid @ModelAttribute("boxn") BoxDeEstacionamientoDTO boxDTO,
           BindingResult result,
           RedirectAttributes redirectAttributes) {
       // Guardar el nuevo Box en la base de datos
	   
	   if (result.hasErrors()) {
	        redirectAttributes.addFlashAttribute("error", "Error al guardar el Box. Verifique los datos ingresados.");
	        return "redirect:/procesos/listado"; // Mantiene la vista de listado
	    }
       BoxDeEstacionamientoDTO boxGuardado = boxService.guardarBox(boxDTO); // boxGuardado ahora tiene su ID

       // Crear un nuevo proceso asociado al Box
       AutomovilDTO autoFicticio = automovilService.obtenerPorId(1L);
       ProcesoBoxAutomovilDTO proceso = new ProcesoBoxAutomovilDTO();
       proceso.setBoxId(boxGuardado.getId());
       proceso.setAutomovilId(autoFicticio.getId());
       proceso.setCosto_total(0.0);

       // Guardar el proceso
       servicio.guardarProceso(proceso);

       // 🏗️ Agregar datos a `RedirectAttributes` para que la vista los mantenga
       redirectAttributes.addFlashAttribute("mensaje", "Box y proceso creados exitosamente.");
     
       redirectAttributes.addFlashAttribute("boxn", new BoxDeEstacionamientoDTO());
       redirectAttributes.addFlashAttribute("auto", new AutomovilDTO());

       return "redirect:/procesos/listado"; // Redirige a la lista con los datos mantenidos
   }

   

   
   // Método para guardar un nuevo automóvil
   @PostMapping("/guardar_auto")
   public String guardarAutomovil(@ModelAttribute AutomovilDTO automovilDTO, @RequestParam(required = false) Long boxId)  {
       automovilService.guardarAutomovil(automovilDTO);
       return "redirect:/procesos/listado"; // Redirige a la vista después de guardar
   }
   
   @PostMapping("/seleccionar_proceso")
   public String seleccionarProceso(@RequestParam Long procesoId, RedirectAttributes redirectAttributes, Model model) {
       // Obtener el proceso seleccionado
	   if (procesoId == null || procesoId <= 0) {
	        redirectAttributes.addFlashAttribute("error", "ID de proceso inválido.");
	        return "redirect:/procesos/listado";
	    }
       ProcesoBoxAutomovilDTO procesoSeleccionado = servicio.obtenerPorId(procesoId);

       if (procesoSeleccionado == null) {
           redirectAttributes.addFlashAttribute("error", "El proceso seleccionado no existe.");
           return "redirect:/procesos/listado"; // 🔥 Redirige con mensaje de error
       }

       // 🏗️ Usar `RedirectAttributes` para mantener los datos en la redirección
       model.addAttribute("procesoSeleccionado", procesoSeleccionado); // Guardar en sesión
       redirectAttributes.addFlashAttribute("mensaje", "Proceso seleccionado exitosamente.");
       
       return "redirect:/procesos/listado"; // 🔥 Ahora redirige con los datos preservados
   }

// Procesar selección para asignar un automóvil
   @PostMapping("/procesar_seleccion")
   public String procesarSeleccion(
		   @RequestParam Long procesoId, 
           @RequestParam Long automovilId,
           @RequestParam int horasDeUso,
           RedirectAttributes redirectAttributes
   ) {
	// Obtener el objeto completo a partir del ID
	    ProcesoBoxAutomovilDTO procesoSeleccionado = servicio.obtenerPorId(procesoId);
	    
	   if (procesoSeleccionado == null) {
           redirectAttributes.addFlashAttribute("error", "No hay un proceso seleccionado en la sesión.");
           return "redirect:/procesos/listado";
       }
	   
	   AutomovilDTO automovil = automovilService.obtenerPorId(automovilId);

	   if (automovil == null) {
           redirectAttributes.addFlashAttribute("error", "El automóvil especificado no existe.");
           return "redirect:/procesos/listado";
       }
	   automovil.setEstado(false);
       automovilService.actualizarAuto(automovilId, automovil);

       BoxDeEstacionamientoDTO box = boxService.obtenerPorIdDTO(procesoSeleccionado.getBoxId());
       box.setDisponibilidad(Disponibilidad.ocupado);
       boxService.guardarBox(box);

       procesoSeleccionado.setAutomovilId(automovilId);
       procesoSeleccionado.setHoras_de_uso(horasDeUso);
       procesoSeleccionado.setFecha_hora(LocalDateTime.now());
	   
       // Calcular el costo total
       Double tarifaPorZona = servicio.obtenerTarifaPorZona(box.getZona());
       procesoSeleccionado.calcularCosto(tarifaPorZona);

       servicio.guardarProceso(procesoSeleccionado);

       redirectAttributes.addFlashAttribute("mensaje", "Automóvil asignado correctamente al proceso.");
     
	   return "redirect:/procesos/listado"; // Redirige a la lista principal después de asignar el auto
   }
  
}
