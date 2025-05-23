/**
 * Paquete que contiene las entidades principales del sistema.
 * <p>
 * Este paquete incluye las clases que representan las entidades de negocio
 * y que son mapeadas a tablas en la base de datos mediante JPA.
 * </p>
 * 
 * <p>Principales entidades:</p>
 * <ul>
 *   <li>{@link co.edu.unbosque.proyectoFinalC3.model.Usuario} - Representa a los usuarios del sistema con sus credenciales y roles</li>
 *   <li>{@link co.edu.unbosque.proyectoFinalC3.model.Archivo} - Representa los archivos convertidos por los usuarios</li>
 * </ul>
 * 
 * <p>Características comunes:</p>
 * <ul>
 *   <li>Anotaciones JPA para mapeo objeto-relacional</li>
 *   <li>Relaciones bien definidas entre entidades</li>
 *   <li>Validación de datos</li>
 *   <li>Soporte para serialización JSON</li>
 * </ul>
 */
package co.edu.unbosque.proyectoFinalC3.model;