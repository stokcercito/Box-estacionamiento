package com.agustin.SistemaEstacionamiento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
@ComponentScan(basePackages = "com.agustin.SistemaEstacionamiento")
@EnableJpaRepositories(basePackages = "com.agustin.SistemaEstacionamiento.repository")
@EntityScan(basePackages = "com.agustin.SistemaEstacionamiento.model")
public class SistemaEstacionamientoApplication {

    private static final Logger logger = LoggerFactory.getLogger(SistemaEstacionamientoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SistemaEstacionamientoApplication.class, args);
        logger.info("SistemaEstacionamientoApplication ha iniciado correctamente.");
    }
}
