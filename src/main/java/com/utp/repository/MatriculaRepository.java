package com.utp.repository;

import com.utp.entity.Matricula;
import com.utp.entity.EstadoMatricula;
import com.utp.entity.TipoMatricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Year;
import java.util.List;
import java.util.Optional;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
    
    List<Matricula> findByAlumnoId(Long alumnoId);
    
    List<Matricula> findByGradoId(Long gradoId);
    
    List<Matricula> findByAnoEscolar(Year anoEscolar);
    
    List<Matricula> findByEstado(EstadoMatricula estado);
    
    List<Matricula> findByTipoMatricula(TipoMatricula tipoMatricula);
    
    @Query("SELECT m FROM Matricula m WHERE m.alumno.apoderado.id = :apoderadoId")
    List<Matricula> findByApoderadoId(@Param("apoderadoId") Long apoderadoId);
    
    @Query("SELECT m FROM Matricula m WHERE m.alumno.apoderado.user.id = :userId")  
    List<Matricula> findByApoderadoUserId(@Param("userId") Integer userId);
    
    Optional<Matricula> findByAlumnoIdAndAnoEscolar(Long alumnoId, Year anoEscolar);
    
    @Query("SELECT m FROM Matricula m WHERE m.alumno.id = :alumnoId AND m.anoEscolar = :anoEscolar AND m.estado = :estado")
    Optional<Matricula> findByAlumnoIdAndAnoEscolarAndEstado(
        @Param("alumnoId") Long alumnoId, 
        @Param("anoEscolar") Year anoEscolar, 
        @Param("estado") EstadoMatricula estado
    );
}
