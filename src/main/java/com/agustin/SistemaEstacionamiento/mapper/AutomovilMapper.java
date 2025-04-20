package com.agustin.SistemaEstacionamiento.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.agustin.SistemaEstacionamiento.dto.AutomovilDTO;
import com.agustin.SistemaEstacionamiento.model.Automovil;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AutomovilMapper {
    AutomovilMapper INSTANCE = Mappers.getMapper(AutomovilMapper.class);

    @Mappings({
    	@Mapping(target = "id", source = "id"),
    	@Mapping(target = "marca", source = "marca"),
    	@Mapping(target = "modelo", source = "modelo"),
    	@Mapping(target = "estado", source = "estado"),
        @Mapping(target = "color", source = "color"),
        @Mapping(target = "duenio", source = "duenio") 
        
    })
    AutomovilDTO toAutomovilDTO(Automovil automovil);

    @Mappings({
    	@Mapping(target = "id", source = "id"),
    	@Mapping(target = "marca", source = "marca"),
    	@Mapping(target = "modelo", source = "modelo"),
    	@Mapping(target = "estado", source = "estado"),
        @Mapping(target = "color", source = "color"),
        @Mapping(target = "duenio", source = "duenio")
       
    })
    Automovil toAutomovil(AutomovilDTO automovilDTO);

    List<AutomovilDTO> toAutomovilDTOList(List<Automovil> automoviles);
    List<Automovil> toAutomovilList(List<AutomovilDTO> automovilDTOs);
}
