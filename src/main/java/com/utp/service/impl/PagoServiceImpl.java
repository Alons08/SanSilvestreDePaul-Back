package com.utp.service.impl;

import com.utp.agregates.request.RegistrarPagoRequest;
import com.utp.entity.FechaPago;
import com.utp.repository.FechaPagoRepository;
import com.utp.service.PagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class PagoServiceImpl implements PagoService {

    private final FechaPagoRepository fechaPagoRepository;

    @Override
    public FechaPago marcarComoPagado(Long fechaPagoId, RegistrarPagoRequest request) {
        FechaPago fechaPago = fechaPagoRepository.findById(fechaPagoId)
            .orElseThrow(() -> new RuntimeException("Fecha de pago no encontrada"));
        
        // Verificar que no esté ya pagado
        if (fechaPago.getPagado()) {
            throw new RuntimeException("Esta cuota ya está marcada como pagada");
        }
        
        // Actualizar campos de pago
        fechaPago.setPagado(true);
        fechaPago.setFechaPago(request.getFechaPago() != null ? request.getFechaPago() : LocalDate.now());
        
        // Generar número de recibo automáticamente si no se proporciona
        String numeroRecibo = request.getNumeroRecibo();
        if (numeroRecibo == null || numeroRecibo.trim().isEmpty()) {
            numeroRecibo = generarNumeroReciboAutomatico();
        }
        fechaPago.setNumeroRecibo(numeroRecibo);
        
        fechaPago.setObservaciones(request.getObservaciones());
        
        return fechaPagoRepository.save(fechaPago);
    }

    @Override
    public FechaPago marcarComoNoPagado(Long fechaPagoId) {
        FechaPago fechaPago = fechaPagoRepository.findById(fechaPagoId)
            .orElseThrow(() -> new RuntimeException("Fecha de pago no encontrada"));
        
        // Verificar que esté pagado
        if (!fechaPago.getPagado()) {
            throw new RuntimeException("Esta cuota no está marcada como pagada");
        }
        
        // Revertir el pago
        fechaPago.setPagado(false);
        fechaPago.setFechaPago(null);
        fechaPago.setNumeroRecibo(null);
        fechaPago.setObservaciones(null);
        
        return fechaPagoRepository.save(fechaPago);
    }

    @Override
    @Transactional(readOnly = true)
    public FechaPago obtenerFechaPagoPorId(Long fechaPagoId) {
        return fechaPagoRepository.findById(fechaPagoId)
            .orElseThrow(() -> new RuntimeException("Fecha de pago no encontrada"));
    }
    
    /**
     * Genera un número de recibo automáticamente en formato: REC-YYYY-NNNNNN
     * @return Número de recibo generado
     */
    private String generarNumeroReciboAutomatico() {
        String año = String.valueOf(LocalDate.now().getYear());
        
        // Contar cuántos pagos se han realizado este año para generar secuencial
        long totalPagosEsteAño = fechaPagoRepository.findAll().stream()
            .filter(fp -> fp.getPagado() && fp.getFechaPago() != null)
            .filter(fp -> fp.getFechaPago().getYear() == LocalDate.now().getYear())
            .count();
        
        // Incrementar para el siguiente número secuencial
        long siguienteSecuencial = totalPagosEsteAño + 1;
        
        // Formato: REC-2025-000001
        return String.format("REC-%s-%06d", año, siguienteSecuencial);
    }
}
