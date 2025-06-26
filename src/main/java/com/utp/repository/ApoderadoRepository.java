package com.utp.repository;

import com.utp.entity.Apoderado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApoderadoRepository extends JpaRepository<Apoderado, Long> {
    
    Optional<Apoderado> findByEmail(String email);
    Optional<Apoderado> findByUser_Username(String username);
    Optional<Apoderado> findByUserId(Integer userId);
    boolean existsByEmail(String email);
}
