package com.utp.service;

import com.utp.agregates.request.RegistrarPagoRequest;
import com.utp.entity.FechaPago;

public interface PagoService {
    
    // Marcar una fecha de pago como pagada
    FechaPago marcarComoPagado(Long fechaPagoId, RegistrarPagoRequest request);
    
    // Marcar una fecha de pago como no pagada (reversar pago)
    FechaPago marcarComoNoPagado(Long fechaPagoId);
    
    // Obtener fecha de pago por ID
    FechaPago obtenerFechaPagoPorId(Long fechaPagoId);
}
