package com.agustin.SistemaEstacionamiento.service;

import org.springframework.stereotype.Service;

import com.agustin.SistemaEstacionamiento.dto.AutomovilDTO;
import com.agustin.SistemaEstacionamiento.dto.BoxDeEstacionamientoDTO;
import com.agustin.SistemaEstacionamiento.mapper.AutomovilMapper;
import com.agustin.SistemaEstacionamiento.model.Automovil;
import com.agustin.SistemaEstacionamiento.model.BoxDeEstacionamiento;
import com.agustin.SistemaEstacionamiento.repository.AutomovilRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AutomovilService {

    private final AutomovilRepository automovilRepository;
    private final AutomovilMapper automovilMapper;
    private static final Logger logger = LoggerFactory.getLogger(AutomovilService.class);

    public AutomovilService(AutomovilRepository automovilRepository, AutomovilMapper automovilMapper) {
        this.automovilRepository = automovilRepository;
        this.automovilMapper = automovilMapper;
    }

    public List<AutomovilDTO> obtenerTodos() {
        logger.info("Obteniendo todos los automóviles");
        return automovilRepository.findAll().stream()
                .map(automovilMapper::toAutomovilDTO)
                .collect(Collectors.toList());
    }
    
    public List<AutomovilDTO> obtenerAutomovilesPorEstadoTrue() {
        logger.info("Obteniendo automóviles con estado=true");
        return automovilRepository.findAll().stream()
                .filter(automovil -> automovil.isEstado()) // Filtra los automóviles con estado=true
                .map(automovilMapper::toAutomovilDTO)     // Mapea a DTO
                .collect(Collectors.toList());           // Recoge la lista
    }
    
    public List<AutomovilDTO> obtenerAutomovilesPorEstadoFalse() {
        logger.info("Obteniendo automóviles con estado=false");
        return automovilRepository.findAll().stream()
                .filter(automovil -> !automovil.isEstado()) // Filtra los automóviles con estado=false
                .map(automovilMapper::toAutomovilDTO)       // Mapea a DTO
                .collect(Collectors.toList());             // Recoge la lista
    }


    public AutomovilDTO guardarAutomovil(AutomovilDTO automovilDTO) {
        logger.info("Guardando automóvil: {}", automovilDTO);
        Automovil automovil = automovilMapper.toAutomovil(automovilDTO);
        automovil = automovilRepository.save(automovil);
        return automovilMapper.toAutomovilDTO(automovil);
    }
   

    public AutomovilDTO obtenerPorId(Long id) {
        logger.info("Obteniendo automóvil por ID: {}", id);
        Optional<Automovil> automovil = automovilRepository.findById(id);
        return automovil.map(automovilMapper::toAutomovilDTO).orElse(null);
    }
    
    public AutomovilDTO actualizarAuto(Long id, AutomovilDTO automovilDTO) {
        logger.info("Actualizando Automovil con ID: {}", id);
        Optional<Automovil> autoExistenteOpt = automovilRepository.findById(id);
        if (autoExistenteOpt.isPresent()) {
            Automovil autoExistente = autoExistenteOpt.get();
            autoExistente.setColor(automovilDTO.getColor());
            autoExistente.setDuenio(automovilDTO.getDuenio());
            autoExistente.setEstado(automovilDTO.isEstado());
            autoExistente.setMarca(automovilDTO.getMarca());
            automovilRepository.save(autoExistente);
            return automovilMapper.toAutomovilDTO(autoExistente);
        } else {
            logger.warn("Automovil con ID {} no encontrado", id);
            throw new IllegalArgumentException("Automovil con ID " + id + " no existe.");
        }
    }

    public void eliminarAutomovil(Long id) {
        logger.info("Eliminando automóvil por ID: {}", id);
        if (automovilRepository.existsById(id)) {
            automovilRepository.deleteById(id);
            logger.info("Automóvil con ID {} eliminado", id);
        } else {
            logger.warn("El automóvil con ID {} no existe", id);
            throw new IllegalArgumentException("El automóvil con ID " + id + " no existe.");
        }
    }
}
