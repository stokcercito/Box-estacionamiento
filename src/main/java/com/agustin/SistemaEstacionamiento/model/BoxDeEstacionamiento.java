package com.agustin.SistemaEstacionamiento.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "box_de_estacionamiento")
public class BoxDeEstacionamiento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único del box

    @Min(value = 1, message = "La zona debe ser al menos 1.")
    @Max(value = 2, message = "La zona no puede ser mayor a 2.")
    private int zona; // Zona del estacionamiento: 1 o 2

    @Enumerated(EnumType.STRING) // Almacena el enum como texto (LIBRE u OCUPADO)
    private Disponibilidad disponibilidad; 
}
