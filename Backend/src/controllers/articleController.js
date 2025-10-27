/**
 * Controlador de Artículos
 * Maneja la lógica de negocio para artículos informativos
 */

const Article = require('../models/Article');

/**
 * Obtener todos los artículos
 * GET /api/articles
 */
const getAllArticles = async (req, res, next) => {
  try {
    const { category } = req.query;

    const filters = {};
    if (category) filters.category = category;

    const articles = await Article.getAll(filters);

    res.status(200).json({
      count: articles.length,
      articles
    });
  } catch (error) {
    res.status(500);
    next(error);
  }
};

/**
 * Obtener un artículo por ID
 * GET /api/articles/:id
 */
const getArticleById = async (req, res, next) => {
  try {
    const { id } = req.params;

    const article = await Article.getById(id);

    if (!article) {
      res.status(404);
      throw new Error('Artículo no encontrado');
    }

    res.status(200).json({
      article
    });
  } catch (error) {
    next(error);
  }
};

/**
 * Obtener artículos por categoría
 * GET /api/articles/category/:category
 */
const getArticlesByCategory = async (req, res, next) => {
  try {
    const { category } = req.params;

    const articles = await Article.getByCategory(category);

    res.status(200).json({
      category,
      count: articles.length,
      articles
    });
  } catch (error) {
    res.status(500);
    next(error);
  }
};

/**
 * Buscar artículos
 * GET /api/articles/search
 */
const searchArticles = async (req, res, next) => {
  try {
    const { q } = req.query;

    if (!q) {
      res.status(400);
      throw new Error('Se requiere un término de búsqueda');
    }

    const articles = await Article.search(q);

    res.status(200).json({
      query: q,
      count: articles.length,
      articles
    });
  } catch (error) {
    next(error);
  }
};

/**
 * Crear nuevo artículo (Admin)
 * POST /api/articles
 */
const createArticle = async (req, res, next) => {
  try {
    const articleData = req.body;

    const article = await Article.create(articleData);

    res.status(201).json({
      message: 'Artículo creado exitosamente',
      article
    });
  } catch (error) {
    res.status(400);
    next(error);
  }
};

/**
 * Actualizar artículo (Admin)
 * PUT /api/articles/:id
 */
const updateArticle = async (req, res, next) => {
  try {
    const { id } = req.params;
    const updateData = req.body;

    const article = await Article.update(id, updateData);

    res.status(200).json({
      message: 'Artículo actualizado exitosamente',
      article
    });
  } catch (error) {
    res.status(400);
    next(error);
  }
};

/**
 * Eliminar artículo (Admin)
 * DELETE /api/articles/:id
 */
const deleteArticle = async (req, res, next) => {
  try {
    const { id } = req.params;

    await Article.delete(id);

    res.status(200).json({
      message: 'Artículo eliminado exitosamente'
    });
  } catch (error) {
    res.status(400);
    next(error);
  }
};

module.exports = {
  getAllArticles,
  getArticleById,
  getArticlesByCategory,
  searchArticles,
  createArticle,
  updateArticle,
  deleteArticle
};

