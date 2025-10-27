/**
 * Controlador de Meditaciones
 * Maneja la lógica de negocio para meditaciones guiadas
 */

const Meditation = require('../models/Meditation');

/**
 * Obtener todas las meditaciones
 * GET /api/meditations
 */
const getAllMeditations = async (req, res, next) => {
  try {
    const { category, duration } = req.query;

    const filters = {};
    if (category) filters.category = category;
    if (duration) filters.duration = parseInt(duration);

    const meditations = await Meditation.getAll(filters);

    res.status(200).json({
      count: meditations.length,
      meditations
    });
  } catch (error) {
    res.status(500);
    next(error);
  }
};

/**
 * Obtener una meditación por ID
 * GET /api/meditations/:id
 */
const getMeditationById = async (req, res, next) => {
  try {
    const { id } = req.params;

    const meditation = await Meditation.getById(id);

    if (!meditation) {
      res.status(404);
      throw new Error('Meditación no encontrada');
    }

    res.status(200).json({
      meditation
    });
  } catch (error) {
    next(error);
  }
};

/**
 * Obtener meditaciones por categoría
 * GET /api/meditations/category/:category
 */
const getMeditationsByCategory = async (req, res, next) => {
  try {
    const { category } = req.params;

    const meditations = await Meditation.getByCategory(category);

    res.status(200).json({
      category,
      count: meditations.length,
      meditations
    });
  } catch (error) {
    res.status(500);
    next(error);
  }
};

/**
 * Crear nueva meditación (Admin)
 * POST /api/meditations
 */
const createMeditation = async (req, res, next) => {
  try {
    const meditationData = req.body;

    const meditation = await Meditation.create(meditationData);

    res.status(201).json({
      message: 'Meditación creada exitosamente',
      meditation
    });
  } catch (error) {
    res.status(400);
    next(error);
  }
};

/**
 * Actualizar meditación (Admin)
 * PUT /api/meditations/:id
 */
const updateMeditation = async (req, res, next) => {
  try {
    const { id } = req.params;
    const updateData = req.body;

    const meditation = await Meditation.update(id, updateData);

    res.status(200).json({
      message: 'Meditación actualizada exitosamente',
      meditation
    });
  } catch (error) {
    res.status(400);
    next(error);
  }
};

/**
 * Eliminar meditación (Admin)
 * DELETE /api/meditations/:id
 */
const deleteMeditation = async (req, res, next) => {
  try {
    const { id } = req.params;

    await Meditation.delete(id);

    res.status(200).json({
      message: 'Meditación eliminada exitosamente'
    });
  } catch (error) {
    res.status(400);
    next(error);
  }
};

module.exports = {
  getAllMeditations,
  getMeditationById,
  getMeditationsByCategory,
  createMeditation,
  updateMeditation,
  deleteMeditation
};

