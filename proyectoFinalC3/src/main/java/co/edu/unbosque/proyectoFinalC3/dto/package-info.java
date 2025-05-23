/**
 * Paquete que contiene los objetos de transferencia de datos (DTOs) del sistema.
 * <p>
 * Los DTOs en este paquete representan estructuras de datos utilizadas para:
 * <ul>
 *   <li>Transferencia de información entre capas de la aplicación</li>
 *   <li>Comunicación con el cliente (frontend/API consumers)</li>
 *   <li>Proyección de datos desde la base de datos</li>
 * </ul>
 * 
 * <p>Principales DTOs incluidos:</p>
 * <ul>
 *   <li>{@link co.edu.unbosque.proyectoFinalC3.dto.UsuarioDTO} - Representa un usuario del sistema con sus propiedades básicas, credenciales e historial</li>
 *   <li>{@link co.edu.unbosque.proyectoFinalC3.dto.ArchivoDTO} - Contiene información sobre archivos convertidos en el sistema</li>
 * </ul>
 * 
 * <p>Estos DTOs son utilizados principalmente por los controladores ({@link co.edu.unbosque.proyectoFinalC3.controller})
 * y servicios ({@link co.edu.unbosque.proyectoFinalC3.service}) de la aplicación.</p>
 */
package co.edu.unbosque.proyectoFinalC3.dto;