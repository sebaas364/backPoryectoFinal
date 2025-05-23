/**
 * Paquete que contiene los repositorios del sistema para acceso a datos.
 * <p>
 * Este paquete incluye interfaces que extienden {@link org.springframework.data.repository.CrudRepository}
 * proporcionando operaciones CRUD básicas y consultas personalizadas para cada entidad.
 * </p>
 * 
 * <p>Repositorios principales:</p>
 * <ul>
 *   <li>{@link co.edu.unbosque.proyectoFinalC3.repository.ArchivoRepository} - Operaciones para la entidad Archivo</li>
 *   <li>{@link co.edu.unbosque.proyectoFinalC3.repository.UsuarioRepository} - Operaciones para la entidad Usuario</li>
 * </ul>
 * 
 * <p>Características comunes:</p>
 * <ul>
 *   <li>Métodos de consulta derivados de nombres</li>
 *   <li>Soporte para operaciones CRUD básicas</li>
 *   <li>Integración con Spring Data JPA</li>
 * </ul>
 */
package co.edu.unbosque.proyectoFinalC3.repository;