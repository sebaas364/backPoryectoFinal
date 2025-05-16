package co.edu.unbosque.proyectoFinalC3.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.proyectoFinalC3.model.Archivo;

public interface ArchivoRepository extends CrudRepository<Archivo, Integer> {
    
    List<Archivo> findByUsuarioId(Integer usuarioId);

    Optional<Archivo> findById(Integer id);
    
    void deleteById(Integer id);
}
