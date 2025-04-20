package com.agustin.SistemaEstacionamiento.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "procesos_box_automovil")
public class ProcesoBoxAutomovil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // Relación con Automovil (Muchos a Uno)
    @JoinColumn(name = "automovil_id", nullable = false)
    private Automovil automovil;

    @ManyToOne // Relación con BoxDeEstacionamiento (Muchos a Uno)
    @JoinColumn(name = "box_id", nullable = false)
    private BoxDeEstacionamiento box;

    @Min(value = 1, message = "Las horas de uso deben ser al menos 1.")
    private Integer horas_de_uso; // Cantidad de horas usadas

    @DecimalMin(value = "0.0", inclusive = true, message = "El costo total debe ser mayor o igual que 0.")
    private Double costo_total; // Costo total del uso

    private LocalDateTime fecha_hora; // Fecha y hora del proceso

    // Método para calcular el costo total del proceso
    public void calcularCosto(Double tarifaPorHora) {
        if (tarifaPorHora == null || tarifaPorHora <= 0) {
            throw new IllegalArgumentException("La tarifa por hora debe ser mayor que 0.");
        }
        if (horas_de_uso == null || horas_de_uso <= 0) {
            throw new IllegalArgumentException("Las horas de uso deben ser mayor que 0.");
        }
        this.costo_total = tarifaPorHora * this.horas_de_uso;
    }
}
