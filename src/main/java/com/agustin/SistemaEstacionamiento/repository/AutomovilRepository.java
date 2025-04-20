package com.agustin.SistemaEstacionamiento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.agustin.SistemaEstacionamiento.model.Automovil;

@Repository
public interface AutomovilRepository extends JpaRepository<Automovil, Long> {
    // Métodos adicionales personalizados, si son necesarios
}
