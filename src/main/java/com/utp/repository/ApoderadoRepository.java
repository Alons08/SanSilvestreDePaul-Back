package com.utp.repository;

import com.utp.entity.Apoderado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApoderadoRepository extends JpaRepository<Apoderado, Long> {
    
    Optional<Apoderado> findByEmail(String email);
    Optional<Apoderado> findByUser_Username(String username);
    Optional<Apoderado> findByUserId(Integer userId);
    boolean existsByEmail(String email);
    
    List<Apoderado> findByEstado(Boolean estado);
    
    @Query("SELECT a FROM Apoderado a WHERE a.estado = true")
    List<Apoderado> findApoderadosActivos();
    
    @Query("SELECT a FROM Apoderado a WHERE a.user.id = :userId AND a.estado = true")
    Optional<Apoderado> findByUserIdAndEstadoTrue(@Param("userId") Integer userId);
}
