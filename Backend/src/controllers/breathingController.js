/**
 * Controlador de Ejercicios de Respiración
 * Maneja la lógica de negocio para ejercicios de respiración
 */

const BreathingExercise = require('../models/BreathingExercise');

/**
 * Obtener todos los ejercicios de respiración
 * GET /api/breathing
 */
const getAllExercises = async (req, res, next) => {
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
 * Obtener un ejercicio de respiración por ID
 * GET /api/breathing/:id
 */
const getExerciseById = async (req, res, next) => {
  try {
    const { id } = req.params;

    const exercise = await BreathingExercise.getById(id);

    if (!exercise) {
      res.status(404);
      throw new Error('Ejercicio no encontrado');
    }

    res.status(200).json({
      exercise
    });
  } catch (error) {
    next(error);
  }
};

/**
 * Guardar progreso de ejercicio
 * POST /api/breathing/progress
 */
const saveProgress = async (req, res, next) => {
  try {
    const { uid } = req.user;
    const progressData = req.body;

    const progress = await BreathingExercise.saveProgress(uid, progressData);

    res.status(201).json({
      message: 'Progreso guardado exitosamente',
      progress
    });
  } catch (error) {
    res.status(400);
    next(error);
  }
};

/**
 * Obtener progreso del usuario
 * GET /api/breathing/progress
 */
const getUserProgress = async (req, res, next) => {
  try {
    const { uid } = req.user;
    const { limit } = req.query;

    const progress = await BreathingExercise.getUserProgress(
      uid, 
      limit ? parseInt(limit) : 10
    );

    res.status(200).json({
      count: progress.length,
      progress
    });
  } catch (error) {
    res.status(500);
    next(error);
  }
};

/**
 * Crear nuevo ejercicio (Admin)
 * POST /api/breathing
 */
const createExercise = async (req, res, next) => {
  try {
    const exerciseData = req.body;

    const exercise = await BreathingExercise.create(exerciseData);

    res.status(201).json({
      message: 'Ejercicio creado exitosamente',
      exercise
    });
  } catch (error) {
    res.status(400);
    next(error);
  }
};

module.exports = {
  getAllExercises,
  getExerciseById,
  saveProgress,
  getUserProgress,
  createExercise
};

