package com.utp.controller;

import com.utp.dto.request.RegistrarPagoRequest;
import com.utp.dto.response.FechaPagoResponse;
import com.utp.entity.FechaPago;
import com.utp.repository.FechaPagoRepository;
import com.utp.service.PagoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('Administrador', 'Secretaria')")
public class PagoController {

    private final PagoService pagoService;
    private final FechaPagoRepository fechaPagoRepository;

    @PutMapping("/{fechaPagoId}/marcar-pagado")
    public ResponseEntity<String> marcarComoPagado(
            @PathVariable Long fechaPagoId, 
            @Valid @RequestBody RegistrarPagoRequest request) {
        try {
            pagoService.marcarComoPagado(fechaPagoId, request);
            return ResponseEntity.ok("Pago registrado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{fechaPagoId}/marcar-no-pagado")
    public ResponseEntity<String> marcarComoNoPagado(@PathVariable Long fechaPagoId) {
        try {
            pagoService.marcarComoNoPagado(fechaPagoId);
            return ResponseEntity.ok("Pago revertido exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{fechaPagoId}")
    public ResponseEntity<FechaPagoResponse> obtenerFechaPago(@PathVariable Long fechaPagoId) {
        try {
            FechaPago fechaPago = pagoService.obtenerFechaPagoPorId(fechaPagoId);
            FechaPagoResponse response = convertirAFechaPagoResponse(fechaPago);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/pendientes")
    public ResponseEntity<List<FechaPago>> obtenerPagosPendientes() {
        try {
            List<FechaPago> pagosPendientes = fechaPagoRepository.findByPagado(false);
            return ResponseEntity.ok(pagosPendientes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/pagados")
    public ResponseEntity<List<FechaPago>> obtenerPagosPagados() {
        try {
            List<FechaPago> pagosPagados = fechaPagoRepository.findByPagado(true);
            return ResponseEntity.ok(pagosPagados);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/matricula/{matriculaId}")
    public ResponseEntity<List<FechaPago>> obtenerPagosPorMatricula(@PathVariable Long matriculaId) {
        try {
            List<FechaPago> pagos = fechaPagoRepository.findByMatriculaId(matriculaId);
            return ResponseEntity.ok(pagos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private FechaPagoResponse convertirAFechaPagoResponse(FechaPago fechaPago) {
        return FechaPagoResponse.builder()
            .id(fechaPago.getId())
            .descripcion(fechaPago.getDescripcion())
            .fechaVencimiento(fechaPago.getFechaVencimiento())
            .monto(fechaPago.getMonto())
            .fechaPago(fechaPago.getFechaPago())
            .pagado(fechaPago.getPagado())
            .numeroRecibo(fechaPago.getNumeroRecibo())
            .observaciones(fechaPago.getObservaciones())
            .build();
    }
}
