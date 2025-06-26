package com.utp.repository;

import com.utp.entity.Aula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AulaRepository extends JpaRepository<Aula, Long> {
    
    List<Aula> findByGradoId(Long gradoId);
    
    List<Aula> findByDocenteId(Long docenteId);
    
    @Query("SELECT a FROM Aula a WHERE a.grado.id IN " +
           "(SELECT m.grado.id FROM Matricula m WHERE m.alumno.apoderado.user.id = :userId)")
    List<Aula> findAulasByApoderadoUserId(@Param("userId") Integer userId);
}
