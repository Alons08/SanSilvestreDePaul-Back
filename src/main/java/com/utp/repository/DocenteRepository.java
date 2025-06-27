package com.utp.repository;

import com.utp.entity.Docente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocenteRepository extends JpaRepository<Docente, Long> {
    
    Optional<Docente> findByDocumentoIdentidadNumeroDocumento(String numeroDocumento);
    
    List<Docente> findByEstado(Boolean estado);
    
    List<Docente> findByNombreContainingIgnoreCase(String nombre);
    
    List<Docente> findByApellidoContainingIgnoreCase(String apellido);
    
    @Query("SELECT d FROM Docente d WHERE d.estado = true")
    List<Docente> findDocentesActivos();
}
