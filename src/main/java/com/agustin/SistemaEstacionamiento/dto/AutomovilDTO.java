package com.agustin.SistemaEstacionamiento.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutomovilDTO {
    private Long id; // Identificador único del automóvil
    private String marca; // Marca del automóvil
    private String modelo; // Modelo del automóvil
    private String color; // Color del automóvil
    private String duenio; // Dueño del automóvil
    private boolean estado; // Estado del automóvil (activo o inactivo)
}
