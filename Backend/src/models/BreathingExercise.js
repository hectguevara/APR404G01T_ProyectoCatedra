/**
 * Modelo de Ejercicio de Respiración
 * Define la estructura y métodos para interactuar con la colección 'breathing_exercises' en Firestore
 */

const { db } = require('../config/firebase');

const COLLECTION_NAME = 'breathing_exercises';

class BreathingExercise {
  /**
   * Obtener todos los ejercicios de respiración
   */
  static async getAll() {
    try {
      const snapshot = await db.collection(COLLECTION_NAME)
        .orderBy('duration', 'asc')
        .get();

      if (snapshot.empty) {
        return [];
      }

      return snapshot.docs.map(doc => ({
        id: doc.id,
        ...doc.data()
      }));
    } catch (error) {
      throw error;
    }
  }

  /**
   * Obtener un ejercicio por ID
   */
  static async getById(id) {
    try {
      const doc = await db.collection(COLLECTION_NAME).doc(id).get();

      if (!doc.exists) {
        return null;
      }

      return {
        id: doc.id,
        ...doc.data()
      };
    } catch (error) {
      throw error;
    }
  }

  /**
   * Crear un nuevo ejercicio de respiración
   */
  static async create(exerciseData) {
    try {
      const exerciseRef = db.collection(COLLECTION_NAME).doc();
      const newExercise = {
        ...exerciseData,
        createdAt: new Date().toISOString(),
        updatedAt: new Date().toISOString()
      };

      await exerciseRef.set(newExercise);

      return {
        id: exerciseRef.id,
        ...newExercise
      };
    } catch (error) {
      throw error;
    }
  }

  /**
   * Guardar progreso de ejercicio para un usuario
   */
  static async saveProgress(userId, progressData) {
    try {
      const progressRef = db.collection('breathing_progress').doc();
      const newProgress = {
        userId,
        ...progressData,
        completedAt: new Date().toISOString()
      };

      await progressRef.set(newProgress);

      return {
        id: progressRef.id,
        ...newProgress
      };
    } catch (error) {
      throw error;
    }
  }

  /**
   * Obtener progreso de usuario
   */
  static async getUserProgress(userId, limit = 10) {
    try {
      const snapshot = await db.collection('breathing_progress')
        .where('userId', '==', userId)
        .orderBy('completedAt', 'desc')
        .limit(limit)
        .get();

      if (snapshot.empty) {
        return [];
      }

      return snapshot.docs.map(doc => ({
        id: doc.id,
        ...doc.data()
      }));
    } catch (error) {
      throw error;
    }
  }
}

module.exports = BreathingExercise;

