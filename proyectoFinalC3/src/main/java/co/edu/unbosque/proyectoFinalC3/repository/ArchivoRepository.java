package co.edu.unbosque.proyectoFinalC3.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import co.edu.unbosque.proyectoFinalC3.model.Archivo;

/**
 * Repositorio para la entidad Archivo que proporciona operaciones CRUD básicas
 * y consultas personalizadas relacionadas con archivos en el sistema.
 * <p>
 * Extiende {@link CrudRepository} heredando operaciones básicas de acceso a datos.
 * </p>
 */
public interface ArchivoRepository extends CrudRepository<Archivo, Integer> {
    
    /**
     * Busca archivos asociados a un usuario específico.
     * 
     * @param usuarioId ID del usuario
     * @return Lista de archivos pertenecientes al usuario
     */
    List<Archivo> findByUsuarioId(Integer usuarioId);

    /**
     * Busca un archivo por su ID.
     * 
     * @param id ID del archivo a buscar
     * @return Optional que contiene el archivo si es encontrado
     */
    @Override
    Optional<Archivo> findById(Integer id);
    
    /**
     * Elimina un archivo por su ID.
     * 
     * @param id ID del archivo a eliminar
     */
    @Override
    void deleteById(Integer id);
    
}
