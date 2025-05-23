/**
 * Paquete que contiene todas las configuraciones y componentes relacionados con la seguridad.
 * <p>
 * Este paquete incluye:
 * <ul>
 *   <li>Configuración principal de seguridad</li>
 *   <li>Filtros de autenticación JWT</li>
 *   <li>Utilidades para manejo de tokens</li>
 *   <li>Servicios de detalles de usuario</li>
 * </ul>
 * </p>
 * 
 * <p>Componentes principales:</p>
 * <ul>
 *   <li>{@link co.edu.unbosque.proyectoFinalC3.security.SecurityConfig} - Configuración central de seguridad</li>
 *   <li>{@link co.edu.unbosque.proyectoFinalC3.security.JwtAuthenticationFilter} - Filtro para autenticación JWT</li>
 *   <li>{@link co.edu.unbosque.proyectoFinalC3.security.JwtUtil} - Utilidades para generación y validación de tokens</li>
 *   <li>{@link co.edu.unbosque.proyectoFinalC3.security.UsuarioDetailsServiceImpl} - Servicio de detalles de usuario</li>
 * </ul>
 * 
 * <p>Características principales:</p>
 * <ul>
 *   <li>Autenticación basada en JWT</li>
 *   <li>Autorización basada en roles</li>
 *   <li>Configuración CORS</li>
 *   <li>Protección CSRF</li>
 *   <li>Codificación segura de contraseñas</li>
 * </ul>
 */
package co.edu.unbosque.proyectoFinalC3.security;