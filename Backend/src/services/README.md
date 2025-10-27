# 🔧 Services Directory

Esta carpeta está destinada para servicios adicionales que puedan ser necesarios en el futuro.

## Propósito

Los servicios son clases o funciones que contienen lógica de negocio compleja que puede ser reutilizada por múltiples controladores.

## Ejemplos de servicios futuros

- **EmailService**: Para enviar correos electrónicos (notificaciones, recuperación de contraseña)
- **StorageService**: Para manejar uploads de archivos a Firebase Storage
- **NotificationService**: Para enviar notificaciones push
- **AnalyticsService**: Para recopilar y analizar métricas de uso
- **CacheService**: Para implementar caché de datos frecuentemente accedidos

## Estructura recomendada

```javascript
class EmailService {
  static async sendWelcomeEmail(user) {
    // Lógica para enviar email de bienvenida
  }

  static async sendPasswordReset(email) {
    // Lógica para enviar email de recuperación
  }
}

module.exports = EmailService;
```

## Uso en controladores

```javascript
const EmailService = require('../services/EmailService');

const register = async (req, res) => {
  const user = await User.create(req.body);
  await EmailService.sendWelcomeEmail(user);
  res.json({ user });
};
```

