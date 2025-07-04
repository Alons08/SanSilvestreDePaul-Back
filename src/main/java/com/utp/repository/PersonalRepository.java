package com.utp.repository;

import com.utp.entity.Personal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonalRepository extends JpaRepository<Personal, Long> {
    Optional<Personal> findByEmail(String email);
    Optional<Personal> findByUserId(Integer userId);
    boolean existsByEmail(String email);
}
