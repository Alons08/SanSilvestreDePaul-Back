package com.utp.repository;

import com.utp.entity.Grado;
import com.utp.entity.NivelEducativo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradoRepository extends JpaRepository<Grado, Long> {
    
    List<Grado> findByNivel(NivelEducativo nivel);
    
    List<Grado> findByNombreGradoContainingIgnoreCase(String nombreGrado);
}
