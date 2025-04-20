package com.agustin.SistemaEstacionamiento.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.agustin.SistemaEstacionamiento.dto.BoxDeEstacionamientoDTO;
import com.agustin.SistemaEstacionamiento.model.BoxDeEstacionamiento;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BoxDeEstacionamientoMapper {

    @Mapping(target = "disponibilidad", source = "disponibilidad") // Enum se mantiene sin cambios
    BoxDeEstacionamientoDTO toBoxDeEstacionamientoDTO(BoxDeEstacionamiento box);

    BoxDeEstacionamiento toBoxDeEstacionamiento(BoxDeEstacionamientoDTO boxDTO);

    // Métodos para listas
    List<BoxDeEstacionamientoDTO> toBoxDeEstacionamientoDTOList(List<BoxDeEstacionamiento> boxes);
    List<BoxDeEstacionamiento> toBoxDeEstacionamientoList(List<BoxDeEstacionamientoDTO> boxDTOs);
}
