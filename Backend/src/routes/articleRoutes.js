/**
 * Rutas de Artículos
 * Define los endpoints para artículos informativos
 */

const express = require('express');
const router = express.Router();
const {
  getAllArticles,
  getArticleById,
  getArticlesByCategory,
  searchArticles,
  createArticle,
  updateArticle,
  deleteArticle
} = require('../controllers/articleController');
const { optionalAuth } = require('../middleware/auth');
const { articleValidators } = require('../middleware/validators');
const { handleValidationErrors } = require('../middleware/errorHandler');

/**
 * @swagger
 * /api/articles:
 *   get:
 *     tags: [Artículos]
 *     summary: Obtener todos los artículos
 *     parameters:
 *       - in: query
 *         name: category
 *         schema:
 *           type: string
 *         description: Filtrar por categoría
 *     responses:
 *       200:
 *         description: Lista de artículos
 */
router.get('/', optionalAuth, getAllArticles);

/**
 * @swagger
 * /api/articles/search:
 *   get:
 *     tags: [Artículos]
 *     summary: Buscar artículos
 *     parameters:
 *       - in: query
 *         name: q
 *         required: true
 *         schema:
 *           type: string
 *         description: Término de búsqueda
 *     responses:
 *       200:
 *         description: Resultados de búsqueda
 */
router.get('/search', searchArticles);

/**
 * @swagger
 * /api/articles/{id}:
 *   get:
 *     tags: [Artículos]
 *     summary: Obtener un artículo por ID
 *     parameters:
 *       - in: path
 *         name: id
 *         required: true
 *         schema:
 *           type: string
 *     responses:
 *       200:
 *         description: Datos del artículo
 *       404:
 *         description: Artículo no encontrado
 */
router.get(
  '/:id',
  articleValidators.getById,
  handleValidationErrors,
  getArticleById
);

/**
 * @swagger
 * /api/articles/category/{category}:
 *   get:
 *     tags: [Artículos]
 *     summary: Obtener artículos por categoría
 *     parameters:
 *       - in: path
 *         name: category
 *         required: true
 *         schema:
 *           type: string
 *     responses:
 *       200:
 *         description: Lista de artículos de la categoría
 */
router.get('/category/:category', getArticlesByCategory);

// Rutas de administración
router.post('/', createArticle);
router.put('/:id', updateArticle);
router.delete('/:id', deleteArticle);

module.exports = router;

