package co.edu.unbosque.proyectoFinalC3.service;

import co.edu.unbosque.proyectoFinalC3.dto.UsuarioDTO;
import co.edu.unbosque.proyectoFinalC3.model.Usuario;
import co.edu.unbosque.proyectoFinalC3.repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio para la gestión de usuarios en el sistema.
 * <p>
 * Esta clase contiene la lógica de negocio para operaciones CRUD de usuarios,
 * validación de credenciales y manejo de códigos de verificación.
 * </p>
 */
@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private final Map<String, VerificationCode> verificationCodes = new HashMap<>();

	/**
	 * Constructor por defecto.
	 */
	public UsuarioService() {

	}

	/**
	 * Cuenta el número total de usuarios en el sistema.
	 * 
	 * @return Número total de usuarios
	 */
	public long count() {
		return usuarioRepository.count();
	}

	/**
	 * Verifica si un usuario existe por su ID.
	 * 
	 * @param id ID del usuario a verificar
	 * @return true si el usuario existe, false en caso contrario
	 */
	public boolean exist(Integer id) {
		return usuarioRepository.existsById(id);
	}

	/**
	 * Crea un nuevo usuario en el sistema.
	 * 
	 * @param data DTO con los datos del nuevo usuario
	 * @return Código de resultado:
	 *         <ul>
	 *         <li>0: Éxito</li>
	 *         <li>1: Nombre de usuario ya existe</li>
	 *         </ul>
	 */
	public int create(UsuarioDTO data) {
		Usuario entity = modelMapper.map(data, Usuario.class);
		if (findUsernameAlreadyTaken(entity.getUsername())) {
			return 1;
		} else {
			entity.setPassword(passwordEncoder.encode(entity.getPassword()));
			if (data.getRole() != null) {
				entity.setRole(Usuario.Role.valueOf(data.getRole().toUpperCase()));
			}
			usuarioRepository.save(entity);
			return 0;
		}
	}

	/**
	 * Obtiene todos los usuarios del sistema.
	 * 
	 * @return Lista de DTOs de usuarios
	 */
	@Transactional(readOnly = true)
	public List<UsuarioDTO> getAll() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		usuarios.forEach(u -> {
			modelMapper.map(u, UsuarioDTO.class);
			Hibernate.initialize(u.getHistorial());
		});
		return usuarios.stream().map(u -> modelMapper.map(u, UsuarioDTO.class)).collect(Collectors.toList());
	}

	/**
	 * Elimina un usuario por su ID.
	 * 
	 * @param id ID del usuario a eliminar
	 * @return Código de resultado:
	 *         <ul>
	 *         <li>0: Éxito</li>
	 *         <li>1: Usuario no encontrado</li>
	 *         </ul>
	 */
	public int deleteById(Integer id) {
		Optional<Usuario> found = usuarioRepository.findById(id);
		if (found.isPresent()) {
			usuarioRepository.delete(found.get());
			return 0;
		} else {
			return 1;
		}
	}

	/**
	 * Elimina un usuario por su nombre de usuario.
	 * 
	 * @param username Nombre de usuario a eliminar
	 * @return Código de resultado:
	 *         <ul>
	 *         <li>0: Éxito</li>
	 *         <li>1: Usuario no encontrado</li>
	 *         </ul>
	 */
	public int deleteByUsername(String username) {
		Optional<Usuario> found = usuarioRepository.findByUsername(username);
		if (found.isPresent()) {
			usuarioRepository.delete(found.get());
			return 0;
		} else {
			return 1;
		}
	}

	/**
	 * Actualiza un usuario existente.
	 * 
	 * @param id      ID del usuario a actualizar
	 * @param newData DTO con los nuevos datos
	 * @return Código de resultado:
	 *         <ul>
	 *         <li>0: Éxito</li>
	 *         <li>1: Nombre de usuario ya existe</li>
	 *         <li>2: Usuario no encontrado</li>
	 *         </ul>
	 */
	public int updateById(Integer id, UsuarioDTO newData) {
		Optional<Usuario> found = usuarioRepository.findById(id);
		if (found.isEmpty()) {
			return 2;
		}
		Optional<Usuario> newFound = usuarioRepository.findByUsername(newData.getUsername());
		if (newFound.isPresent() && !newFound.get().getId().equals(id)) {
			return 1;
		}
		Usuario temp = found.get();
		temp.setUsername(newData.getUsername());
		if (newData.getPassword() != null && !newData.getPassword().isBlank()) {
			temp.setPassword(passwordEncoder.encode(newData.getPassword()));
		}
		if (newData.getRole() != null) {
			temp.setRole(Usuario.Role.valueOf(newData.getRole().toUpperCase()));
		}
		usuarioRepository.save(temp);
		return 0;
	}

	/**
	 * Obtiene un usuario por su ID.
	 * 
	 * @param id ID del usuario a buscar
	 * @return DTO del usuario o null si no se encuentra
	 */
	@Transactional(readOnly = true)
	public UsuarioDTO getById(Integer id) {
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		if (usuario.isPresent()) {
			Hibernate.initialize(usuario.get().getHistorial());
			return modelMapper.map(usuario.get(), UsuarioDTO.class);
		}
		return null;
	}

	/**
	 * Verifica si un nombre de usuario ya está en uso.
	 * 
	 * @param username Nombre de usuario a verificar
	 * @return true si el nombre ya está en uso, false en caso contrario
	 */
	public boolean findUsernameAlreadyTaken(String username) {
		Optional<Usuario> found = usuarioRepository.findByUsername(username);
		return found.isPresent();
	}

	/**
	 * Valida las credenciales de un usuario.
	 * 
	 * @param username Nombre de usuario
	 * @param password Contraseña
	 * @return Código de resultado:
	 *         <ul>
	 *         <li>0: Credenciales válidas</li>
	 *         <li>1: Credenciales inválidas</li>
	 *         </ul>
	 */
	public int validateCredentials(String username, String password) {
		Optional<Usuario> userOpt = usuarioRepository.findByUsername(username);
		if (userOpt.isPresent()) {
			Usuario user = userOpt.get();
			if (passwordEncoder.matches(password, user.getPassword())) {
				return 0;
			}
		}
		return 1;
	}

	/**
	 * Almacena un código de verificación para un correo electrónico.
	 * 
	 * @param email Correo electrónico del usuario
	 * @param code  Código de verificación
	 */
	public void saveVerificationCode(String email, String code) {
		verificationCodes.put(email, new VerificationCode(code, LocalDateTime.now().plusMinutes(10)));
	}

	/**
	 * Valida un código de verificación para un correo electrónico.
	 * 
	 * @param email     Correo electrónico del usuario
	 * @param inputCode Código ingresado por el usuario
	 * @return true si el código es válido, false en caso contrario
	 */
	public boolean validateVerificationCode(String email, String inputCode) {
		VerificationCode storedCode = verificationCodes.get(email);
		if (storedCode == null) {
			return false;
		}
		if (LocalDateTime.now().isAfter(storedCode.getExpirationTime())) {
			verificationCodes.remove(email);
			return false;
		}
		if (storedCode.getCode().equals(inputCode)) {
			verificationCodes.remove(email);
			return true;
		}
		return false;
	}

	/**
	 * Clase interna para representar un código de verificación con su tiempo de
	 * expiración.
	 */
	private static class VerificationCode {
		private final String code;
		private final LocalDateTime expirationTime;

		public VerificationCode(String code, LocalDateTime expirationTime) {
			this.code = code;
			this.expirationTime = expirationTime;
		}

		public String getCode() {
			return code;
		}

		public LocalDateTime getExpirationTime() {
			return expirationTime;
		}
	}

}
