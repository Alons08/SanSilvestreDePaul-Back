package com.utp.service;

import com.utp.agregates.response.ApoderadoDashboardResponse;
import com.utp.agregates.response.FechaPagoResponse;
import com.utp.agregates.response.HorarioResponse;

import java.util.List;

public interface ApoderadoService {
    
    // Dashboard del apoderado con toda la información
    ApoderadoDashboardResponse obtenerDashboardApoderado(Integer userId);
    
    // Obtener fechas de pago pendientes
    List<FechaPagoResponse> obtenerFechasPagoPendientes(Integer userId);
    
    // Obtener todas las fechas de pago
    List<FechaPagoResponse> obtenerTodasLasFechasPago(Integer userId);
    
    // Obtener horarios de los alumnos
    List<HorarioResponse> obtenerHorariosAlumnos(Integer userId);
}
