package com.utp.repository;

import com.utp.entity.FechaPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FechaPagoRepository extends JpaRepository<FechaPago, Long> {
    
    List<FechaPago> findByMatriculaId(Long matriculaId);
    
    List<FechaPago> findByPagado(Boolean pagado);
    
    List<FechaPago> findByFechaVencimientoBefore(LocalDate fecha);
    
    @Query("SELECT fp FROM FechaPago fp WHERE fp.matricula.alumno.apoderado.id = :apoderadoId")
    List<FechaPago> findByApoderadoId(@Param("apoderadoId") Long apoderadoId);
    
    @Query("SELECT fp FROM FechaPago fp WHERE fp.matricula.alumno.apoderado.user.id = :userId")
    List<FechaPago> findByApoderadoUserId(@Param("userId") Integer userId);
    
    @Query("SELECT fp FROM FechaPago fp WHERE fp.matricula.alumno.apoderado.user.id = :userId AND fp.pagado = false")
    List<FechaPago> findPendientesByApoderadoUserId(@Param("userId") Integer userId);
    
    @Query("SELECT fp FROM FechaPago fp WHERE fp.matricula.alumno.apoderado.user.id = :userId AND fp.pagado = true")
    List<FechaPago> findPagadosByApoderadoUserId(@Param("userId") Integer userId);
    
    @Query("SELECT fp FROM FechaPago fp WHERE fp.matricula.id = :matriculaId AND fp.pagado = :pagado")
    List<FechaPago> findByMatriculaIdAndPagado(@Param("matriculaId") Long matriculaId, @Param("pagado") Boolean pagado);
    
    @Query("SELECT fp FROM FechaPago fp WHERE fp.fechaVencimiento BETWEEN :fechaInicio AND :fechaFin")
    List<FechaPago> findByFechaVencimientoBetween(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);
    
    @Query("SELECT fp FROM FechaPago fp WHERE fp.matricula.alumno.id = :alumnoId")
    List<FechaPago> findByAlumnoId(@Param("alumnoId") Long alumnoId);
    
    @Query("SELECT fp FROM FechaPago fp WHERE fp.matricula.alumno.id = :alumnoId AND fp.pagado = false")
    List<FechaPago> findPendientesByAlumnoId(@Param("alumnoId") Long alumnoId);
    
    @Query("SELECT fp FROM FechaPago fp WHERE fp.matricula.alumno.id = :alumnoId AND LOWER(fp.descripcion) LIKE LOWER(CONCAT('%', :descripcion, '%'))")
    List<FechaPago> findByAlumnoIdAndDescripcionContainingIgnoreCase(@Param("alumnoId") Long alumnoId, @Param("descripcion") String descripcion);
}
