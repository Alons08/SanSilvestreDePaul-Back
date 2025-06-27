# 💰 Sistema de Pagos - Documentación de API

## 🔐 Roles y Permisos

### **Administradores y Secretarias** (`/api/pagos/*`)
- ✅ Registrar pagos
- ✅ Revertir pagos
- ✅ Consultar todos los pagos
- ✅ Ver pagos pendientes/pagados

### **Apoderados** (`/api/apoderado/*`)
- ✅ Ver sus cuotas pendientes
- ✅ Ver sus cuotas pagadas
- ✅ Ver todas sus cuotas
- ❌ NO pueden registrar pagos

---

## 📋 Endpoints para Administradores/Secretarias

### **1. Registrar Pago**
```http
PUT /api/pagos/{fechaPagoId}/marcar-pagado
Authorization: Bearer <token>
Content-Type: application/json

{
    "fechaPago": "2025-06-26",
    "numeroRecibo": "REC-001-2025",
    "observaciones": "Pago en efectivo"
}
```

### **2. Revertir Pago**
```http
PUT /api/pagos/{fechaPagoId}/marcar-no-pagado
Authorization: Bearer <token>
```

### **3. Ver Pagos Pendientes**
```http
GET /api/pagos/pendientes
Authorization: Bearer <token>
```

### **4. Ver Pagos Realizados**
```http
GET /api/pagos/pagados
Authorization: Bearer <token>
```

### **5. Ver Pagos de una Matrícula**
```http
GET /api/pagos/matricula/{matriculaId}
Authorization: Bearer <token>
```

---

## 👨‍👩‍👧‍👦 Endpoints para Apoderados

### **1. Ver Cuotas Pendientes**
```http
GET /api/apoderado/fechas-pago/pendientes
Authorization: Bearer <token>
```

### **2. Ver Cuotas Pagadas**
```http
GET /api/apoderado/fechas-pago/pagadas
Authorization: Bearer <token>
```

### **3. Ver Todas las Cuotas**
```http
GET /api/apoderado/fechas-pago
Authorization: Bearer <token>
```

---

## 📊 Respuesta de FechaPago

```json
{
    "id": 1,
    "descripcion": "Cuota 6/2025",
    "fechaVencimiento": "2025-06-15",
    "monto": 150.00,
    "fechaPago": "2025-06-26",
    "pagado": true,
    "numeroRecibo": "REC-001-2025",
    "observaciones": "Pago en efectivo"
}
```

---

## 🔄 Flujo de Pagos

1. **Creación**: Las cuotas se crean automáticamente al matricular (`pagado: false`)
2. **Registro**: Admin/Secretaria registra el pago usando `PUT /api/pagos/{id}/marcar-pagado`
3. **Consulta**: Los apoderados pueden ver el estado actualizado
4. **Reversión**: Si hay error, se puede revertir con `PUT /api/pagos/{id}/marcar-no-pagado`

---

## ⚠️ Validaciones

- ✅ Solo cuotas no pagadas pueden marcarse como pagadas
- ✅ Solo cuotas pagadas pueden revertirse
- ✅ Fecha de pago es opcional (usa fecha actual si no se proporciona)
- ✅ Los apoderados solo ven sus propias cuotas
- ✅ Solo Admin/Secretaria pueden modificar estados de pago
