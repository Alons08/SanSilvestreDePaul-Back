# 📋 GUÍA COMPLETA DE ENDPOINTS - SISTEMA ESCOLAR SAN SILVESTRE DE PAÚL

**Base URL:** `http://localhost:8025`

---

## 🔐 1. AUTENTICACIÓN (`/auth/`) - SIN TOKEN

### LOGIN
```http
POST http://localhost:8025/auth/login
Content-Type: application/json
🔓 ROL: PÚBLICO (Sin autenticación)

{
    "username": "Alonso",
    "password": "123456"
}
```

### REGISTER
```http
POST http://localhost:8025/auth/register
Content-Type: application/json
🔓 ROL: PÚBLICO (Sin autenticación)

{
    "username": "nuevo_usuario",
    "password": "123456",
    "role": "Administrador"
}
```

### FORGOT PASSWORD
```http
POST http://localhost:8025/auth/forgot-password
Content-Type: application/json
🔓 ROL: PÚBLICO (Sin autenticación)
⚠️ LIMITACIÓN: Solo funciona para usuarios APODERADO

{
    "email": "apoderado@email.com"
}
```

### RESET PASSWORD
```http
POST http://localhost:8025/auth/reset-password
Content-Type: application/json
🔓 ROL: PÚBLICO (Sin autenticación)
⚠️ LIMITACIÓN: Solo funciona para usuarios APODERADO

{
    "token": "token_recibido_por_email",
    "newPassword": "nueva_contraseña",
    "confirmPassword": "nueva_contraseña"
}
```

---

## 🧪 2. DEMOS Y TESTING (`/api/v1/`)

### ENDPOINT PARA TODOS
```http
GET http://localhost:8025/api/v1/todos
Authorization: Bearer tu_jwt_token
👥 ROLES: Administrador, Secretaria, Apoderado
```

### ENDPOINT SOLO APODERADOS
```http
GET http://localhost:8025/api/v1/solo
Authorization: Bearer tu_jwt_token_apoderado
👤 ROL: Apoderado únicamente
```

### ENDPOINT SOLO ADMIN
```http
GET http://localhost:8025/api/v1/admin
Authorization: Bearer tu_jwt_token_admin
👑 ROL: Administrador únicamente
```

---

## 🏗️ 3. SETUP - CREAR ENTIDADES (`/api/setup/`)

### CREAR APODERADO
```http
POST http://localhost:8025/api/setup/apoderados
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
Content-Type: application/json

{
    "userId": 1,
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
    "cargo": "Gerente",
    "documentoIdentidad": {
        "tipoDocumento": "DNI",
        "numeroDocumento": "87654321"
    }
}
```

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

### CREAR ALUMNO
```http
POST http://localhost:8025/api/setup/alumnos
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
Content-Type: application/json

{
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
    "diagnosticoMedico": null,
    "documentoIdentidad": {
        "tipoDocumento": "DNI",
        "numeroDocumento": "11223344"
    }
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

---

## 🔍 4. SETUP - CONSULTAS (GET PUT DELETE) (`/api/setup/`)

### LISTAR USUARIOS
```http
GET http://localhost:8025/api/setup/usuarios
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
```

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
    "cargo": "Director",
    "documentoIdentidad": {
        "tipoDocumento": "DNI",
        "numeroDocumento": "87654321"
    }
}
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
    "nombre": "Ana Actualizada",
    "apellido": "Mendoza",
    "fechaNacimiento": "2017-03-15",
    "genero": "Femenino",
    "direccion": "Nueva Dirección Alumno",
    "departamento": "Lima",
    "provincia": "Lima",
    "distrito": "San Isidro",
    "tieneDiscapacidad": false,
    "diagnosticoMedico": null,
    "documentoIdentidad": {
        "tipoDocumento": "DNI",
        "numeroDocumento": "11223344"
    }
}
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

## 🎯 5. DATOS DE PRUEBA (`/api/setup/`)

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

---

## 🔄 6. GESTIÓN DE ESTADOS (`/api/estados/`)

### CAMBIAR ESTADO USUARIO
```http
PUT http://localhost:8025/api/estados/usuarios/1?estado=true
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
```

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

### LISTAR USUARIOS POR ESTADO
```http
GET http://localhost:8025/api/estados/usuarios?estado=true
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

