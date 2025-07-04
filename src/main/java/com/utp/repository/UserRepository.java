package com.utp.repository;

import com.utp.entity.Role;
import com.utp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
    
    boolean existsByRole(Role role);
    
    List<User> findByEstado(Boolean estado);

}