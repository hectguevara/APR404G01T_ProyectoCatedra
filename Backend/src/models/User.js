/**
 * Modelo de Usuario
 * Define la estructura y métodos para interactuar con la colección 'users' en Firestore
 */

const { db } = require('../config/firebase');
const bcrypt = require('bcrypt');

const COLLECTION_NAME = 'users';

class User {
  /**
   * Crear un nuevo usuario en Firestore
   */
  static async create(userData) {
    try {
      const { email, password, name } = userData;

      // Verificar si el usuario ya existe
      const existingUser = await this.findByEmail(email);
      if (existingUser) {
        throw new Error('El email ya está registrado');
      }

      // Hash de la contraseña
      const hashedPassword = await bcrypt.hash(password, 10);

      // Crear documento del usuario
      const userRef = db.collection(COLLECTION_NAME).doc();
      const newUser = {
        uid: userRef.id,
        email: email.toLowerCase(),
        password: hashedPassword,
        name,
        createdAt: new Date().toISOString(),
        updatedAt: new Date().toISOString()
      };

      await userRef.set(newUser);

      // Retornar usuario sin contraseña
      const { password: _, ...userWithoutPassword } = newUser;
      return userWithoutPassword;
    } catch (error) {
      throw error;
    }
  }

  /**
   * Buscar usuario por email
   */
  static async findByEmail(email) {
    try {
      const snapshot = await db.collection(COLLECTION_NAME)
        .where('email', '==', email.toLowerCase())
        .limit(1)
        .get();

      if (snapshot.empty) {
        return null;
      }

      return snapshot.docs[0].data();
    } catch (error) {
      throw error;
    }
  }

  /**
   * Buscar usuario por UID
   */
  static async findByUid(uid) {
    try {
      const doc = await db.collection(COLLECTION_NAME).doc(uid).get();

      if (!doc.exists) {
        return null;
      }

      const { password, ...userWithoutPassword } = doc.data();
      return userWithoutPassword;
    } catch (error) {
      throw error;
    }
  }

  /**
   * Verificar contraseña
   */
  static async verifyPassword(plainPassword, hashedPassword) {
    try {
      return await bcrypt.compare(plainPassword, hashedPassword);
    } catch (error) {
      throw error;
    }
  }

  /**
   * Actualizar perfil de usuario
   */
  static async update(uid, updateData) {
    try {
      const userRef = db.collection(COLLECTION_NAME).doc(uid);
      const doc = await userRef.get();

      if (!doc.exists) {
        throw new Error('Usuario no encontrado');
      }

      const updatedData = {
        ...updateData,
        updatedAt: new Date().toISOString()
      };

      // No permitir actualizar email o password directamente
      delete updatedData.email;
      delete updatedData.password;
      delete updatedData.uid;

      await userRef.update(updatedData);

      return await this.findByUid(uid);
    } catch (error) {
      throw error;
    }
  }

  /**
   * Eliminar usuario
   */
  static async delete(uid) {
    try {
      await db.collection(COLLECTION_NAME).doc(uid).delete();
      return true;
    } catch (error) {
      throw error;
    }
  }
}

module.exports = User;

