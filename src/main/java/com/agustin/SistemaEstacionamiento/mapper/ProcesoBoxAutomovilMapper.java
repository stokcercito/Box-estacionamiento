package com.agustin.SistemaEstacionamiento.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.agustin.SistemaEstacionamiento.dto.ProcesoBoxAutomovilDTO;
import com.agustin.SistemaEstacionamiento.model.ProcesoBoxAutomovil;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProcesoBoxAutomovilMapper {
    ProcesoBoxAutomovilMapper INSTANCE = Mappers.getMapper(ProcesoBoxAutomovilMapper.class);

    // De entidad a DTO
    @Mappings({
        @Mapping(target = "duenioAutomovil", source = "automovil.duenio"),
        @Mapping(target = "marcaAutomovil", source = "automovil.marca"),
        @Mapping(target = "modeloAutomovil", source = "automovil.modelo"),
        @Mapping(target = "colorAutomovil", source = "automovil.color"),
        @Mapping(target = "zonaBox", source = "box.zona"),
        @Mapping(target = "disponibilidadBox", source = "box.disponibilidad"),
        @Mapping(target = "horas_de_uso", source = "horas_de_uso"),
        @Mapping(target = "costo_total", source = "costo_total"),
        @Mapping(target = "automovilId", source = "automovil.id"),
        @Mapping(target = "boxId", source = "box.id"),
        @Mapping(target = "id", source = "id")
    })
    ProcesoBoxAutomovilDTO toProcesoBoxAutomovilDTO(ProcesoBoxAutomovil proceso);

    // De DTO a entidad
    @Mappings({
        @Mapping(target = "automovil.duenio", source = "duenioAutomovil"),
        @Mapping(target = "automovil.marca", source = "marcaAutomovil"),
        @Mapping(target = "automovil.modelo", source = "modeloAutomovil"),
        @Mapping(target = "automovil.color", source = "colorAutomovil"),
        @Mapping(target = "box.zona", source = "zonaBox"),
        @Mapping(target = "box.disponibilidad", source = "disponibilidadBox"),
        @Mapping(target = "horas_de_uso", source = "horas_de_uso"),
        @Mapping(target = "costo_total", source = "costo_total"),
        @Mapping(target = "automovil.id", source = "automovilId"),
        @Mapping(target = "box.id", source = "boxId"),
        @Mapping(target = "id", source = "id")
    })
    ProcesoBoxAutomovil toProcesoBoxAutomovil(ProcesoBoxAutomovilDTO procesoDTO);

    // Lista de entidades a lista de DTOs
    List<ProcesoBoxAutomovilDTO> toProcesoBoxAutomovilDTOList(List<ProcesoBoxAutomovil> procesos);

    // Lista de DTOs a lista de entidades
    List<ProcesoBoxAutomovil> toProcesoBoxAutomovilList(List<ProcesoBoxAutomovilDTO> procesoDTOs);
}