---

## 🎓 7. MATRÍCULAS (`/api/matriculas/`)

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

## 💰 8. GESTIÓN DE PAGOS (`/api/pagos/`)

### MARCAR CUOTA COMO PAGADA
```http
PUT http://localhost:8025/api/pagos/1/marcar-pagado
Authorization: Bearer tu_jwt_token_admin_o_secretaria
👑🏢 ROLES: Administrador, Secretaria
Content-Type: application/json

{
    "numeroRecibo": "REC-001-2025",
    "observaciones": "Pago realizado en efectivo"
}
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

## 👨‍👩‍👧‍👦 9. PORTAL APODERADOS (`/api/apoderado/`)

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

### VER MIS MATRÍCULAS
```http
GET http://localhost:8025/api/matriculas/mis-matriculas
Authorization: Bearer tu_jwt_token_apoderado
👤 ROL: Apoderado únicamente
```

---

## 🔑 LEYENDA DE ROLES Y TOKENS

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
    "password": "123456"
}
```

### 2. Token de Apoderado
```http
POST http://localhost:8025/auth/login
Content-Type: application/json

{
    "username": "apoderado_prueba",
    "password": "123456"
}
```

### 3. Token de Secretaria
**Primero registra un usuario secretaria:**
```http
POST http://localhost:8025/auth/register
Content-Type: application/json

{
    "username": "secretaria1",
    "password": "123456",
    "role": "Secretaria"
}
```

**Luego haz login:**
```http
POST http://localhost:8025/auth/login
Content-Type: application/json

{
    "username": "secretaria1",
    "password": "123456"
}
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
├── 📁 02-Demos-Testing/
├── 📁 03-Setup-Configuracion/
├── 📁 04-Administracion/
├── 📁 05-Gestion-Estados/
├── 📁 06-Matriculas/
├── 📁 07-Pagos/
└── 📁 08-Portal-Apoderados/
```



------------------------------------------------------------------------------------------------------------

## 🎯 FLUJO COMPLETO PARA CREAR UNA MATRÍCULA DESDE CERO

### **PASO 1: AUTENTICACIÓN**
```http
POST http://localhost:8025/auth/login
Content-Type: application/json

{
    "username": "Alonso",
    "password": "123456"
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

### **PASO 3: CREAR USUARIO PARA APODERADO**

#### **3.1 Registrar Usuario**
```http
POST http://localhost:8025/auth/register
Content-Type: application/json

{
    "username": "carlos_mendoza",
    "password": "123456",
    "role": "Apoderado"
}
```

#### **3.2 Crear Perfil de Apoderado**
```http
POST http://localhost:8025/api/setup/apoderados
Authorization: Bearer tu_jwt_token_admin_o_secretaria
Content-Type: application/json

{
    "userId": 2,
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
    "cargo": "Gerente",
    "documentoIdentidad": {
        "tipoDocumento": "DNI",
        "numeroDocumento": "87654321"
    }
}
```

---

### **PASO 4: CREAR ALUMNO**
```http
POST http://localhost:8025/api/setup/alumnos
Authorization: Bearer tu_jwt_token_admin_o_secretaria
Content-Type: application/json

{
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
    "diagnosticoMedico": null,
    "documentoIdentidad": {
        "tipoDocumento": "DNI",
        "numeroDocumento": "11223344"
    }
}
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
- Cada cuota con monto de S/ 200.00
- Estado inicial: no pagado

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
5. **👤 Registrar Usuario** → Cuenta para apoderado
6. **👨‍👩‍👧‍👦 Crear Apoderado** → Perfil del responsable
7. **🧒 Crear Alumno** → Estudiante a matricular
8. **🎓 Crear Matrícula** → Genera automáticamente 10 cuotas
9. **✅ Verificar** → Confirmar datos y pagos
10. **💰 Gestionar Pagos** → Marcar cuotas como pagadas

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
- 1 matrícula con 10 cuotas de S/ 200.00