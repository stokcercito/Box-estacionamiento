package com.agustin.SistemaEstacionamiento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.agustin.SistemaEstacionamiento.model.ProcesoBoxAutomovil;

import java.util.List;
import java.util.Optional;

// Interfaz para manejar operaciones CRUD
@Repository
public interface ProcesoBoxAutomovilRepository extends JpaRepository<ProcesoBoxAutomovil, Long> {
    @Query("SELECT p FROM ProcesoBoxAutomovil p " +
           "JOIN FETCH p.automovil a " +
           "JOIN FETCH p.box b")
    List<ProcesoBoxAutomovil> findAllWithAutomovilAndBox();

    @Query("SELECT p FROM ProcesoBoxAutomovil p " +
           "JOIN FETCH p.automovil a " +
           "JOIN FETCH p.box b " +
           "WHERE p.id = :id")
    ProcesoBoxAutomovil findByIdWithAutomovilAndBox(@Param("id") Long id);
    
    Optional<ProcesoBoxAutomovil> findByBoxId(Long boxId);
  

}