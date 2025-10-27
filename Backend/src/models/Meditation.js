/**
 * Modelo de Meditación
 * Define la estructura y métodos para interactuar con la colección 'meditations' en Firestore
 */

const { db } = require('../config/firebase');

const COLLECTION_NAME = 'meditations';

class Meditation {
  /**
   * Obtener todas las meditaciones
   */
  static async getAll(filters = {}) {
    try {
      let query = db.collection(COLLECTION_NAME);

      // Aplicar filtros opcionales
      if (filters.category) {
        query = query.where('category', '==', filters.category);
      }

      if (filters.duration) {
        query = query.where('duration', '<=', filters.duration);
      }

      const snapshot = await query.orderBy('createdAt', 'desc').get();

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
   * Obtener una meditación por ID
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
   * Crear una nueva meditación (admin)
   */
  static async create(meditationData) {
    try {
      const meditationRef = db.collection(COLLECTION_NAME).doc();
      const newMeditation = {
        ...meditationData,
        createdAt: new Date().toISOString(),
        updatedAt: new Date().toISOString()
      };

      await meditationRef.set(newMeditation);

      return {
        id: meditationRef.id,
        ...newMeditation
      };
    } catch (error) {
      throw error;
    }
  }

  /**
   * Obtener meditaciones por categoría
   */
  static async getByCategory(category) {
    try {
      const snapshot = await db.collection(COLLECTION_NAME)
        .where('category', '==', category)
        .orderBy('createdAt', 'desc')
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
   * Actualizar meditación
   */
  static async update(id, updateData) {
    try {
      const meditationRef = db.collection(COLLECTION_NAME).doc(id);
      const doc = await meditationRef.get();

      if (!doc.exists) {
        throw new Error('Meditación no encontrada');
      }

      const updatedData = {
        ...updateData,
        updatedAt: new Date().toISOString()
      };

      await meditationRef.update(updatedData);

      return await this.getById(id);
    } catch (error) {
      throw error;
    }
  }

  /**
   * Eliminar meditación
   */
  static async delete(id) {
    try {
      await db.collection(COLLECTION_NAME).doc(id).delete();
      return true;
    } catch (error) {
      throw error;
    }
  }
}

module.exports = Meditation;

