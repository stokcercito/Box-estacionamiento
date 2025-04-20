package com.agustin.SistemaEstacionamiento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.agustin.SistemaEstacionamiento.model.BoxDeEstacionamiento;

public interface BoxDeEstacionamientoRepository extends JpaRepository<BoxDeEstacionamiento, Long> {
}
