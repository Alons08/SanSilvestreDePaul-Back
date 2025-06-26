package com.utp.controller;

import com.utp.agregates.request.MatriculaRequest;
import com.utp.agregates.response.MatriculaResponse;
import com.utp.entity.EstadoMatricula;
import com.utp.service.MatriculaService;
import com.utp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.List;

@RestController
@RequestMapping("/api/matriculas")
@RequiredArgsConstructor
public class MatriculaController {

    private final MatriculaService matriculaService;
    private final UserService userService;

    @PostMapping("/nueva")
    @PreAuthorize("hasAnyAuthority('Administrador', 'Secretaria')")
    public ResponseEntity<MatriculaResponse> crearMatriculaNueva(@Valid @RequestBody MatriculaRequest request) {
        try {
            Integer userId = obtenerUserIdAutenticado();
            MatriculaResponse response = matriculaService.crearMatriculaNueva(request, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/ratificacion")
    @PreAuthorize("hasAuthority('Apoderado')")
    public ResponseEntity<MatriculaResponse> crearMatriculaRatificacion(@Valid @RequestBody MatriculaRequest request) {
        try {
            Integer userId = obtenerUserIdAutenticado();
            MatriculaResponse response = matriculaService.crearMatriculaRatificacion(request, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('Administrador', 'Secretaria') or @userService.esMatriculaDelApoderado(#id, authentication.name)")
    public ResponseEntity<MatriculaResponse> obtenerMatricula(@PathVariable Long id) {
        try {
            MatriculaResponse response = matriculaService.obtenerMatriculaPorId(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/mis-matriculas")
    @PreAuthorize("hasAuthority('Apoderado')")
    public ResponseEntity<List<MatriculaResponse>> obtenerMisMatriculas() {
        try {
            Integer userId = obtenerUserIdAutenticado();
            List<MatriculaResponse> response = matriculaService.listarMatriculasPorApoderado(userId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('Administrador', 'Secretaria')")
    public ResponseEntity<List<MatriculaResponse>> listarTodasLasMatriculas() {
        try {
            List<MatriculaResponse> response = matriculaService.listarTodasLasMatriculas();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/estado")
    @PreAuthorize("hasAnyAuthority('Administrador', 'Secretaria')")
    public ResponseEntity<MatriculaResponse> actualizarEstado(
            @PathVariable Long id, 
            @RequestParam EstadoMatricula estado) {
        try {
            MatriculaResponse response = matriculaService.actualizarEstadoMatricula(id, estado);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/estado/{estado}")
    @PreAuthorize("hasAnyAuthority('Administrador', 'Secretaria')")
    public ResponseEntity<List<MatriculaResponse>> obtenerPorEstado(@PathVariable EstadoMatricula estado) {
        try {
            List<MatriculaResponse> response = matriculaService.obtenerMatriculasPorEstado(estado);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/ano/{ano}")
    @PreAuthorize("hasAnyAuthority('Administrador', 'Secretaria')")
    public ResponseEntity<List<MatriculaResponse>> obtenerPorAno(@PathVariable int ano) {
        try {
            Year anoEscolar = Year.of(ano);
            List<MatriculaResponse> response = matriculaService.obtenerMatriculasPorAnoEscolar(anoEscolar);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('Administrador', 'Secretaria') or @userService.esMatriculaDelApoderado(#id, authentication.name)")
    public ResponseEntity<Void> eliminarMatricula(@PathVariable Long id) {
        try {
            Integer userId = obtenerUserIdAutenticado();
            matriculaService.eliminarMatricula(id, userId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/puede-matricular/{alumnoId}/{ano}")
    @PreAuthorize("hasAnyAuthority('Administrador', 'Secretaria', 'Apoderado')")
    public ResponseEntity<Boolean> puedeMatricular(@PathVariable Long alumnoId, @PathVariable int ano) {
        try {
            Year anoEscolar = Year.of(ano);
            boolean puede = matriculaService.puedeMatricular(alumnoId, anoEscolar);
            return ResponseEntity.ok(puede);
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
