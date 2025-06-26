# Sistema de Recuperación de Contraseña para Apoderados

## Configuración de Email

Para que el sistema de recuperación de contraseña funcione correctamente, debes configurar los siguientes parámetros en `application.properties`:

```properties
# Configuración de Email (Gmail)
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=tu-email@gmail.com
spring.mail.password=tu-app-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=true

# URL del frontend para generar enlaces de recuperación
app.frontend.url=http://localhost:3000
```

### Configuración de Gmail

1. **Habilitar 2FA**: Ve a tu cuenta de Google → Seguridad → Verificación en 2 pasos
2. **Generar App Password**: En "Contraseñas de aplicaciones", genera una nueva contraseña específica para esta aplicación
3. **Usar App Password**: Usa esta contraseña generada en `spring.mail.password`, NO tu contraseña de Gmail

## Endpoints Disponibles

### 1. Solicitar Recuperación de Contraseña
```
POST /auth/forgot-password
Content-Type: application/json

{
    "email": "apoderado@ejemplo.com"
}
```

**Respuesta exitosa:**
```json
{
    "success": true,
    "message": "Si el email está registrado, recibirás las instrucciones para restablecer tu contraseña"
}
```

### 2. Restablecer Contraseña
```
POST /auth/reset-password
Content-Type: application/json

{
    "token": "token_recibido_por_email",
    "newPassword": "nuevaContrasena123",
    "confirmPassword": "nuevaContrasena123"
}
```

**Respuesta exitosa:**
```json
{
    "success": true,
    "message": "Contraseña restablecida exitosamente"
}
```

### 3. Validar Token de Recuperación
```
GET /auth/validate-reset-token?token=token_recibido_por_email
```

**Respuesta exitosa:**
```json
{
    "success": true,
    "message": "Token válido"
}
```

## Flujo de Recuperación de Contraseña

1. **Solicitud Inicial**: El apoderado ingresa su email en el formulario de "Olvidé mi contraseña"
2. **Generación de Token**: El sistema genera un token único y seguro que expira en 1 hora
3. **Envío de Email**: Se envía un correo con el enlace de recuperación al email del apoderado
4. **Validación**: El usuario hace clic en el enlace y el frontend valida el token
5. **Nueva Contraseña**: El usuario ingresa su nueva contraseña
6. **Confirmación**: El sistema actualiza la contraseña y marca el token como usado

## Características de Seguridad

- **Tokens Únicos**: Cada solicitud genera un token único y aleatorio
- **Expiración**: Los tokens expiran automáticamente después de 1 hora
- **Un Solo Uso**: Cada token solo puede usarse una vez
- **Limpieza Automática**: Los tokens expirados se eliminan automáticamente cada hora
- **Validación de Email**: Solo se pueden recuperar contraseñas de emails registrados como apoderados
- **Encriptación**: Las nuevas contraseñas se encriptan antes de guardarse

## Consideraciones Importantes

1. **Email Real**: Asegúrate de usar un email real y configurado correctamente
2. **Firewall**: Verifica que el puerto 587 esté abierto para conexiones SMTP
3. **Frontend**: El enlace en el email apunta a `app.frontend.url`, asegúrate de configurar la URL correcta
4. **Logs**: El sistema registra todas las operaciones importantes para auditoría
5. **Seguridad**: Por razones de seguridad, no se revela si un email existe o no en la respuesta

## Estructura de Base de Datos

Se crea automáticamente la tabla `password_reset_tokens` con la siguiente estructura:

```sql
CREATE TABLE password_reset_tokens (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    token VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL,
    user_id INTEGER NOT NULL,
    created_at DATETIME NOT NULL,
    expires_at DATETIME NOT NULL,
    used BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

## Personalización del Email

Puedes personalizar el contenido del email modificando el método `sendPasswordResetEmail` en la clase `EmailService`. El email incluye:

- Saludo personalizado con el nombre del apoderado
- Enlace directo para restablecer la contraseña
- Información sobre la expiración del token
- Instrucciones de seguridad
