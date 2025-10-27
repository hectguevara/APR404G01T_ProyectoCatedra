/**
 * Rutas de Meditaciones
 * Define los endpoints para meditaciones guiadas
 */

const express = require('express');
const router = express.Router();
const {
  getAllMeditations,
  getMeditationById,
  getMeditationsByCategory,
  createMeditation,
  updateMeditation,
  deleteMeditation
} = require('../controllers/meditationController');
const { optionalAuth } = require('../middleware/auth');
const { meditationValidators } = require('../middleware/validators');
const { handleValidationErrors } = require('../middleware/errorHandler');

/**
 * @swagger
 * /api/meditations:
 *   get:
 *     tags: [Meditaciones]
 *     summary: Obtener todas las meditaciones
 *     parameters:
 *       - in: query
 *         name: category
 *         schema:
 *           type: string
 *         description: Filtrar por categoría
 *       - in: query
 *         name: duration
 *         schema:
 *           type: number
 *         description: Duración máxima en minutos
 *     responses:
 *       200:
 *         description: Lista de meditaciones
 */
router.get('/', optionalAuth, getAllMeditations);

/**
 * @swagger
 * /api/meditations/{id}:
 *   get:
 *     tags: [Meditaciones]
 *     summary: Obtener una meditación por ID
 *     parameters:
 *       - in: path
 *         name: id
 *         required: true
 *         schema:
 *           type: string
 *     responses:
 *       200:
 *         description: Datos de la meditación
 *       404:
 *         description: Meditación no encontrada
 */
router.get(
  '/:id',
  meditationValidators.getById,
  handleValidationErrors,
  getMeditationById
);

/**
 * @swagger
 * /api/meditations/category/{category}:
 *   get:
 *     tags: [Meditaciones]
 *     summary: Obtener meditaciones por categoría
 *     parameters:
 *       - in: path
 *         name: category
 *         required: true
 *         schema:
 *           type: string
 *     responses:
 *       200:
 *         description: Lista de meditaciones de la categoría
 */
router.get('/category/:category', getMeditationsByCategory);

// Rutas de administración (requieren autenticación)
router.post('/', createMeditation);
router.put('/:id', updateMeditation);
router.delete('/:id', deleteMeditation);

module.exports = router;

