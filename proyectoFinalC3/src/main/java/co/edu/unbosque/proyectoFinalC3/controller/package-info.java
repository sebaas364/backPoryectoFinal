/**
 * Paquete que contiene los controladores REST de la aplicación ProyectoFinalC3.
 * <p>
 * Este paquete incluye los siguientes controladores principales:
 * </p>
 * <ul>
 *   <li>{@link co.edu.unbosque.proyectoFinalC3.controller.AuthController} - Maneja autenticación y registro de usuarios</li>
 *   <li>{@link co.edu.unbosque.proyectoFinalC3.controller.UsuarioController} - Gestiona operaciones CRUD de usuarios</li>
 *   <li>{@link co.edu.unbosque.proyectoFinalC3.controller.ArchivoController} - Controla operaciones de conversión de archivos</li>
 * </ul>
 * <p>
 * Todos los endpoints requieren autenticación JWT excepto los relacionados con autenticación.
 * </p>
 * 
 * @see co.edu.unbosque.proyectoFinalC3.security.JwtUtil
 * @see org.springframework.web.bind.annotation.RestController
 */
package co.edu.unbosque.proyectoFinalC3.controller;