package com.utp.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    
    Integer obtenerUserIdPorUsername(String username);
    
    UserDetails obtenerUserDetailsPorUsername(String username);
    
    boolean esApoderadoDelAlumno(Integer userId, Long alumnoId);
    
    boolean esMatriculaDelApoderado(Long matriculaId, String username);
}
