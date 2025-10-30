/**
 * Modelo de Seguimiento Emocional
 * Define la estructura y métodos para interactuar con la colección 'tracking' en Firestore
 */

const { db } = require('../config/firebase');

const COLLECTION_NAME = 'tracking';

class Tracking {
  /**
   * Crear un nuevo registro de seguimiento
   */
  static async create(userId, trackingData) {
    try {
      const trackingRef = db.collection(COLLECTION_NAME).doc();
      const newTracking = {
        userId,
        ...trackingData,
        date: trackingData.date || new Date().toISOString(),
        createdAt: new Date().toISOString()
      };

      await trackingRef.set(newTracking);

      return {
        id: trackingRef.id,
        ...newTracking
      };
    } catch (error) {
      throw error;
    }
  }

  /**
   * Obtener registros de seguimiento de un usuario
   */
  static async getUserTracking(userId, options = {}) {
    try {
      let query = db.collection(COLLECTION_NAME)
        .where('userId', '==', userId);

      // Filtrar por rango de fechas si se proporciona
      if (options.startDate) {
        query = query.where('date', '>=', options.startDate);
      }

      if (options.endDate) {
        query = query.where('date', '<=', options.endDate);
      }

      // Limitar resultados
      const limit = options.limit || 30;
      
      // TEMPORAL: Sin orderBy para evitar necesidad de índice
      // query = query.orderBy('date', 'desc').limit(limit);
      query = query.limit(limit);

      const snapshot = await query.get();

      if (snapshot.empty) {
        return [];
      }

      // Ordenar en memoria después de obtener los datos
      const results = snapshot.docs.map(doc => ({
        id: doc.id,
        ...doc.data()
      }));
      
      // Ordenar por fecha descendente (más reciente primero)
      results.sort((a, b) => {
        if (!a.date || !b.date) return 0;
        return new Date(b.date) - new Date(a.date);
      });

      return results;
    } catch (error) {
      throw error;
    }
  }

  /**
   * Obtener estadísticas de estado de ánimo
   */
  static async getStats(userId, options = {}) {
    try {
      let query = db.collection(COLLECTION_NAME)
        .where('userId', '==', userId);

      // Filtrar por rango de fechas
      if (options.startDate) {
        query = query.where('date', '>=', options.startDate);
      }

      if (options.endDate) {
        query = query.where('date', '<=', options.endDate);
      }

      const snapshot = await query.get();

      if (snapshot.empty) {
        return {
          totalEntries: 0,
          moodDistribution: {},
          averageMood: null,
          commonActivities: []
        };
      }

      const entries = snapshot.docs.map(doc => doc.data());

      // Calcular distribución de estados de ánimo
      const moodDistribution = entries.reduce((acc, entry) => {
        acc[entry.mood] = (acc[entry.mood] || 0) + 1;
        return acc;
      }, {});

      // Calcular puntuación promedio de ánimo
      const moodScores = {
        'muy_mal': 1,
        'mal': 2,
        'neutral': 3,
        'bien': 4,
        'muy_bien': 5
      };

      const totalScore = entries.reduce((sum, entry) => 
        sum + (moodScores[entry.mood] || 3), 0
      );
      const averageMood = (totalScore / entries.length).toFixed(2);

      // Actividades más comunes
      const activityCounts = {};
      entries.forEach(entry => {
        if (entry.activities && Array.isArray(entry.activities)) {
          entry.activities.forEach(activity => {
            activityCounts[activity] = (activityCounts[activity] || 0) + 1;
          });
        }
      });

      const commonActivities = Object.entries(activityCounts)
        .sort((a, b) => b[1] - a[1])
        .slice(0, 5)
        .map(([activity, count]) => ({ activity, count }));

      return {
        totalEntries: entries.length,
        moodDistribution,
        averageMood: parseFloat(averageMood),
        commonActivities,
        period: {
          start: options.startDate || entries[entries.length - 1].date,
          end: options.endDate || entries[0].date
        }
      };
    } catch (error) {
      throw error;
    }
  }

  /**
   * Obtener un registro específico
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
   * Actualizar un registro
   */
  static async update(id, userId, updateData) {
    try {
      const trackingRef = db.collection(COLLECTION_NAME).doc(id);
      const doc = await trackingRef.get();

      if (!doc.exists) {
        throw new Error('Registro no encontrado');
      }

      const currentData = doc.data();

      // Verificar que el usuario sea el propietario
      if (currentData.userId !== userId) {
        throw new Error('No autorizado para actualizar este registro');
      }

      const updatedData = {
        ...updateData,
        updatedAt: new Date().toISOString()
      };

      await trackingRef.update(updatedData);

      return await this.getById(id);
    } catch (error) {
      throw error;
    }
  }

  /**
   * Eliminar un registro
   */
  static async delete(id, userId) {
    try {
      const doc = await db.collection(COLLECTION_NAME).doc(id).get();

      if (!doc.exists) {
        throw new Error('Registro no encontrado');
      }

      const currentData = doc.data();

      // Verificar que el usuario sea el propietario
      if (currentData.userId !== userId) {
        throw new Error('No autorizado para eliminar este registro');
      }

      await db.collection(COLLECTION_NAME).doc(id).delete();
      return true;
    } catch (error) {
      throw error;
    }
  }
}

module.exports = Tracking;

