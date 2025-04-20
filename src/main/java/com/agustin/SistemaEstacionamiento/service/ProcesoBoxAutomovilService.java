package com.agustin.SistemaEstacionamiento.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.agustin.SistemaEstacionamiento.dto.ProcesoBoxAutomovilDTO;
import com.agustin.SistemaEstacionamiento.mapper.ProcesoBoxAutomovilMapper;
import com.agustin.SistemaEstacionamiento.model.ProcesoBoxAutomovil;
import com.agustin.SistemaEstacionamiento.repository.ProcesoBoxAutomovilRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProcesoBoxAutomovilService {

    private final ProcesoBoxAutomovilRepository repository;
    private final ProcesoBoxAutomovilMapper mapper;
    private static final Logger logger = LoggerFactory.getLogger(ProcesoBoxAutomovilService.class);

    public ProcesoBoxAutomovilService(ProcesoBoxAutomovilRepository repository, @Qualifier("procesoBoxAutomovilMapper") ProcesoBoxAutomovilMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    // Guardar un proceso utilizando DTO, calcular costo automáticamente
    public ProcesoBoxAutomovilDTO guardarProceso(ProcesoBoxAutomovilDTO procesoDTO) {
        if (procesoDTO == null) {
            throw new IllegalArgumentException("El DTO del proceso no puede ser nulo.");
        }

        // Mapear el DTO a la entidad
        ProcesoBoxAutomovil proceso = mapper.toProcesoBoxAutomovil(procesoDTO); // Llamar al método correcto
        logger.info("Guardando proceso: {}", procesoDTO);
        // Guardar en la base de datos y mapear de vuelta a DTO
        return mapper.toProcesoBoxAutomovilDTO(repository.save(proceso));
    }

    // Obtener todos los procesos como DTOs
 
    public List<ProcesoBoxAutomovilDTO> obtenerTodos() {
        logger.info("Obteniendo todos los procesos de box y automóvil");
        List<ProcesoBoxAutomovil> procesos = repository.findAllWithAutomovilAndBox();

        if (procesos.isEmpty()) {
            logger.warn("No se encontraron procesos de box y automóvil.");
            return Collections.emptyList(); // Devuelve una lista vacía si no hay resultados.
        }

        return procesos.stream()
                .map(mapper::toProcesoBoxAutomovilDTO)
                .collect(Collectors.toList());
    }

    // Obtener un proceso por ID como DTO
    public ProcesoBoxAutomovilDTO obtenerPorId(Long id) {
        logger.info("Obteniendo proceso por ID: {}", id);
        return repository.findById(id)
                .map(mapper::toProcesoBoxAutomovilDTO)
                .orElseThrow(() -> {
                    logger.warn("Proceso con ID {} no encontrado", id);
                    return new IllegalArgumentException("El proceso con ID " + id + " no existe.");
                });
    }
    
 
    
    public ProcesoBoxAutomovilDTO obtenerProcesoPorBoxId(Long boxId) {
        logger.info("Obteniendo proceso por ID de Box: {}", boxId);

        return repository.findByBoxId(boxId) // Método en el repositorio para buscar por boxId
                .map(mapper::toProcesoBoxAutomovilDTO) // Mapea la entidad a un DTO
                .orElseThrow(() -> {
                    logger.warn("Proceso con Box ID {} no encontrado", boxId);
                    return new IllegalArgumentException("El proceso con Box ID " + boxId + " no existe.");
                });
    }
    
    
    // Actualizar un proceso por ID
   
    public ProcesoBoxAutomovilDTO actualizarProceso(ProcesoBoxAutomovilDTO procesoDTO) {
        if (procesoDTO == null) {
            throw new IllegalArgumentException("El DTO del proceso no puede ser nulo.");
        }

        // Mapear el DTO a la entidad
        ProcesoBoxAutomovil proceso = mapper.toProcesoBoxAutomovil(procesoDTO); // Llamar al método correcto
        logger.info("Actualizando proceso: {}", procesoDTO);
        // Guardar en la base de datos y mapear de vuelta a DTO
        return mapper.toProcesoBoxAutomovilDTO(repository.save(proceso));
    }
    

    // Eliminar un proceso por ID
    public void eliminarProceso(Long id) {
        logger.info("Eliminando proceso por ID: {}", id);
        if (!repository.existsById(id)) {
            logger.warn("Proceso con ID {} no encontrado", id);
            throw new IllegalArgumentException("El proceso con ID " + id + " no existe.");
        }
        repository.deleteById(id);
    }

    // Método para calcular la tarifa por zona
    public Double obtenerTarifaPorZona(int zona) {
        switch (zona) {
            case 1:
                return 500.0; // Tarifa para zona 1
            case 2:
                return 300.0; // Tarifa para zona 2
            default:
                return null; // Zona inválida
        }
    }

	
    
    
}
