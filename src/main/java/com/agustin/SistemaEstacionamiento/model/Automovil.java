package com.agustin.SistemaEstacionamiento.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "automoviles")
public class Automovil {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único

    @NotNull(message = "La marca es obligatoria.")
    @Size(min = 2, max = 50, message = "La marca debe tener entre 2 y 50 caracteres.")
    private String marca; // Marca del automóvil

    @NotNull(message = "El modelo es obligatorio.")
    @Size(min = 1, max = 50, message = "El modelo debe tener entre 1 y 50 caracteres.")
    private String modelo; // Modelo del automóvil

    @Size(max = 20, message = "El color no debe exceder los 20 caracteres.")
    @Pattern(regexp = "[A-Za-z ]+", message = "El color debe contener solo letras.")
    private String color; // Color del automóvil

    @NotNull(message = "El DNI del dueño es obligatorio.")
    @Pattern(regexp = "\\d{8}|N/A", message = "El DNI debe contener 8 dígitos.")
    private String duenio; // DNI del dueño del automóvil

    private boolean estado; // Indica si el auto está disponible o no
}
