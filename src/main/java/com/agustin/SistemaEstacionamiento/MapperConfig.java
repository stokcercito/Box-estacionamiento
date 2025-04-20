package com.agustin.SistemaEstacionamiento;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.agustin.SistemaEstacionamiento.mapper.AutomovilMapper;
import com.agustin.SistemaEstacionamiento.mapper.BoxDeEstacionamientoMapper;
import com.agustin.SistemaEstacionamiento.mapper.ProcesoBoxAutomovilMapper;

import org.mapstruct.factory.Mappers;

@Configuration
public class MapperConfig {

    @Bean
    @Primary
    BoxDeEstacionamientoMapper boxDeEstacionamientoMapper() {
        return Mappers.getMapper(BoxDeEstacionamientoMapper.class);
    }

    @Bean
    AutomovilMapper automovilMapper() {
        return Mappers.getMapper(AutomovilMapper.class);
    }

    @Bean
    ProcesoBoxAutomovilMapper procesoBoxAutomovilMapper() {
        return Mappers.getMapper(ProcesoBoxAutomovilMapper.class);
    }
}

