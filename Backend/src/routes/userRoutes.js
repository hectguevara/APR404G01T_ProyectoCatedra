/**
 * Rutas de Usuarios
 * Define los endpoints para registro, login y gestión de perfil
 */

const express = require('express');
const router = express.Router();
const {
  register,
  login,
  getProfile,
  updateProfile,
  deleteAccount
} = require('../controllers/userController');
const { authenticateToken } = require('../middleware/auth');
const { userValidators } = require('../middleware/validators');
const { handleValidationErrors } = require('../middleware/errorHandler');

/**
 * @swagger
 * /api/users/register:
 *   post:
 *     tags: [Usuarios]
 *     summary: Registrar un nuevo usuario
 *     description: Crea una nueva cuenta de usuario en la plataforma
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             required:
 *               - email
 *               - password
 *               - name
 *             properties:
 *               email:
 *                 type: string
 *                 format: email
 *               password:
 *                 type: string
 *                 minLength: 6
 *               name:
 *                 type: string
 *     responses:
 *       201:
 *         description: Usuario registrado exitosamente
 *       409:
 *         description: El email ya está registrado
 */
router.post(
  '/register',
  userValidators.register,
  handleValidationErrors,
  register
);

/**
 * @swagger
 * /api/users/login:
 *   post:
 *     tags: [Usuarios]
 *     summary: Iniciar sesión
 *     description: Autentica un usuario y retorna un token JWT
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             required:
 *               - email
 *               - password
 *             properties:
 *               email:
 *                 type: string
 *                 format: email
 *               password:
 *                 type: string
 *     responses:
 *       200:
 *         description: Login exitoso
 *       401:
 *         description: Credenciales inválidas
 */
router.post(
  '/login',
  userValidators.login,
  handleValidationErrors,
  login
);

/**
 * @swagger
 * /api/users/profile:
 *   get:
 *     tags: [Usuarios]
 *     summary: Obtener perfil del usuario
 *     security:
 *       - bearerAuth: []
 *     responses:
 *       200:
 *         description: Perfil del usuario
 *       401:
 *         description: No autenticado
 */
router.get('/profile', authenticateToken, getProfile);

/**
 * @swagger
 * /api/users/profile:
 *   put:
 *     tags: [Usuarios]
 *     summary: Actualizar perfil del usuario
 *     security:
 *       - bearerAuth: []
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               name:
 *                 type: string
 *     responses:
 *       200:
 *         description: Perfil actualizado exitosamente
 */
router.put('/profile', authenticateToken, updateProfile);

/**
 * @swagger
 * /api/users/profile:
 *   delete:
 *     tags: [Usuarios]
 *     summary: Eliminar cuenta de usuario
 *     security:
 *       - bearerAuth: []
 *     responses:
 *       200:
 *         description: Cuenta eliminada exitosamente
 */
router.delete('/profile', authenticateToken, deleteAccount);

module.exports = router;

