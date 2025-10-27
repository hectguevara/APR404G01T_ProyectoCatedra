/**
 * Rutas de Ejercicios de Respiración
 * Define los endpoints para ejercicios de respiración
 */

const express = require('express');
const router = express.Router();
const {
  getAllExercises,
  getExerciseById,
  saveProgress,
  getUserProgress,
  createExercise
} = require('../controllers/breathingController');
const { authenticateToken, optionalAuth } = require('../middleware/auth');
const { breathingValidators } = require('../middleware/validators');
const { handleValidationErrors } = require('../middleware/errorHandler');

/**
 * @swagger
 * /api/breathing:
 *   get:
 *     tags: [Ejercicios de Respiración]
 *     summary: Obtener todos los ejercicios de respiración
 *     responses:
 *       200:
 *         description: Lista de ejercicios
 */
router.get('/', optionalAuth, getAllExercises);

/**
 * @swagger
 * /api/breathing/{id}:
 *   get:
 *     tags: [Ejercicios de Respiración]
 *     summary: Obtener un ejercicio por ID
 *     parameters:
 *       - in: path
 *         name: id
 *         required: true
 *         schema:
 *           type: string
 *     responses:
 *       200:
 *         description: Datos del ejercicio
 *       404:
 *         description: Ejercicio no encontrado
 */
router.get('/:id', getExerciseById);

/**
 * @swagger
 * /api/breathing/progress:
 *   post:
 *     tags: [Ejercicios de Respiración]
 *     summary: Guardar progreso de ejercicio
 *     security:
 *       - bearerAuth: []
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             required:
 *               - exerciseId
 *               - completed
 *             properties:
 *               exerciseId:
 *                 type: string
 *               completed:
 *                 type: boolean
 *               duration:
 *                 type: number
 *     responses:
 *       201:
 *         description: Progreso guardado exitosamente
 */
router.post(
  '/progress',
  authenticateToken,
  breathingValidators.saveProgress,
  handleValidationErrors,
  saveProgress
);

/**
 * @swagger
 * /api/breathing/progress:
 *   get:
 *     tags: [Ejercicios de Respiración]
 *     summary: Obtener progreso del usuario
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - in: query
 *         name: limit
 *         schema:
 *           type: number
 *         description: Número de registros a retornar
 *     responses:
 *       200:
 *         description: Historial de progreso
 */
router.get('/progress', authenticateToken, getUserProgress);

// Ruta de administración
router.post('/', createExercise);

module.exports = router;

