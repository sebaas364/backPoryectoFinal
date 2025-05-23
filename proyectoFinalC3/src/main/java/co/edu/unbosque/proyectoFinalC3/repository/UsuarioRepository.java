package co.edu.unbosque.proyectoFinalC3.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import co.edu.unbosque.proyectoFinalC3.model.Usuario;

/**
 * Repositorio para la entidad Usuario que proporciona operaciones CRUD básicas
 * y consultas personalizadas para la gestión de usuarios en el sistema.
 * <p>
 * Extiende {@link CrudRepository} proporcionando operaciones estándar de persistencia
 * y métodos adicionales específicos para la entidad Usuario.
 */
public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {

    /**
     * Busca un usuario por su correo electrónico.
     * 
     * @param correo Correo electrónico del usuario a buscar
     * @return {@link Optional} que contiene el usuario si es encontrado
     */
    Optional<Usuario> findByCorreo(String correo);
    
    /**
     * Busca un usuario por su nombre de usuario.
     * 
     * @param username Nombre de usuario a buscar
     * @return {@link Optional} que contiene el usuario si es encontrado
     */
    Optional<Usuario> findByUsername(String username);

    /**
     * Elimina un usuario por su nombre de usuario.
     * 
     * @param username Nombre de usuario del usuario a eliminar
     */
    void deleteByUsername(String username);
    
    /**
     * Obtiene todos los usuarios registrados en el sistema.
     * 
     * @return Lista de todos los usuarios
     */
    @Override
    List<Usuario> findAll();
    
}
