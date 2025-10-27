/**
 * Rutas de Audios Relajantes
 * Define los endpoints para la biblioteca de audios
 */

const express = require('express');
const router = express.Router();
const {
  getAllAudios,
  getAudioById,
  getAudiosByCategory,
  getCategories,
  createAudio,
  updateAudio,
  deleteAudio
} = require('../controllers/audioController');
const { optionalAuth } = require('../middleware/auth');

/**
 * @swagger
 * /api/audios:
 *   get:
 *     tags: [Audios]
 *     summary: Obtener todos los audios
 *     parameters:
 *       - in: query
 *         name: category
 *         schema:
 *           type: string
 *         description: Filtrar por categoría
 *       - in: query
 *         name: type
 *         schema:
 *           type: string
 *         description: Filtrar por tipo
 *     responses:
 *       200:
 *         description: Lista de audios
 */
router.get('/', optionalAuth, getAllAudios);

/**
 * @swagger
 * /api/audios/categories:
 *   get:
 *     tags: [Audios]
 *     summary: Obtener todas las categorías de audios
 *     responses:
 *       200:
 *         description: Lista de categorías
 */
router.get('/categories', getCategories);

/**
 * @swagger
 * /api/audios/{id}:
 *   get:
 *     tags: [Audios]
 *     summary: Obtener un audio por ID
 *     parameters:
 *       - in: path
 *         name: id
 *         required: true
 *         schema:
 *           type: string
 *     responses:
 *       200:
 *         description: Datos del audio
 *       404:
 *         description: Audio no encontrado
 */
router.get('/:id', getAudioById);

/**
 * @swagger
 * /api/audios/category/{category}:
 *   get:
 *     tags: [Audios]
 *     summary: Obtener audios por categoría
 *     parameters:
 *       - in: path
 *         name: category
 *         required: true
 *         schema:
 *           type: string
 *     responses:
 *       200:
 *         description: Lista de audios de la categoría
 */
router.get('/category/:category', getAudiosByCategory);

// Rutas de administración
router.post('/', createAudio);
router.put('/:id', updateAudio);
router.delete('/:id', deleteAudio);

module.exports = router;

