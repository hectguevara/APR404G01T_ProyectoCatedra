/**
 * Controlador de Audios Relajantes
 * Maneja la lógica de negocio para la biblioteca de audios
 */

const Audio = require('../models/Audio');

/**
 * Obtener todos los audios
 * GET /api/audios
 */
const getAllAudios = async (req, res, next) => {
  try {
    const { category, type } = req.query;

    const filters = {};
    if (category) filters.category = category;
    if (type) filters.type = type;

    const audios = await Audio.getAll(filters);

    res.status(200).json({
      count: audios.length,
      audios
    });
  } catch (error) {
    res.status(500);
    next(error);
  }
};

/**
 * Obtener un audio por ID
 * GET /api/audios/:id
 */
const getAudioById = async (req, res, next) => {
  try {
    const { id } = req.params;

    const audio = await Audio.getById(id);

    if (!audio) {
      res.status(404);
      throw new Error('Audio no encontrado');
    }

    res.status(200).json({
      audio
    });
  } catch (error) {
    next(error);
  }
};

/**
 * Obtener audios por categoría
 * GET /api/audios/category/:category
 */
const getAudiosByCategory = async (req, res, next) => {
  try {
    const { category } = req.params;

    const audios = await Audio.getByCategory(category);

    res.status(200).json({
      category,
      count: audios.length,
      audios
    });
  } catch (error) {
    res.status(500);
    next(error);
  }
};

/**
 * Obtener todas las categorías disponibles
 * GET /api/audios/categories
 */
const getCategories = async (req, res, next) => {
  try {
    const categories = await Audio.getCategories();

    res.status(200).json({
      count: categories.length,
      categories
    });
  } catch (error) {
    res.status(500);
    next(error);
  }
};

/**
 * Crear nuevo audio (Admin)
 * POST /api/audios
 */
const createAudio = async (req, res, next) => {
  try {
    const audioData = req.body;

    const audio = await Audio.create(audioData);

    res.status(201).json({
      message: 'Audio creado exitosamente',
      audio
    });
  } catch (error) {
    res.status(400);
    next(error);
  }
};

/**
 * Actualizar audio (Admin)
 * PUT /api/audios/:id
 */
const updateAudio = async (req, res, next) => {
  try {
    const { id } = req.params;
    const updateData = req.body;

    const audio = await Audio.update(id, updateData);

    res.status(200).json({
      message: 'Audio actualizado exitosamente',
      audio
    });
  } catch (error) {
    res.status(400);
    next(error);
  }
};

/**
 * Eliminar audio (Admin)
 * DELETE /api/audios/:id
 */
const deleteAudio = async (req, res, next) => {
  try {
    const { id } = req.params;

    await Audio.delete(id);

    res.status(200).json({
      message: 'Audio eliminado exitosamente'
    });
  } catch (error) {
    res.status(400);
    next(error);
  }
};

module.exports = {
  getAllAudios,
  getAudioById,
  getAudiosByCategory,
  getCategories,
  createAudio,
  updateAudio,
  deleteAudio
};

