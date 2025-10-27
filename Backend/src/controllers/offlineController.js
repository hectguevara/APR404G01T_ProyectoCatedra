/**
 * Controlador de Recursos Offline
 * Proporciona endpoints para obtener recursos que pueden ser descargados para uso offline
 */

const Meditation = require('../models/Meditation');
const BreathingExercise = require('../models/BreathingExercise');
const Audio = require('../models/Audio');
const Article = require('../models/Article');

/**
 * Obtener todos los recursos para modo offline
 * GET /api/offline/resources
 */
const getAllOfflineResources = async (req, res, next) => {
  try {
    // Obtener todos los recursos necesarios para modo offline
    const [meditations, exercises, audios, articles] = await Promise.all([
      Meditation.getAll(),
      BreathingExercise.getAll(),
      Audio.getAll(),
      Article.getAll()
    ]);

    // Calcular tamaño estimado (para que el cliente sepa cuánto descargará)
    const estimatedSize = {
      meditations: meditations.length * 5, // ~5MB por meditación (estimado)
      audios: audios.length * 3, // ~3MB por audio (estimado)
      exercises: exercises.length * 0.1, // ~100KB por ejercicio (estimado)
      articles: articles.length * 0.5, // ~500KB por artículo (estimado)
      total: (meditations.length * 5) + (audios.length * 3) + 
             (exercises.length * 0.1) + (articles.length * 0.5)
    };

    res.status(200).json({
      message: 'Recursos offline obtenidos exitosamente',
      version: '1.0.0', // Versión de los recursos (para caché)
      lastUpdated: new Date().toISOString(),
      estimatedSize: {
        ...estimatedSize,
        unit: 'MB'
      },
      resources: {
        meditations: {
          count: meditations.length,
          items: meditations
        },
        breathingExercises: {
          count: exercises.length,
          items: exercises
        },
        audios: {
          count: audios.length,
          items: audios
        },
        articles: {
          count: articles.length,
          items: articles
        }
      }
    });
  } catch (error) {
    res.status(500);
    next(error);
  }
};

/**
 * Obtener meditaciones para offline
 * GET /api/offline/meditations
 */
const getOfflineMeditations = async (req, res, next) => {
  try {
    const meditations = await Meditation.getAll();

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
 * Obtener ejercicios de respiración para offline
 * GET /api/offline/breathing
 */
const getOfflineBreathing = async (req, res, next) => {
  try {
    const exercises = await BreathingExercise.getAll();

    res.status(200).json({
      count: exercises.length,
      exercises
    });
  } catch (error) {
    res.status(500);
    next(error);
  }
};

/**
 * Obtener audios para offline
 * GET /api/offline/audios
 */
const getOfflineAudios = async (req, res, next) => {
  try {
    const audios = await Audio.getAll();

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
 * Obtener artículos para offline
 * GET /api/offline/articles
 */
const getOfflineArticles = async (req, res, next) => {
  try {
    const articles = await Article.getAll();

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
 * Verificar actualizaciones de recursos
 * GET /api/offline/check-updates
 */
const checkUpdates = async (req, res, next) => {
  try {
    const { version } = req.query;
    const currentVersion = '1.0.0';

    res.status(200).json({
      currentVersion,
      clientVersion: version || 'unknown',
      updateAvailable: version !== currentVersion,
      lastUpdated: new Date().toISOString()
    });
  } catch (error) {
    res.status(500);
    next(error);
  }
};

module.exports = {
  getAllOfflineResources,
  getOfflineMeditations,
  getOfflineBreathing,
  getOfflineAudios,
  getOfflineArticles,
  checkUpdates
};

