package com.agustin.SistemaEstacionamiento.dto;

import java.time.LocalDateTime;

import com.agustin.SistemaEstacionamiento.model.Disponibilidad;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcesoBoxAutomovilDTO {

	private Long id; // Identificador único del proceso
    private Long automovilId; // ID del automóvil asociado
    private Long boxId; // ID del box asociado
    private Integer horas_de_uso; // Cantidad de horas utilizadas
    private Double costo_total; // Costo total del uso
    private LocalDateTime fecha_hora; // Fecha y hora del proceso

   // private AutomovilDTO automovil; // Automóvil anidado
    //private BoxDeEstacionamientoDTO box;

    // Campos de la tabla Automóviles
    private int duenioAutomovil;
    private String marcaAutomovil; // Marca del automóvil asociado
    private String modeloAutomovil; // Modelo del automóvil asociado
    private String colorAutomovil;

    // Campos de la tabla BoxDeEstacionamiento
    private int zonaBox; // Zona del box asociado (1 o 2)
    private Disponibilidad disponibilidadBox; // Disponibilidad del box (LIBRE u OCUPADO)

	
    
    
  
    
    
   
    public void calcularCosto(Double tarifaPorHora) {
		// TODO Auto-generated method stub
		 if (tarifaPorHora == null || tarifaPorHora <= 0) {
	            throw new IllegalArgumentException("La tarifa por hora debe ser mayor que 0.");
	        }
	        if (horas_de_uso == null || horas_de_uso <= 0) {
	            throw new IllegalArgumentException("Las horas de uso deben ser mayor que 0.");
	        }
	        this.costo_total = tarifaPorHora * this.horas_de_uso;
		
	}
}
