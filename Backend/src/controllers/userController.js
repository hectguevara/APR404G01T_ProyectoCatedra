/**
 * Controlador de Usuarios
 * Maneja la lógica de negocio para registro, login y perfil de usuarios
 */

const User = require('../models/User');
const { generateToken } = require('../config/jwt');

/**
 * Registrar un nuevo usuario
 * POST /api/users/register
 */
const register = async (req, res, next) => {
  try {
    const { email, password, name } = req.body;

    // Crear usuario
    const user = await User.create({ email, password, name });

    // Generar token JWT
    const token = generateToken({
      uid: user.uid,
      email: user.email
    });

    res.status(201).json({
      message: 'Usuario registrado exitosamente',
      user,
      token
    });
  } catch (error) {
    if (error.message === 'El email ya está registrado') {
      res.status(409);
    } else {
      res.status(400);
    }
    next(error);
  }
};

/**
 * Iniciar sesión
 * POST /api/users/login
 */
const login = async (req, res, next) => {
  try {
    const { email, password } = req.body;

    // Buscar usuario por email
    const user = await User.findByEmail(email);

    if (!user) {
      res.status(401);
      throw new Error('Credenciales inválidas');
    }

    // Verificar contraseña
    const isPasswordValid = await User.verifyPassword(password, user.password);

    if (!isPasswordValid) {
      res.status(401);
      throw new Error('Credenciales inválidas');
    }

    // Generar token JWT
    const token = generateToken({
      uid: user.uid,
      email: user.email
    });

    // Remover contraseña antes de enviar
    const { password: _, ...userWithoutPassword } = user;

    res.status(200).json({
      message: 'Inicio de sesión exitoso',
      user: userWithoutPassword,
      token
    });
  } catch (error) {
    next(error);
  }
};

/**
 * Obtener perfil del usuario autenticado
 * GET /api/users/profile
 */
const getProfile = async (req, res, next) => {
  try {
    const { uid } = req.user;

    const user = await User.findByUid(uid);

    if (!user) {
      res.status(404);
      throw new Error('Usuario no encontrado');
    }

    res.status(200).json({
      user
    });
  } catch (error) {
    next(error);
  }
};

/**
 * Actualizar perfil del usuario
 * PUT /api/users/profile
 */
const updateProfile = async (req, res, next) => {
  try {
    const { uid } = req.user;
    const updateData = req.body;

    const updatedUser = await User.update(uid, updateData);

    res.status(200).json({
      message: 'Perfil actualizado exitosamente',
      user: updatedUser
    });
  } catch (error) {
    res.status(400);
    next(error);
  }
};

/**
 * Eliminar cuenta de usuario
 * DELETE /api/users/profile
 */
const deleteAccount = async (req, res, next) => {
  try {
    const { uid } = req.user;

    await User.delete(uid);

    res.status(200).json({
      message: 'Cuenta eliminada exitosamente'
    });
  } catch (error) {
    res.status(400);
    next(error);
  }
};

module.exports = {
  register,
  login,
  getProfile,
  updateProfile,
  deleteAccount
};

