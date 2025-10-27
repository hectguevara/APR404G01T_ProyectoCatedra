/**
 * Configuración de Swagger para la documentación de la API
 */

const swaggerDefinition = {
  openapi: '3.0.0',
  info: {
    title: 'PeaceNest API',
    version: '1.0.0',
    description: 'API REST para PeaceNest - Herramientas para la Relajación y Bienestar Mental',
    contact: {
      name: 'PeaceNest Team',
      email: 'support@peacenest.com'
    },
    license: {
      name: 'MIT',
      url: 'https://opensource.org/licenses/MIT'
    }
  },
  servers: [
    {
      url: 'http://localhost:8080',
      description: 'Servidor de desarrollo'
    },
    {
      url: 'https://api.peacenest.com',
      description: 'Servidor de producción'
    }
  ],
  components: {
    securitySchemes: {
      bearerAuth: {
        type: 'http',
        scheme: 'bearer',
        bearerFormat: 'JWT',
        description: 'Introduce el token JWT obtenido al hacer login'
      }
    },
    schemas: {
      User: {
        type: 'object',
        properties: {
          uid: { type: 'string', description: 'ID único del usuario' },
          email: { type: 'string', format: 'email', description: 'Correo electrónico' },
          name: { type: 'string', description: 'Nombre completo del usuario' },
          createdAt: { type: 'string', format: 'date-time', description: 'Fecha de creación' }
        }
      },
      Meditation: {
        type: 'object',
        properties: {
          id: { type: 'string', description: 'ID único de la meditación' },
          title: { type: 'string', description: 'Título de la meditación' },
          description: { type: 'string', description: 'Descripción detallada' },
          audioUrl: { type: 'string', format: 'uri', description: 'URL del audio' },
          duration: { type: 'number', description: 'Duración en minutos' },
          category: { type: 'string', description: 'Categoría' }
        }
      },
      BreathingExercise: {
        type: 'object',
        properties: {
          id: { type: 'string', description: 'ID único del ejercicio' },
          name: { type: 'string', description: 'Nombre del ejercicio' },
          duration: { type: 'number', description: 'Duración en segundos' },
          instructions: { type: 'string', description: 'Instrucciones del ejercicio' },
          inhale: { type: 'number', description: 'Segundos de inhalación' },
          hold: { type: 'number', description: 'Segundos de retención' },
          exhale: { type: 'number', description: 'Segundos de exhalación' }
        }
      },
      Article: {
        type: 'object',
        properties: {
          id: { type: 'string', description: 'ID único del artículo' },
          title: { type: 'string', description: 'Título del artículo' },
          content: { type: 'string', description: 'Contenido del artículo' },
          category: { type: 'string', description: 'Categoría' },
          imageUrl: { type: 'string', format: 'uri', description: 'URL de la imagen' },
          author: { type: 'string', description: 'Autor del artículo' },
          publishedAt: { type: 'string', format: 'date-time', description: 'Fecha de publicación' }
        }
      },
      Tracking: {
        type: 'object',
        properties: {
          id: { type: 'string', description: 'ID único del registro' },
          userId: { type: 'string', description: 'ID del usuario' },
          mood: { type: 'string', enum: ['muy_mal', 'mal', 'neutral', 'bien', 'muy_bien'], description: 'Estado de ánimo' },
          notes: { type: 'string', description: 'Notas adicionales' },
          activities: { type: 'array', items: { type: 'string' }, description: 'Actividades realizadas' },
          date: { type: 'string', format: 'date-time', description: 'Fecha del registro' }
        }
      },
      Error: {
        type: 'object',
        properties: {
          error: { type: 'string', description: 'Mensaje de error' },
          code: { type: 'string', description: 'Código de error' }
        }
      }
    }
  },
  security: [
    {
      bearerAuth: []
    }
  ]
};

module.exports = swaggerDefinition;

