/**
 * Modelo de Audio
 * Define la estructura y métodos para interactuar con la colección 'audios' en Firestore
 */

const { db } = require('../config/firebase');

const COLLECTION_NAME = 'audios';

class Audio {
  /**
   * Obtener todos los audios relajantes
   */
  static async getAll(filters = {}) {
    try {
      let query = db.collection(COLLECTION_NAME);

      // Filtrar por categoría si se proporciona
      if (filters.category) {
        query = query.where('category', '==', filters.category);
      }

      // Filtrar por tipo si se proporciona (naturaleza, música, ambientes, etc.)
      if (filters.type) {
        query = query.where('type', '==', filters.type);
      }

      const snapshot = await query
        .orderBy('title', 'asc')
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
   * Obtener un audio por ID
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
   * Obtener audios por categoría
   */
  static async getByCategory(category) {
    try {
      const snapshot = await db.collection(COLLECTION_NAME)
        .where('category', '==', category)
        .orderBy('title', 'asc')
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
   * Crear un nuevo audio
   */
  static async create(audioData) {
    try {
      const audioRef = db.collection(COLLECTION_NAME).doc();
      const newAudio = {
        ...audioData,
        createdAt: new Date().toISOString(),
        updatedAt: new Date().toISOString()
      };

      await audioRef.set(newAudio);

      return {
        id: audioRef.id,
        ...newAudio
      };
    } catch (error) {
      throw error;
    }
  }

  /**
   * Actualizar audio
   */
  static async update(id, updateData) {
    try {
      const audioRef = db.collection(COLLECTION_NAME).doc(id);
      const doc = await audioRef.get();

      if (!doc.exists) {
        throw new Error('Audio no encontrado');
      }

      const updatedData = {
        ...updateData,
        updatedAt: new Date().toISOString()
      };

      await audioRef.update(updatedData);

      return await this.getById(id);
    } catch (error) {
      throw error;
    }
  }

  /**
   * Eliminar audio
   */
  static async delete(id) {
    try {
      await db.collection(COLLECTION_NAME).doc(id).delete();
      return true;
    } catch (error) {
      throw error;
    }
  }

  /**
   * Obtener categorías disponibles
   */
  static async getCategories() {
    try {
      const snapshot = await db.collection(COLLECTION_NAME).get();

      if (snapshot.empty) {
        return [];
      }

      const categories = new Set();
      snapshot.docs.forEach(doc => {
        const data = doc.data();
        if (data.category) {
          categories.add(data.category);
        }
      });

      return Array.from(categories).sort();
    } catch (error) {
      throw error;
    }
  }
}

module.exports = Audio;

