package com.agustin.SistemaEstacionamiento.service;

import org.springframework.stereotype.Service;

import com.agustin.SistemaEstacionamiento.dto.AutomovilDTO;
import com.agustin.SistemaEstacionamiento.dto.BoxDeEstacionamientoDTO;
import com.agustin.SistemaEstacionamiento.mapper.BoxDeEstacionamientoMapper;
import com.agustin.SistemaEstacionamiento.model.BoxDeEstacionamiento;
import com.agustin.SistemaEstacionamiento.model.Disponibilidad;
import com.agustin.SistemaEstacionamiento.repository.AutomovilRepository;
import com.agustin.SistemaEstacionamiento.repository.BoxDeEstacionamientoRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BoxDeEstacionamientoService {

    private final BoxDeEstacionamientoRepository boxRepository;
    private final AutomovilRepository automovilRepository;
    private final BoxDeEstacionamientoMapper mapper;
    private static final Logger logger = LoggerFactory.getLogger(BoxDeEstacionamientoService.class);

    public BoxDeEstacionamientoService(BoxDeEstacionamientoRepository boxRepository, AutomovilRepository automovilRepository, BoxDeEstacionamientoMapper mapper) {
        this.boxRepository = boxRepository;
        this.automovilRepository = automovilRepository;
        this.mapper = mapper;
    }

    public List<BoxDeEstacionamientoDTO> obtenerTodos() {
        logger.info("Obteniendo todos los boxes de estacionamiento");
        return boxRepository.findAll().stream()
                .map(mapper::toBoxDeEstacionamientoDTO)
                .collect(Collectors.toList());
    }
    
    public List<BoxDeEstacionamientoDTO> obtenerBoxesLibres() {
        logger.info("Obteniendo todos los boxes con disponibilidad libre");
        return boxRepository.findAll().stream()
                .filter(box -> box.getDisponibilidad() == Disponibilidad.libre) // Filtrar por disponibilidad libre
                .map(mapper::toBoxDeEstacionamientoDTO) // Convertir a DTO
                .collect(Collectors.toList()); // Recopilar en una lista
    }
    
    public List<BoxDeEstacionamientoDTO> obtenerBoxesOcup() {
        logger.info("Obteniendo todos los boxes con disponibilidad Ocupado");
        return boxRepository.findAll().stream()
                .filter(box -> box.getDisponibilidad() == Disponibilidad.ocupado) // Filtrar por disponibilidad ocupado
                .map(mapper::toBoxDeEstacionamientoDTO) // Convertir a DTO
                .collect(Collectors.toList()); // Recopilar en una lista
    }


    public BoxDeEstacionamientoDTO guardarBox(BoxDeEstacionamientoDTO boxDTO) {
        logger.info("Guardando box de estacionamiento: {}", boxDTO);
        BoxDeEstacionamiento box = mapper.toBoxDeEstacionamiento(boxDTO);
        box = boxRepository.save(box);
        return mapper.toBoxDeEstacionamientoDTO(box);
    }

    public BoxDeEstacionamientoDTO actualizarBox(Long id, BoxDeEstacionamientoDTO boxDTO) {
        logger.info("Actualizando box de estacionamiento con ID: {}", id);
        Optional<BoxDeEstacionamiento> boxExistenteOpt = boxRepository.findById(id);
        if (boxExistenteOpt.isPresent()) {
            BoxDeEstacionamiento boxExistente = boxExistenteOpt.get();
            boxExistente.setZona(boxDTO.getZona());
          //  Disponibilidad disponibilidad = Disponibilidad.valueOf(boxDTO.getDisponibilidad());
           // boxExistente.setDisponibilidad(disponibilidad);
            boxExistente.setDisponibilidad(boxDTO.getDisponibilidad());
            boxRepository.save(boxExistente);
            return mapper.toBoxDeEstacionamientoDTO(boxExistente);
        } else {
            logger.warn("Box de estacionamiento con ID {} no encontrado", id);
            throw new IllegalArgumentException("Box de estacionamiento con ID " + id + " no existe.");
        }
    }

    public BoxDeEstacionamientoDTO obtenerPorIdDTO(Long id) {
        logger.info("Obteniendo box de estacionamiento por ID: {}", id);
        return boxRepository.findById(id)
                .map(mapper::toBoxDeEstacionamientoDTO)
                .orElseThrow(() -> {
                    logger.warn("Box de estacionamiento con ID {} no encontrado", id);
                    return new IllegalArgumentException("Box de estacionamiento con ID " + id + " no existe.");
                });
    }
    public BoxDeEstacionamiento obtenerPorId(Long id) {
        logger.info("Obteniendo box de estacionamiento por ID (modelo): {}", id);
        return boxRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Box de estacionamiento con ID {} no encontrado", id);
                    return new IllegalArgumentException("Box de estacionamiento con ID " + id + " no existe.");
                });
    }

    
    public List<AutomovilDTO> obtenerAutomoviles() {
        logger.info("Obteniendo todos los automóviles");
        return automovilRepository.findAll().stream()
                .map(automovil -> new AutomovilDTO(automovil.getId(), automovil.getMarca(), automovil.getModelo(), automovil.getDuenio(), null, false))
                .collect(Collectors.toList());
    }

    public boolean esBoxDisponible(Long id) {
        logger.info("Verificando disponibilidad del box de estacionamiento con ID: {}", id);
        return boxRepository.findById(id)
                .map(box -> box.getDisponibilidad() == Disponibilidad.libre)
                .orElse(false);
    }

    public void eliminarBox(Long id) {
        logger.info("Eliminando box de estacionamiento con ID: {}", id);
        if (boxRepository.existsById(id)) {
            boxRepository.deleteById(id);
            logger.info("Box de estacionamiento con ID {} eliminado", id);
        } else {
            logger.warn("Box de estacionamiento con ID {} no encontrado", id);
            throw new IllegalArgumentException("Box de estacionamiento con ID " + id + " no existe.");
        }
    }
    
    public Double obtenerTarifaPorZona(int zona) {
        switch (zona) {
            case 1:
                return 500.0;
            case 2:
                return 300.0;
            default:
                throw new IllegalArgumentException("Zona inválida: " + zona);
        }
    }

}
