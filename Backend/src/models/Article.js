/**
 * Modelo de Artículo
 * Define la estructura y métodos para interactuar con la colección 'articles' en Firestore
 */

const { db } = require('../config/firebase');

const COLLECTION_NAME = 'articles';

class Article {
  /**
   * Obtener todos los artículos
   */
  static async getAll(filters = {}) {
    try {
      let query = db.collection(COLLECTION_NAME);

      // Filtrar por categoría si se proporciona
      if (filters.category) {
        query = query.where('category', '==', filters.category);
      }

      const snapshot = await query
        .orderBy('publishedAt', 'desc')
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
   * Obtener un artículo por ID
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
   * Obtener artículos por categoría
   */
  static async getByCategory(category) {
    try {
      const snapshot = await db.collection(COLLECTION_NAME)
        .where('category', '==', category)
        .orderBy('publishedAt', 'desc')
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
   * Crear un nuevo artículo
   */
  static async create(articleData) {
    try {
      const articleRef = db.collection(COLLECTION_NAME).doc();
      const newArticle = {
        ...articleData,
        publishedAt: articleData.publishedAt || new Date().toISOString(),
        createdAt: new Date().toISOString(),
        updatedAt: new Date().toISOString()
      };

      await articleRef.set(newArticle);

      return {
        id: articleRef.id,
        ...newArticle
      };
    } catch (error) {
      throw error;
    }
  }

  /**
   * Actualizar artículo
   */
  static async update(id, updateData) {
    try {
      const articleRef = db.collection(COLLECTION_NAME).doc(id);
      const doc = await articleRef.get();

      if (!doc.exists) {
        throw new Error('Artículo no encontrado');
      }

      const updatedData = {
        ...updateData,
        updatedAt: new Date().toISOString()
      };

      await articleRef.update(updatedData);

      return await this.getById(id);
    } catch (error) {
      throw error;
    }
  }

  /**
   * Eliminar artículo
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
   * Buscar artículos por texto
   */
  static async search(searchTerm) {
    try {
      // Nota: Firestore no soporta búsqueda de texto completo nativamente
      // Esta es una implementación básica que busca en el título
      const snapshot = await db.collection(COLLECTION_NAME).get();

      if (snapshot.empty) {
        return [];
      }

      const searchLower = searchTerm.toLowerCase();
      return snapshot.docs
        .map(doc => ({
          id: doc.id,
          ...doc.data()
        }))
        .filter(article => 
          article.title.toLowerCase().includes(searchLower) ||
          (article.content && article.content.toLowerCase().includes(searchLower))
        );
    } catch (error) {
      throw error;
    }
  }
}

module.exports = Article;

