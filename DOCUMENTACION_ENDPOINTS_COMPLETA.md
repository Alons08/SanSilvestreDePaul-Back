# 📋 GUÍA COMPLETA DE ENDPOINTS - SISTEMA ESCOLAR SAN SILVESTRE DE PAÚL

**Base URL:** `http://localhost:8025`

---

## 📋 ÍNDICE DE NAVEGACIÓN

1. [🔐 AUTENTICACIÓN](#-1-autenticación-auth---sin-token)
2. [👥 GESTIÓN DE PERSONAL ADMINISTRATIVO](#-2-gestión-de-personal-administrativo-apipersonal)
3. [🏗️ CREAR ENTIDADES DE CONFIGURACIÓN ESCOLAR](#️-3-crear-entidades-de-configuración-escolar-apisetup)
4. [�‍👩‍👧‍👦 GESTIÓN DE APODERADOS](#‍‍‍-4-gestión-de-apoderados-apisetup)
5. [� CONSULTAS Y GESTIÓN DE ENTIDADES](#-5-consultas-y-gestión-de-entidades-apisetup)
6. [🎓 MATRÍCULAS](#-6-matrículas-apimatrículas)
7. [💰 GESTIÓN DE PAGOS](#-7-gestión-de-pagos-apipagos)
8. [🔍 CONSULTAS AVANZADAS DE ALUMNOS Y CUOTAS](#-8-consultas-avanzadas-de-alumnos-y-cuotas-apiadmin)
9. [🔄 GESTIÓN DE ESTADOS](#-9-gestión-de-estados-apiestados)
10. [👨‍👩‍👧‍👦 PORTAL APODERADOS](#‍‍‍-10-portal-apoderados-apiapoderado)
11. [📝 DATOS DE PRUEBA PARA DESARROLLO](#-11-datos-de-prueba-para-desarrollo-apiadmin)

---

## 🔐 1. AUTENTICACIÓN (`/auth/`) - SIN TOKEN

### LOGIN
```http
POST http://localhost:8025/auth/login
Content-Type: application/json
🔓 ROL: PÚBLICO (Sin autenticación)

{
    "username": "Alonso",
    "password": "alonso123"
}
```

### FORGOT PASSWORD
```http
POST http://localhost:8025/auth/forgot-password
Content-Type: application/json
🔓 ROL: PÚBLICO (Sin autenticación)
✅ COMPATIBLE: Todos los roles (Apoderado, Administrador, Secretaria)

{
    "email": "admin@sansilvestro.edu.pe"
}

# ✅ RESPUESTAS:
# - Email válido: "Se ha enviado un email con las instrucciones para restablecer tu contraseña"
# - Email inválido: "El email no está registrado en el sistema"
```

### RESET PASSWORD
```http
POST http://localhost:8025/auth/reset-password
Content-Type: application/json
🔓 ROL: PÚBLICO (Sin autenticación)
✅ COMPATIBLE: Todos los roles (Apoderado, Administrador, Secretaria)

{
    "token": "token_recibido_por_email",
    "newPassword": "nueva_contraseña",
    "confirmPassword": "nueva_contraseña"
}
```

---

## 👥 2. GESTIÓN DE PERSONAL ADMINISTRATIVO (`/api/personal/`)

### LISTAR TODO EL PERSONAL
```http
GET http://localhost:8025/api/personal
Authorization: Bearer tu_jwt_token_admin
👑 ROL: Administrador únicamente
```

### OBTENER PERSONAL POR ID
```http
GET http://localhost:8025/api/personal/1
Authorization: Bearer tu_jwt_token_admin
👑 ROL: Administrador únicamente
```

### CREAR NUEVO PERSONAL
```http
POST http://localhost:8025/api/personal
Authorization: Bearer tu_jwt_token_admin
👑 ROL: Administrador únicamente
Content-Type: application/json

{
    "tipoDocumento": "DNI",
    "numeroDocumento": "12345678",
    "username": "secretaria_nueva",
    "password": "secretaria123",
    "role": "Secretaria",
    "nombre": "María",
    "apellido": "García",
    "email": "maria.garcia@sansilvestro.edu.pe"
}

# ✅ NOTA: Al crear personal se genera automáticamente:
# - Usuario en el sistema con las credenciales proporcionadas
# - Registro de personal vinculado al usuario
# - Documento de identidad asociado
# - Estado activo por defecto
```

### ACTUALIZAR PERSONAL
```http
PUT http://localhost:8025/api/personal/1
Authorization: Bearer tu_jwt_token_admin
👑 ROL: Administrador únicamente
Content-Type: application/json

{
    "tipoDocumento": "DNI",
    "numeroDocumento": "12345678",
    "username": "secretaria_actualizada",
    "password": "nueva_contraseña",  // ⚠️ OPCIONAL: Solo si quieres cambiar la contraseña
    "role": "Secretaria",
    "nombre": "María",
    "apellido": "García Actualizada",
    "email": "maria.actualizada@sansilvestro.edu.pe"
}

# ✅ NOTAS:
# - El campo password es opcional
# - Si no envías password, se mantiene la contraseña actual
# - Se valida que el email no esté en uso por otro personal
```

### CAMBIAR ESTADO DEL PERSONAL
```http
PUT http://localhost:8025/api/personal/1/estado?estado=false
Authorization: Bearer tu_jwt_token_admin
👑 ROL: Administrador únicamente

# ✅ EFECTO:
# - Cambia el estado del personal (activo/inactivo)
# - También actualiza el estado del usuario asociado
# - Si estado=false, el usuario NO podrá hacer login
# - Si estado=true, el usuario SÍ podrá hacer login
```

### ELIMINAR PERSONAL
```http
DELETE http://localhost:8025/api/personal/1
Authorization: Bearer tu_jwt_token_admin
👑 ROL: Administrador únicamente

# ⚠️ ADVERTENCIA: Elimina permanentemente:
# - El registro de personal
# - El usuario asociado
# - El documento de identidad
# - Esta acción NO es reversible
```

---

## 🏗️ 3. CREAR ENTIDADES DE CONFIGURACIÓN ESCOLAR (`/api/setup/`)

### CREAR DOCENTE
```http
POST http://localhost:8025/api/setup/docentes
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
Content-Type: application/json

{
    "nombre": "María",
    "apellido": "González",
    "direccion": "Av. Educación 123",
    "departamento": "Lima",
    "provincia": "Lima",
    "distrito": "San Isidro",
    "telefono": "987654321",
    "email": "maria.gonzalez@colegio.edu.pe",
    "documentoIdentidad": {
        "tipoDocumento": "DNI",
        "numeroDocumento": "12345678"
    }
}
```

### CREAR GRADO
```http
POST http://localhost:8025/api/setup/grados
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
Content-Type: application/json

{
    "nombreGrado": "1er Grado",
    "nivel": "Primaria"
}
```

### CREAR CURSO
```http
POST http://localhost:8025/api/setup/cursos
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
Content-Type: application/json

{
    "nombre": "Matemática",
    "descripcion": "Curso de matemática básica"
}
```

### CREAR GRADO-CURSO
```http
POST http://localhost:8025/api/setup/grado-cursos
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
Content-Type: application/json

{
    "gradoId": 1,
    "cursoId": 1,
    "horasSemanales": 6
}
```

### CREAR AULA
```http
POST http://localhost:8025/api/setup/aulas
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
Content-Type: application/json

{
    "nombre": "Aula A1",
    "gradoId": 1,
    "docenteId": 1,
    "capacidad": 25,
    "horarioInicio": "08:00:00",
    "horarioFin": "13:00:00"
}
```

---

## 👨‍👩‍👧‍👦 4. GESTIÓN DE APODERADOS (`/api/setup/`)

### CREAR APODERADO - **FORMATO UNIFICADO** ⭐
```http
POST http://localhost:8025/api/setup/apoderados
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
Content-Type: application/json

{
    "tipoDocumento": "DNI",
    "numeroDocumento": "87654321",
    "username": "carlos_mendoza",
    "password": "carlos123",
    "nombre": "Carlos",
    "apellido": "Mendoza",
    "parentesco": "Padre",
    "direccion": "Av. Los Padres 456",
    "departamento": "Lima",
    "provincia": "Lima",
    "distrito": "Miraflores",
    "telefono": "998877665",
    "email": "carlos.mendoza@email.com",
    "lugarTrabajo": "Empresa ABC",
    "cargo": "Gerente"
}

# ✅ FORMATO UNIFICADO: Igual que la creación de Personal
# ✅ UN SOLO PASO: No necesitas crear usuario primero
# ✅ AUTOMÁTICO: Se genera todo lo necesario:
# - Usuario en el sistema con las credenciales proporcionadas
# - Registro de apoderado vinculado al usuario
# - Documento de identidad asociado
# - Estado activo por defecto
# - Rol Apoderado asignado automáticamente
```

### CREAR ALUMNO
```http
POST http://localhost:8025/api/setup/alumnos
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
Content-Type: application/json

{
    "tipoDocumento": "DNI",
    "numeroDocumento": "11223344",
    "apoderadoId": 1,
    "nombre": "Ana",
    "apellido": "Mendoza",
    "fechaNacimiento": "2017-03-15",
    "genero": "Femenino",
    "direccion": "Av. Los Padres 456",
    "departamento": "Lima",
    "provincia": "Lima",
    "distrito": "Miraflores",
    "tieneDiscapacidad": false,
    "diagnosticoMedico": null
}

# ✅ FORMATO UNIFICADO: Campos directos sin objetos anidados
# ✅ SIMPLIFICADO: tipoDocumento y numeroDocumento como campos simples
```

---

## 🔍 5. CONSULTAS Y GESTIÓN DE ENTIDADES (`/api/setup/`)

### LISTAR APODERADOS
```http
GET http://localhost:8025/api/setup/apoderados
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
```

### LISTAR DOCENTES
```http
GET http://localhost:8025/api/setup/docentes
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
```

### LISTAR GRADOS
```http
GET http://localhost:8025/api/setup/grados
Authorization: Bearer tu_jwt_token
👥 ROLES: Administrador, Secretaria, Apoderado
```

### LISTAR ALUMNOS
```http
GET http://localhost:8025/api/setup/alumnos
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
```

### LISTAR AULAS
```http
GET http://localhost:8025/api/setup/aulas
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
```

### LISTAR CURSOS
```http
GET http://localhost:8025/api/setup/cursos
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
```

### LISTAR GRADO-CURSOS
```http
GET http://localhost:8025/api/setup/grado-cursos
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
```

### ACTUALIZAR APODERADO
```http
PUT http://localhost:8025/api/admin/apoderados/1
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
Content-Type: application/json

{
    "tipoDocumento": "DNI",
    "numeroDocumento": "87654321",
    "username": "carlos_actualizado",
    "password": "nueva_contraseña",  // ⚠️ OPCIONAL: Solo si quieres cambiar credenciales
    "nombre": "Carlos Actualizado",
    "apellido": "Mendoza",
    "parentesco": "Padre",
    "direccion": "Nueva Dirección 789",
    "departamento": "Lima",
    "provincia": "Lima",
    "distrito": "San Borja",
    "telefono": "999888777",
    "email": "carlos.nuevo@email.com",
    "lugarTrabajo": "Nueva Empresa",
    "cargo": "Director"
}

# ✅ FORMATO UNIFICADO: Campos directos, sin objetos anidados
# ✅ SIMPLIFICADO: tipoDocumento y numeroDocumento como campos simples
# ⚠️ NOTA: Los campos username y password son para referencia pero no se usan en actualización
```
```

### ACTUALIZAR DOCENTE
```http
PUT http://localhost:8025/api/admin/docentes/1
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
Content-Type: application/json

{
    "nombre": "María Actualizada",
    "apellido": "González",
    "direccion": "Nueva Dirección Docente 123",
    "departamento": "Lima",
    "provincia": "Lima",
    "distrito": "Miraflores",
    "telefono": "987123456",
    "email": "maria.nueva@colegio.edu.pe",
    "documentoIdentidad": {
        "tipoDocumento": "DNI",
        "numeroDocumento": "12345678"
    }
}
```

### ACTUALIZAR ALUMNO
```http
PUT http://localhost:8025/api/admin/alumnos/1
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
Content-Type: application/json

{
    "tipoDocumento": "DNI",
    "numeroDocumento": "11223344",
    "nombre": "Ana Actualizada",
    "apellido": "Mendoza",
    "fechaNacimiento": "2017-03-15",
    "genero": "Femenino",
    "direccion": "Nueva Dirección Alumno",
    "departamento": "Lima",
    "provincia": "Lima",
    "distrito": "San Isidro",
    "tieneDiscapacidad": false,
    "diagnosticoMedico": null
}

# ✅ FORMATO UNIFICADO: Campos directos sin objetos anidados
# ✅ SIMPLIFICADO: tipoDocumento y numeroDocumento como campos simples
```
```

### ACTUALIZAR GRADO
```http
PUT http://localhost:8025/api/admin/grados/1
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
Content-Type: application/json

{
    "nombreGrado": "1er Grado Actualizado",
    "nivel": "Primaria"
}
```

### ELIMINAR GRADO 
```http
DELETE http://localhost:8025/api/admin/grados/1
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
```

### ACTUALIZAR AULA
```http
PUT http://localhost:8025/api/admin/aulas/1
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
Content-Type: application/json

{
    "nombre": "Aula A1 Actualizada",
    "gradoId": 1,
    "docenteId": 1,
    "capacidad": 30,
    "horarioInicio": "07:30:00",
    "horarioFin": "13:30:00"
}
```

### ELIMINAR AULA
```http
DELETE http://localhost:8025/api/admin/aulas/1
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
```

### ACTUALIZAR CURSO
```http
PUT http://localhost:8025/api/admin/cursos/1
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
Content-Type: application/json

{
    "nombre": "Matemática Avanzada",
    "descripcion": "Curso de matemática avanzada actualizado"
}
```

### ELIMINAR CURSO
```http
DELETE http://localhost:8025/api/admin/cursos/1
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
```

### ACTUALIZAR GRADO-CURSO
```http
PUT http://localhost:8025/api/admin/grado-cursos/1
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
Content-Type: application/json

{
    "gradoId": 1,
    "cursoId": 1,
    "horasSemanales": 8
}
```

### ELIMINAR GRADO-CURSO
```http
DELETE http://localhost:8025/api/admin/grado-cursos/1
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
```

---

## 🎓 6. MATRÍCULAS (`/api/matriculas/`)

### CREAR MATRÍCULA NUEVA
```http
POST http://localhost:8025/api/matriculas/nueva
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
Content-Type: application/json

{
    "alumnoId": 1,
    "gradoId": 1,
    "anoEscolar": 2025,
    "tipoMatricula": "Nueva",
    "observaciones": "Matrícula nueva para el año 2025"
}
```

### CREAR MATRÍCULA RATIFICACIÓN
```http
POST http://localhost:8025/api/matriculas/ratificacion
Authorization: Bearer tu_jwt_token_apoderado
👤 ROL: Apoderado únicamente
Content-Type: application/json

{
    "alumnoId": 1,
    "gradoId": 2,
    "anoEscolar": 2025,
    "tipoMatricula": "Ratificacion",
    "observaciones": "Ratificación para siguiente grado"
}
```

### OBTENER MATRÍCULA POR ID
```http
GET http://localhost:8025/api/matriculas/1
Authorization: Bearer tu_jwt_token
👥 ROLES: Administrador, Secretaria, Apoderado (solo sus propias matrículas)
```

### LISTAR TODAS LAS MATRÍCULAS
```http
GET http://localhost:8025/api/matriculas
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
```

### ACTUALIZAR ESTADO DE MATRÍCULA
```http
PUT http://localhost:8025/api/matriculas/1/estado?estado=Completada
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
```

### OBTENER MATRÍCULAS POR ESTADO
```http
GET http://localhost:8025/api/matriculas/estado/Completada
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
```

### OBTENER MATRÍCULAS POR AÑO
```http
GET http://localhost:8025/api/matriculas/ano/2025
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
```

### ELIMINAR MATRÍCULA
```http
DELETE http://localhost:8025/api/matriculas/1
Authorization: Bearer tu_jwt_token
👥 ROLES: Administrador, Secretaria, Apoderado (solo sus propias matrículas)
```

### VERIFICAR SI PUEDE MATRICULAR
```http
GET http://localhost:8025/api/matriculas/puede-matricular/1/2025
Authorization: Bearer tu_jwt_token
👥 ROLES: Administrador, Secretaria, Apoderado
```

---

## 💰 7. GESTIÓN DE PAGOS (`/api/pagos/`)

### MARCAR CUOTA COMO PAGADA
```http
PUT http://localhost:8025/api/pagos/1/marcar-pagado
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
Content-Type: application/json

{
    "numeroRecibo": "REC-001-2025",  // ⚠️ OPCIONAL: Si no se envía, se genera automáticamente
    "observaciones": "Pago realizado en efectivo"
}

# ✅ NOTA: El numeroRecibo se genera automáticamente en formato REC-YYYY-NNNNNN
# Si no envías numeroRecibo, el sistema genera: REC-2025-000001, REC-2025-000002, etc.
# Si envías numeroRecibo, usará el valor que proporciones (ej: recibo físico)
```

### REVERTIR PAGO DE CUOTA
```http
PUT http://localhost:8025/api/pagos/1/marcar-no-pagado
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
Content-Type: application/json

{
    "observaciones": "Pago revertido por error en el sistema"
}
```

### OBTENER DETALLE DE PAGO
```http
GET http://localhost:8025/api/pagos/1
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
```

### VER TODAS LAS CUOTAS PENDIENTES
```http
GET http://localhost:8025/api/pagos/pendientes
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
```

### VER TODAS LAS CUOTAS PAGADAS
```http
GET http://localhost:8025/api/pagos/pagados
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
```

### VER PAGOS POR MATRÍCULA
```http
GET http://localhost:8025/api/pagos/matricula/1
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
```

---

## 🔍 8. CONSULTAS AVANZADAS DE ALUMNOS Y CUOTAS (`/api/admin/`)

### BUSCAR ALUMNOS - **NUEVO**
```http
GET http://localhost:8025/api/admin/alumnos/buscar?nombre=Juan&apellido=Perez
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria

# Parámetros opcionales:
# - nombre: Busca por nombre o apellido (cualquiera de los dos)
# - apellido: Busca por apellido específico
# - Si no se envían parámetros, devuelve todos los alumnos

# Ejemplos:
# /api/admin/alumnos/buscar?nombre=Juan
# /api/admin/alumnos/buscar?nombre=Juan&apellido=Perez
# /api/admin/alumnos/buscar
```

### OBTENER CUOTAS DE UN ALUMNO - **NUEVO**
```http
GET http://localhost:8025/api/admin/alumnos/1/cuotas?tipo=pendientes
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria

# Parámetros:
# - tipo: "todas" (por defecto) o "pendientes"

# Ejemplos:
# /api/admin/alumnos/1/cuotas (todas las cuotas)
# /api/admin/alumnos/1/cuotas?tipo=pendientes (solo pendientes)
```

### BUSCAR CUOTAS POR DESCRIPCIÓN - **NUEVO**
```http
GET http://localhost:8025/api/admin/alumnos/1/cuotas/buscar?descripcion=Marzo
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria

# Busca cuotas que contengan el texto en la descripción
# Ejemplo: "Marzo" encontrará "Mensualidad Marzo 2025"
```

### RESUMEN DE PAGOS DEL ALUMNO - **NUEVO**
```http
GET http://localhost:8025/api/admin/alumnos/1/resumen-pagos
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria

# Respuesta incluye:
# - Total de cuotas
# - Cuotas pagadas
# - Cuotas pendientes
# - Las próximas 3 cuotas pendientes
```

### BÚSQUEDA GLOBAL ALUMNO + CUOTAS - **NUEVO**
```http
GET http://localhost:8025/api/admin/busqueda-global?termino=Juan
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria

# Búsqueda combinada que devuelve:
# - Alumnos que coincidan con el término
# - Resumen de cuotas pendientes para cada alumno encontrado
# - Ideal para búsquedas rápidas desde un solo endpoint
```

---

## 🔄 9. GESTIÓN DE ESTADOS (`/api/estados/`)

### CAMBIAR ESTADO APODERADO
```http
PUT http://localhost:8025/api/estados/apoderados/1?estado=false
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
```

### CAMBIAR ESTADO ALUMNO
```http
PUT http://localhost:8025/api/estados/alumnos/1?estado=true
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
```

### CAMBIAR ESTADO DOCENTE
```http
PUT http://localhost:8025/api/estados/docentes/1?estado=false
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
```

### REASIGNAR APODERADO A ALUMNO
```http
PUT http://localhost:8025/api/estados/alumnos/1/reasignar-apoderado/2
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
```

### LISTAR APODERADOS POR ESTADO
```http
GET http://localhost:8025/api/estados/apoderados?estado=false
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
```

### LISTAR ALUMNOS POR ESTADO
```http
GET http://localhost:8025/api/estados/alumnos?estado=true
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
```

### LISTAR DOCENTES POR ESTADO
```http
GET http://localhost:8025/api/estados/docentes?estado=true
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
```

### LISTAR ALUMNOS SIN APODERADO ACTIVO
```http
GET http://localhost:8025/api/estados/alumnos/sin-apoderado-activo
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
```

### LISTAR APODERADOS ACTIVOS
```http
GET http://localhost:8025/api/estados/apoderados/activos
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
```

## 👨‍👩‍👧‍👦 10. PORTAL APODERADOS (`/api/apoderado/`)

### DASHBOARD DEL APODERADO
```http
GET http://localhost:8025/api/apoderado/dashboard
Authorization: Bearer tu_jwt_token_apoderado
👤 ROL: Apoderado únicamente
```

### VER MIS CUOTAS PENDIENTES
```http
GET http://localhost:8025/api/apoderado/fechas-pago/pendientes
Authorization: Bearer tu_jwt_token_apoderado
👤 ROL: Apoderado únicamente
```

### VER TODAS MIS CUOTAS (PAGADAS Y PENDIENTES)
```http
GET http://localhost:8025/api/apoderado/fechas-pago
Authorization: Bearer tu_jwt_token_apoderado
👤 ROL: Apoderado únicamente
```

### VER MIS CUOTAS PAGADAS (SOLO PAGADAS)
```http
GET http://localhost:8025/api/apoderado/fechas-pago/pagadas
Authorization: Bearer tu_jwt_token_apoderado
👤 ROL: Apoderado únicamente
```

### VER HORARIOS DE MIS HIJOS
```http
GET http://localhost:8025/api/apoderado/horarios
Authorization: Bearer tu_jwt_token_apoderado
👤 ROL: Apoderado únicamente
```

### VER CURSOS DE MIS HIJOS - **NUEVO**
```http
GET http://localhost:8025/api/apoderado/cursos
Authorization: Bearer tu_jwt_token_apoderado
👤 ROL: Apoderado únicamente

# Respuesta incluye:
# - Lista de todos los cursos de cada hijo
# - Información del curso (nombre, descripción, horas semanales)
# - Información del grado y nivel educativo
# - Nombre del alumno para identificar a cuál hijo pertenece cada curso
```

### VER MIS MATRÍCULAS
```http
GET http://localhost:8025/api/matriculas/mis-matriculas
Authorization: Bearer tu_jwt_token_apoderado
👤 ROL: Apoderado únicamente
```

---

## � COMPARACIÓN DE FORMATOS - PERSONAL vs APODERADO

### **FORMATO UNIFICADO** ✅
Tanto el **Personal** como los **Apoderados** ahora siguen el mismo formato simple:

#### **Crear Personal:**
```http
POST http://localhost:8025/api/personal
{
    "tipoDocumento": "DNI",
    "numeroDocumento": "12345678",
    "username": "secretaria_nueva",
    "password": "secretaria123",
    "role": "Secretaria",
    "nombre": "María",
    "apellido": "García",
    "email": "maria.garcia@sansilvestro.edu.pe"
}
```

#### **Crear Apoderado (Método Unificado):**
```http
POST http://localhost:8025/api/setup/apoderados
{
    "tipoDocumento": "DNI",
    "numeroDocumento": "87654321",
    "username": "carlos_mendoza",
    "password": "carlos123",
    "nombre": "Carlos",
    "apellido": "Mendoza",
    "parentesco": "Padre",        // ← Campos adicionales específicos del apoderado
    "direccion": "Av. Los Padres 456",
    "departamento": "Lima",
    "provincia": "Lima",
    "distrito": "Miraflores",
    "telefono": "998877665",
    "email": "carlos.mendoza@email.com",
    "lugarTrabajo": "Empresa ABC",
    "cargo": "Gerente"
}
```

### **VENTAJAS DEL FORMATO UNIFICADO:**
- ✅ **Un solo paso:** No necesitas crear usuario primero
- ✅ **Consistencia:** Ambos formatos son similares
- ✅ **Simplicidad:** Menos endpoints que recordar
- ✅ **Automático:** Todo se crea en una sola operación
- ✅ **Validaciones:** Se verifican duplicados automáticamente

---

## �🔑 LEYENDA DE ROLES Y TOKENS

### 🔓 PÚBLICO
Sin token necesario

### 👑 ADMINISTRADOR
Token de usuario con role="Administrador"

### 🏢 SECRETARIA
Token de usuario con role="Secretaria"

### 👤 APODERADO
Token de usuario con role="Apoderado"

### 👥 MÚLTIPLES
Acepta varios roles según se especifique

---------------------------------------------------------------------------------------------------

## 🚀 CÓMO OBTENER TOKENS PARA CADA ROL

### 1. Token de Administrador
```http
POST http://localhost:8025/auth/login
Content-Type: application/json

{
    "username": "Alonso",
    "password": "alonso123"
}

# ✅ DATOS COMPLETOS DEL ADMINISTRADOR:
# Usuario: Alonso / alonso123
# Email: admin@sansilvestro.edu.pe (para recuperar contraseña)
# Documento: DNI 12345678
# Personal: Alonso Administrador
```

### 2. Token de Secretaria
```http
POST http://localhost:8025/auth/login
Content-Type: application/json

{
    "username": "Jose",
    "password": "jose123"
}

# ✅ DATOS COMPLETOS DE LA SECRETARIA:
# Usuario: Jose / jose123
# Email: secretaria@sansilvestro.edu.pe (para recuperar contraseña)
# Documento: DNI 87654321
# Personal: José Secretario
```

### 3. Token de Apoderado
```http
POST http://localhost:8025/auth/login
Content-Type: application/json

{
    "username": "apoderado_demo",
    "password": "123456"
}
```

### 4. Crear Nueva Secretaria (Opcional)
**Si necesitas crear personal adicional:**
```http
POST http://localhost:8025/api/personal
Authorization: Bearer tu_jwt_token_admin
Content-Type: application/json

{
    "tipoDocumento": "DNI",
    "numeroDocumento": "98765432",
    "username": "secretaria_nueva",
    "password": "secretaria123",
    "role": "Secretaria",
    "nombre": "Ana",
    "apellido": "López",
    "email": "ana.lopez@sansilvestro.edu.pe"
}
```
```

---

## 📝 VALORES VÁLIDOS PARA CAMPOS

### Estados de Matrícula
- `Pendiente`
- `Completada`
- `Cancelada`

### Tipos de Matrícula
- `Nueva`
- `Ratificacion`

### Tipos de Documento
- `DNI`
- `Pasaporte`
- `CarnetExtranjeria`

### Niveles Educativos
- `Inicial`
- `Primaria`
- `Secundaria`

### Géneros
- `Masculino`
- `Femenino`

### Parentescos
- `Padre`
- `Madre`
- `Tutor`
- `Abuelo`
- `Abuela`
- `Hermano`
- `Hermana`
- `Tio`
- `Tia`

---

## 📁 ORGANIZACIÓN SUGERIDA DE CARPETAS PARA POSTMAN

```
📂 SanSilvestre-API-Endpoints/
├── 📁 01-Autenticacion/
├── 📁 02-Gestion-Personal-Administrativo/
├── 📁 03-Configuracion-Inicial/
├── 📁 04-Consultas-Listados/
├── 📁 05-Administracion/
├── 📁 06-Datos-Prueba/
├── 📁 07-Gestion-Estados/
├── 📁 08-Matriculas/
├── 📁 09-Gestion-Pagos/
├── 📁 10-Consultas-Avanzadas/
└── 📁 11-Portal-Apoderados/
```



------------------------------------------------------------------------------------------------------------

## 🎯 FLUJO COMPLETO PARA CREAR UNA MATRÍCULA DESDE CERO

### **PASO 1: AUTENTICACIÓN**
```http
POST http://localhost:8025/auth/login
Content-Type: application/json

{
    "username": "Alonso",
    "password": "alonso123"
}
```
**⚠️ Importante:** Guarda el token JWT que recibes para los siguientes pasos.

---

### **PASO 2: CREAR ENTIDADES BÁSICAS (Si no existen)**

#### **2.1 Crear Grado**
```http
POST http://localhost:8025/api/setup/grados
Authorization: Bearer tu_jwt_token_admin_o_secretaria
Content-Type: application/json

{
    "nombreGrado": "1er Grado",
    "nivel": "Primaria"
}
```

#### **2.2 Crear Docente**
```http
POST http://localhost:8025/api/setup/docentes
Authorization: Bearer tu_jwt_token_admin_o_secretaria
Content-Type: application/json

{
    "nombre": "María",
    "apellido": "González",
    "direccion": "Av. Educación 123",
    "departamento": "Lima",
    "provincia": "Lima",
    "distrito": "San Isidro",
    "telefono": "987654321",
    "email": "maria.gonzalez@colegio.edu.pe",
    "documentoIdentidad": {
        "tipoDocumento": "DNI",
        "numeroDocumento": "12345678"
    }
}
```

#### **2.3 Crear Aula**
```http
POST http://localhost:8025/api/setup/aulas
Authorization: Bearer tu_jwt_token_admin_o_secretaria
Content-Type: application/json

{
    "nombre": "Aula A1",
    "gradoId": 1,
    "docenteId": 1,
    "capacidad": 25,
    "horarioInicio": "08:00:00",
    "horarioFin": "13:00:00"
}
```

---

### **PASO 3: CREAR APODERADO (MÉTODO SIMPLIFICADO)**

```http
POST http://localhost:8025/api/setup/apoderados
Authorization: Bearer tu_jwt_token_admin_o_secretaria
Content-Type: application/json

{
    "tipoDocumento": "DNI",
    "numeroDocumento": "87654321",
    "username": "carlos_mendoza",
    "password": "carlos123",
    "nombre": "Carlos",
    "apellido": "Mendoza",
    "parentesco": "Padre",
    "direccion": "Av. Los Padres 456",
    "departamento": "Lima",
    "provincia": "Lima",
    "distrito": "Miraflores",
    "telefono": "998877665",
    "email": "carlos.mendoza@email.com",
    "lugarTrabajo": "Empresa ABC",
    "cargo": "Gerente"
}

# ✅ VENTAJAS DEL MÉTODO COMPLETO:
# - ✅ UN SOLO ENDPOINT (en lugar de 2)
# - ✅ Crea automáticamente el usuario Y el apoderado
# - ✅ Rol Apoderado asignado automáticamente
# - ✅ Estados activos por defecto
# - ✅ Mismo formato que Personal administrativo
```

---

### **PASO 4: CREAR ALUMNO**
```http
POST http://localhost:8025/api/setup/alumnos
Authorization: Bearer tu_jwt_token_admin_o_secretaria
Content-Type: application/json

{
    "tipoDocumento": "DNI",
    "numeroDocumento": "11223344",
    "apoderadoId": 1,
    "nombre": "Ana",
    "apellido": "Mendoza",
    "fechaNacimiento": "2017-03-15",
    "genero": "Femenino",
    "direccion": "Av. Los Padres 456",
    "departamento": "Lima",
    "provincia": "Lima",
    "distrito": "Miraflores",
    "tieneDiscapacidad": false,
    "diagnosticoMedico": null
}

# ✅ FORMATO UNIFICADO: También usa campos directos como Apoderado y Personal
```
```

---

### **PASO 5: CREAR MATRÍCULA**
```http
POST http://localhost:8025/api/matriculas/nueva
Authorization: Bearer tu_jwt_token_admin_o_secretaria
Content-Type: application/json

{
    "alumnoId": 1,
    "gradoId": 1,
    "anoEscolar": 2025,
    "tipoMatricula": "Nueva",
    "observaciones": "Matrícula nueva para el año 2025"
}
```

**✅ Resultado:** Al crear la matrícula, automáticamente se generan:
- 10 cuotas mensuales (marzo a diciembre)
- Numeración de cuotas del 1 al 10 (Cuota 1/2025, Cuota 2/2025, etc.)
- Fechas de vencimiento correspondientes a cada mes (marzo a diciembre)
- Cada cuota con monto de S/ 200.00
- Estado inicial: no pagado
- **Lista completa de cursos** que llevará el alumno con horas semanales
- **Información del horario** (aula, docente, horarios de inicio y fin)

---

### **PASO 6: VERIFICAR DATOS DE LA MATRÍCULA**

#### **6.1 Ver Detalles de la Matrícula**
```http
GET http://localhost:8025/api/matriculas/1
Authorization: Bearer tu_jwt_token
```

#### **6.2 Ver Fechas de Pago Generadas**
```http
GET http://localhost:8025/api/setup/fechas-pago/matricula/1
Authorization: Bearer tu_jwt_token_admin_o_secretaria
```

---

### **PASO 7: GESTIONAR PAGOS (OPCIONAL)**

#### **7.1 Marcar Primera Cuota como Pagada**
```http
PUT http://localhost:8025/api/pagos/1/marcar-pagado
Authorization: Bearer tu_jwt_token_admin_o_secretaria
Content-Type: application/json

{
    "numeroRecibo": "REC-001-2025",
    "observaciones": "Pago de matrícula en efectivo"
}
```

#### **7.2 Ver Estado de Pagos del Apoderado**
```http
POST http://localhost:8025/auth/login
Content-Type: application/json

{
    "username": "carlos_mendoza",
    "password": "123456"
}
```

```http
GET http://localhost:8025/api/apoderado/fechas-pago/pendientes
Authorization: Bearer token_del_apoderado
```

---

### **📋 RESUMEN DEL FLUJO COMPLETO**

1. **🔐 Login** → Obtener token de Administrador/Secretaria
2. **🏗️ Crear Grado** → Base para la matrícula
3. **👩‍🏫 Crear Docente** → Responsable del aula
4. **🏫 Crear Aula** → Espacio físico
5. **‍👩‍👧‍👦 Crear Apoderado** → Usuario y perfil en UN SOLO PASO
6. **🧒 Crear Alumno** → Estudiante a matricular
7. **🎓 Crear Matrícula** → Genera automáticamente 10 cuotas
8. **✅ Verificar** → Confirmar datos y pagos
9. **💰 Gestionar Pagos** → Marcar cuotas como pagadas

### **🚀 MEJORAS DEL FLUJO:**
- ✅ **PASO 5 SIMPLIFICADO:** Ya no necesitas crear usuario separadamente
- ✅ **MENOS ENDPOINTS:** De 2 pasos a 1 solo paso para apoderados
- ✅ **CONSISTENCIA:** Mismo formato que Personal administrativo

---

### **🚨 NOTAS IMPORTANTES**
- **Orden obligatorio:** Debes seguir el orden exacto de los pasos
- **IDs:** Ajusta los IDs según los datos que vayas creando
- **Tokens:** Usa el token correcto según el rol requerido
- **Automático:** Las cuotas se crean automáticamente al hacer la matrícula
- **Validaciones:** El sistema valida que existan todas las entidades relacionadas

---------------------------------------------------------------------------------------------------------------

### **⚡ FLUJO RÁPIDO CON DATOS DE PRUEBA**
Si prefieres crear todo de una vez para pruebas:

```http
POST http://localhost:8025/api/setup/datos-prueba
Authorization: Bearer tu_jwt_token_admin
```

Este endpoint crea automáticamente:
- 3 grados (1°, 2°, 3° de primaria)
- 6 cursos básicos con sus asociaciones
- 1 docente y 2 aulas
- 1 usuario apoderado completo
- 1 alumno matriculado
- 1 matrícula con 10 cuotas numeradas del 1 al 10 de S/ 200.00
- **Respuesta completa** con cursos y horario del alumno

---

## 💡 EJEMPLOS COMPARATIVOS - FORMATO UNIFICADO

### **Crear Personal Administrativo:**
```http
POST http://localhost:8025/api/personal
Authorization: Bearer tu_jwt_token_admin
Content-Type: application/json

{
    "tipoDocumento": "DNI",
    "numeroDocumento": "15612357",
    "username": "secretaria_nueva",
    "password": "secretaria123",
    "role": "Secretaria",
    "nombre": "María",
    "apellido": "García",
    "email": "maria.garcia@sansilvestro.edu.pe"
}
```

### **Crear Apoderado (Nuevo Formato):**
```http
POST http://localhost:8025/api/setup/apoderados
Authorization: Bearer tu_jwt_token_admin_o_secretaria
Content-Type: application/json

{
    "tipoDocumento": "DNI",
    "numeroDocumento": "89654321",
    "username": "carlos_mendoza",
    "password": "carlos123",
    "nombre": "Carlos",
    "apellido": "Mendoza",
    "parentesco": "Padre",
    "direccion": "Av. Los Padres 456",
    "departamento": "Lima",
    "provincia": "Lima",
    "distrito": "Miraflores",
    "telefono": "998877665",
    "email": "carlos.mendoza@email.com",
    "lugarTrabajo": "Empresa ABC",
    "cargo": "Gerente"
}
```

### **🎉 RESULTADO IDÉNTICO EN AMBOS CASOS:**
- ✅ Usuario creado automáticamente
- ✅ Perfil asociado (Personal o Apoderado)
- ✅ Documento de identidad registrado
- ✅ Estados activos por defecto
- ✅ Contraseña encriptada
- ✅ Email único validado
- ✅ Un solo paso, sin complicaciones

**¡Ahora ambos formatos son igualmente simples!** 🚀

---

## 📝 11. DATOS DE PRUEBA PARA DESARROLLO (`/api/admin/`)

### CREAR DATOS DE PRUEBA COMPLETOS
```http
POST http://localhost:8025/api/setup/datos-prueba
Authorization: Bearer tu_jwt_token_admin
👑 ROL: Administrador únicamente
```

### VER FECHAS DE PAGO POR MATRÍCULA
```http
GET http://localhost:8025/api/setup/fechas-pago/matricula/1
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
```

### VER TODAS LAS FECHAS DE PAGO
```http
GET http://localhost:8025/api/setup/fechas-pago
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
```