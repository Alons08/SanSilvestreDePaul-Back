package com.utp.controller;

import com.utp.dto.response.ApoderadoDashboardResponse;
import com.utp.dto.response.FechaPagoResponse;
import com.utp.dto.response.HorarioResponse;
import com.utp.dto.response.CursoAlumnoResponse;
import com.utp.service.ApoderadoService;
import com.utp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/apoderado")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('Apoderado')")
public class ApoderadoController {

    private final ApoderadoService apoderadoService;
    private final UserService userService;

    @GetMapping("/dashboard")
    public ResponseEntity<ApoderadoDashboardResponse> obtenerDashboard() {
        try {
            Integer userId = obtenerUserIdAutenticado();
            ApoderadoDashboardResponse response = apoderadoService.obtenerDashboardApoderado(userId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/fechas-pago/pendientes")
    public ResponseEntity<List<FechaPagoResponse>> obtenerFechasPagoPendientes() {
        try {
            Integer userId = obtenerUserIdAutenticado();
            List<FechaPagoResponse> response = apoderadoService.obtenerFechasPagoPendientes(userId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/fechas-pago")
    public ResponseEntity<List<FechaPagoResponse>> obtenerTodasLasFechasPago() {
        try {
            Integer userId = obtenerUserIdAutenticado();
            List<FechaPagoResponse> response = apoderadoService.obtenerTodasLasFechasPago(userId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/fechas-pago/pagadas")
    public ResponseEntity<List<FechaPagoResponse>> obtenerFechasPagoPagadas() {
        try {
            Integer userId = obtenerUserIdAutenticado();
            List<FechaPagoResponse> response = apoderadoService.obtenerFechasPagoPagadas(userId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/horarios")
    public ResponseEntity<List<HorarioResponse>> obtenerHorarios() {
        try {
            Integer userId = obtenerUserIdAutenticado();
            List<HorarioResponse> response = apoderadoService.obtenerHorariosAlumnos(userId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/cursos")
    public ResponseEntity<List<CursoAlumnoResponse>> obtenerCursosHijos() {
        try {
            Integer userId = obtenerUserIdAutenticado();
            List<CursoAlumnoResponse> response = apoderadoService.obtenerCursosHijos(userId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private Integer obtenerUserIdAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userService.obtenerUserIdPorUsername(username);
    }
}
