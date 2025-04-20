package com.agustin.SistemaEstacionamiento.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.Min;

import com.agustin.SistemaEstacionamiento.model.Disponibilidad;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoxDeEstacionamientoDTO {
    private Long id; // Identificador único del box
    @Min(value = 1, message = "La zona debe ser al menos 1.")
    @Max(value = 2, message = "La zona no puede ser mayor a 2.")
    private int zona; // Zona del estacionamiento (1 o 2)
    @Enumerated(EnumType.STRING) // Almacena el enum como texto (LIBRE u OCUPADO)
    private Disponibilidad disponibilidad; 
}
