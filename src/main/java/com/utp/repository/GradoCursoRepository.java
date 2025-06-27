package com.utp.repository;

import com.utp.entity.GradoCurso;
import com.utp.entity.Grado;
import com.utp.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GradoCursoRepository extends JpaRepository<GradoCurso, Long> {
    List<GradoCurso> findByGrado(Grado grado);
    List<GradoCurso> findByCurso(Curso curso);
    List<GradoCurso> findByGradoId(Long gradoId);
    Optional<GradoCurso> findByGradoAndCurso(Grado grado, Curso curso);
    boolean existsByGradoAndCurso(Grado grado, Curso curso);
    
    @Query("SELECT gc FROM GradoCurso gc JOIN FETCH gc.grado JOIN FETCH gc.curso")
    List<GradoCurso> findAllWithDetails();
    
    @Query("SELECT gc FROM GradoCurso gc JOIN FETCH gc.grado JOIN FETCH gc.curso WHERE gc.grado.id = :gradoId")
    List<GradoCurso> findByGradoIdWithDetails(Long gradoId);
}
