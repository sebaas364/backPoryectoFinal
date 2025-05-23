package co.edu.unbosque.proyectoFinalC3.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import co.edu.unbosque.proyectoFinalC3.repository.UsuarioRepository;

/**
 * Implementación personalizada de {@link UserDetailsService} que carga los
 * detalles de un usuario desde la base de datos para la autenticación con
 * Spring Security.
 * <p>
 * Esta clase conecta el sistema de autenticación de Spring Security con los
 * usuarios almacenados en la base de datos a través del
 * {@link UsuarioRepository}.
 * </p>
 */
@Service
public class UsuarioDetailsServiceImpl implements UserDetailsService {

	private final UsuarioRepository usuarioRepository;

	/**
	 * Constructor que inyecta el repositorio de usuarios.
	 * 
	 * @param userRepository Repositorio de usuarios para acceder a los datos
	 */
	public UsuarioDetailsServiceImpl(UsuarioRepository userRepository) {
		this.usuarioRepository = userRepository;
	}

	/**
	 * Carga los detalles del usuario por su nombre de usuario.
	 * 
	 * @param username Nombre de usuario a buscar
	 * @return Implementación de {@link UserDetails} con los datos del usuario
	 * @throws UsernameNotFoundException Si no se encuentra el usuario
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return usuarioRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
	}

}
