package co.edu.unbosque.proyectoFinalC3.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.proyectoFinalC3.model.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Integer>{
	
	Optional<Usuario> findByCorreo(String correo);
	
	public Optional<Usuario> findByUsername(String username);

	public void deleteByUsername(String username); 
	
}
