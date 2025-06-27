package com.utp.repository;

import com.utp.entity.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
    
    List<Alumno> findByApoderadoId(Long apoderadoId);
    
    @Query("SELECT a FROM Alumno a WHERE a.apoderado.user.id = :userId")
    List<Alumno> findByApoderadoUserId(@Param("userId") Integer userId);
    
    Optional<Alumno> findByDocumentoIdentidadNumeroDocumento(String numeroDocumento);
    
    List<Alumno> findByEstado(Boolean estado);
    
    @Query("SELECT a FROM Alumno a WHERE LOWER(a.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')) OR LOWER(a.apellido) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Alumno> findByNombreOrApellidoContainingIgnoreCase(@Param("nombre") String nombre);
    
    @Query("SELECT a FROM Alumno a WHERE a.apoderado.id = :apoderadoId AND a.estado = true")
    List<Alumno> findByApoderadoIdAndEstadoTrue(@Param("apoderadoId") Long apoderadoId);
    
    @Query("SELECT a FROM Alumno a WHERE a.apoderado.estado = true AND a.estado = true")
    List<Alumno> findAlumnosConApoderadoActivo();
    
    @Query("SELECT a FROM Alumno a WHERE a.apoderado IS NULL OR a.apoderado.estado = false")
    List<Alumno> findAlumnosSinApoderadoActivo();
}
