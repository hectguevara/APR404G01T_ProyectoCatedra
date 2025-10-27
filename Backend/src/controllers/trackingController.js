/**
 * Controlador de Seguimiento Emocional
 * Maneja la lógica de negocio para el registro y análisis del estado de ánimo
 */

const Tracking = require('../models/Tracking');

/**
 * Crear nuevo registro de seguimiento
 * POST /api/tracking
 */
const createTracking = async (req, res, next) => {
  try {
    const { uid } = req.user;
    const trackingData = req.body;

    const tracking = await Tracking.create(uid, trackingData);

    res.status(201).json({
      message: 'Registro de seguimiento creado exitosamente',
      tracking
    });
  } catch (error) {
    res.status(400);
    next(error);
  }
};

/**
 * Obtener registros de seguimiento del usuario
 * GET /api/tracking
 */
const getUserTracking = async (req, res, next) => {
  try {
    const { uid } = req.user;
    const { startDate, endDate, limit } = req.query;

    const options = {};
    if (startDate) options.startDate = startDate;
    if (endDate) options.endDate = endDate;
    if (limit) options.limit = parseInt(limit);

    const tracking = await Tracking.getUserTracking(uid, options);

    res.status(200).json({
      count: tracking.length,
      tracking
    });
  } catch (error) {
    res.status(500);
    next(error);
  }
};

/**
 * Obtener estadísticas del estado de ánimo
 * GET /api/tracking/stats
 */
const getStats = async (req, res, next) => {
  try {
    const { uid } = req.user;
    const { startDate, endDate } = req.query;

    const options = {};
    if (startDate) options.startDate = startDate;
    if (endDate) options.endDate = endDate;

    const stats = await Tracking.getStats(uid, options);

    res.status(200).json({
      stats
    });
  } catch (error) {
    res.status(500);
    next(error);
  }
};

/**
 * Obtener un registro específico
 * GET /api/tracking/:id
 */
const getTrackingById = async (req, res, next) => {
  try {
    const { id } = req.params;

    const tracking = await Tracking.getById(id);

    if (!tracking) {
      res.status(404);
      throw new Error('Registro no encontrado');
    }

    // Verificar que el usuario sea el propietario
    if (tracking.userId !== req.user.uid) {
      res.status(403);
      throw new Error('No autorizado para ver este registro');
    }

    res.status(200).json({
      tracking
    });
  } catch (error) {
    next(error);
  }
};

/**
 * Actualizar registro de seguimiento
 * PUT /api/tracking/:id
 */
const updateTracking = async (req, res, next) => {
  try {
    const { uid } = req.user;
    const { id } = req.params;
    const updateData = req.body;

    const tracking = await Tracking.update(id, uid, updateData);

    res.status(200).json({
      message: 'Registro actualizado exitosamente',
      tracking
    });
  } catch (error) {
    if (error.message === 'No autorizado para actualizar este registro') {
      res.status(403);
    } else if (error.message === 'Registro no encontrado') {
      res.status(404);
    } else {
      res.status(400);
    }
    next(error);
  }
};

/**
 * Eliminar registro de seguimiento
 * DELETE /api/tracking/:id
 */
const deleteTracking = async (req, res, next) => {
  try {
    const { uid } = req.user;
    const { id } = req.params;

    await Tracking.delete(id, uid);

    res.status(200).json({
      message: 'Registro eliminado exitosamente'
    });
  } catch (error) {
    if (error.message === 'No autorizado para eliminar este registro') {
      res.status(403);
    } else if (error.message === 'Registro no encontrado') {
      res.status(404);
    } else {
      res.status(400);
    }
    next(error);
  }
};

module.exports = {
  createTracking,
  getUserTracking,
  getStats,
  getTrackingById,
  updateTracking,
  deleteTracking
};

