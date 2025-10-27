/**
 * Rutas de Recursos Offline
 * Define los endpoints para obtener recursos que pueden ser descargados para uso offline
 */

const express = require('express');
const router = express.Router();
const {
  getAllOfflineResources,
  getOfflineMeditations,
  getOfflineBreathing,
  getOfflineAudios,
  getOfflineArticles,
  checkUpdates
} = require('../controllers/offlineController');
const { optionalAuth } = require('../middleware/auth');

/**
 * @swagger
 * /api/offline/resources:
 *   get:
 *     tags: [Modo Offline]
 *     summary: Obtener todos los recursos para modo offline
 *     description: Retorna todos los recursos necesarios para uso offline de la aplicación
 *     responses:
 *       200:
 *         description: Recursos offline completos
 */
router.get('/resources', optionalAuth, getAllOfflineResources);

/**
 * @swagger
 * /api/offline/meditations:
 *   get:
 *     tags: [Modo Offline]
 *     summary: Obtener meditaciones para offline
 *     responses:
 *       200:
 *         description: Lista de meditaciones
 */
router.get('/meditations', optionalAuth, getOfflineMeditations);

/**
 * @swagger
 * /api/offline/breathing:
 *   get:
 *     tags: [Modo Offline]
 *     summary: Obtener ejercicios de respiración para offline
 *     responses:
 *       200:
 *         description: Lista de ejercicios
 */
router.get('/breathing', optionalAuth, getOfflineBreathing);

/**
 * @swagger
 * /api/offline/audios:
 *   get:
 *     tags: [Modo Offline]
 *     summary: Obtener audios para offline
 *     responses:
 *       200:
 *         description: Lista de audios
 */
router.get('/audios', optionalAuth, getOfflineAudios);

/**
 * @swagger
 * /api/offline/articles:
 *   get:
 *     tags: [Modo Offline]
 *     summary: Obtener artículos para offline
 *     responses:
 *       200:
 *         description: Lista de artículos
 */
router.get('/articles', optionalAuth, getOfflineArticles);

/**
 * @swagger
 * /api/offline/check-updates:
 *   get:
 *     tags: [Modo Offline]
 *     summary: Verificar actualizaciones de recursos
 *     parameters:
 *       - in: query
 *         name: version
 *         schema:
 *           type: string
 *         description: Versión actual del cliente
 *     responses:
 *       200:
 *         description: Estado de actualizaciones
 */
router.get('/check-updates', checkUpdates);

module.exports = router;

