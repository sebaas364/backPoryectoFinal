package co.edu.unbosque.proyectoFinalC3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.proyectoFinalC3.dto.UsuarioDTO;
import co.edu.unbosque.proyectoFinalC3.service.UsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.List;

/**
 * Controlador REST para operaciones CRUD de usuarios.
 * <p>
 * Proporciona endpoints para gestionar usuarios con autenticación JWT
 * requerida.
 * </p>
 */
@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = { "*" })
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	public UsuarioController() {

	}

	/**
	 * Crea un nuevo usuario desde JSON.
	 * 
	 * @param newUser DTO con datos del nuevo usuario
	 * @return ResponseEntity con resultado de la operación
	 */
	@PostMapping(path = "/createjson", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createWithJSON(@RequestBody UsuarioDTO newUser) {
		if (newUser.getUsername().contains("<") || newUser.getUsername().contains(">")) {
			return ResponseEntity.badRequest().body("Solicitud con caracteres inválidos");
		}
		int status = usuarioService.create(newUser);
		return status == 0 ? ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado exitosamente")
				: ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
						.body("Error al crear el usuario, posiblemente el nombre ya está en uso");
	}

	/**
	 * Crea un nuevo usuario desde parámetros de formulario.
	 * 
	 * @param username Nombre de usuario
	 * @param correo   Correo electrónico
	 * @param password Contraseña
	 * @param role     Rol del usuario
	 * @return ResponseEntity con resultado de la operación
	 */
	@PostMapping("/create")
	public ResponseEntity<String> create(@RequestParam String username, @RequestParam String correo,
			@RequestParam String password, @RequestParam(required = true) String role) {
		UsuarioDTO newUser = new UsuarioDTO(username, correo, password, role, false, false, null);
		int status = usuarioService.create(newUser);
		return status == 0 ? ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado exitosamente")
				: ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
						.body("Error al crear el usuario, posiblemente el nombre ya está en uso");
	}

	/**
	 * Obtiene todos los usuarios.
	 * 
	 * @return Lista de usuarios o 204 No Content si no hay usuarios
	 */
	@GetMapping("/getall")
	public ResponseEntity<List<UsuarioDTO>> getAll() {
		List<UsuarioDTO> users = usuarioService.getAll();
		return users.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(users);
	}

	/**
	 * Cuenta el total de usuarios.
	 * 
	 * @return Número total de usuarios o 204 No Content si no hay usuarios
	 */
	@GetMapping("/count")
	public ResponseEntity<Long> countAll() {
		Long count = usuarioService.count();
		return count == 0 ? ResponseEntity.noContent().build() : ResponseEntity.ok(count);
	}

	/**
	 * Verifica si existe un usuario por ID.
	 * 
	 * @param id ID del usuario
	 * @return true si existe, 204 No Content si no existe
	 */
	@GetMapping("/exists/{id}")
	public ResponseEntity<Boolean> exists(@PathVariable Integer id) {
		return usuarioService.exist(id) ? ResponseEntity.ok(true) : ResponseEntity.noContent().build();
	}

	/**
	 * Obtiene un usuario por ID.
	 * 
	 * @param id ID del usuario
	 * @return Usuario encontrado o 404 Not Found
	 */
	@GetMapping("/getbyid/{id}")
	public ResponseEntity<UsuarioDTO> getById(@PathVariable Integer id) {
		UsuarioDTO found = usuarioService.getById(id);
		return found != null ? ResponseEntity.ok(found) : ResponseEntity.notFound().build();
	}

	/**
	 * Actualiza un usuario desde JSON.
	 * 
	 * @param id      ID del usuario a actualizar
	 * @param newUser DTO con nuevos datos del usuario
	 * @return ResponseEntity con resultado de la operación
	 */
	@PutMapping(path = "/updatejson", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateWithJSON(@RequestParam Integer id, @RequestBody UsuarioDTO newUser) {
		int status = usuarioService.updateById(id, newUser);
		switch (status) {
		case 0:
			return ResponseEntity.ok("Usuario actualizado exitosamente");
		case 1:
			return ResponseEntity.status(HttpStatus.IM_USED).body("El nuevo nombre de usuario ya está en uso");
		case 2:
			return ResponseEntity.notFound().build();
		default:
			return ResponseEntity.badRequest().body("Error al actualizar");
		}
	}

	/**
	 * Actualiza un usuario desde parámetros de formulario.
	 * 
	 * @param id          ID del usuario
	 * @param newUsername Nuevo nombre de usuario
	 * @param newCorreo   Nuevo correo electrónico
	 * @param newPassword Nueva contraseña
	 * @param role        Nuevo rol
	 * @return ResponseEntity con resultado de la operación
	 */
	@PutMapping("/update")
	public ResponseEntity<String> update(@RequestParam Integer id, @RequestParam String newUsername,
			@RequestParam String newCorreo, @RequestParam String newPassword,
			@RequestParam(required = true) String role) {
		UsuarioDTO newUser = new UsuarioDTO(newUsername, newCorreo, newPassword, role, false, false, null);
		int status = usuarioService.updateById(id, newUser);
		switch (status) {
		case 0:
			return ResponseEntity.ok("Usuario actualizado exitosamente");
		case 1:
			return ResponseEntity.status(HttpStatus.IM_USED).body("El nuevo nombre de usuario ya está en uso");
		case 2:
			return ResponseEntity.notFound().build();
		default:
			return ResponseEntity.badRequest().body("Error al actualizar");
		}
	}

	/**
	 * Elimina un usuario por ID.
	 * 
	 * @param id ID del usuario a eliminar
	 * @return ResponseEntity con resultado de la operación
	 */
	@DeleteMapping("/deletebyid/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Integer id) {
		return usuarioService.deleteById(id) == 0 ? ResponseEntity.ok("Usuario eliminado exitosamente")
				: ResponseEntity.notFound().build();
	}

	/**
	 * Elimina un usuario por nombre.
	 * 
	 * @param name Nombre del usuario a eliminar
	 * @return ResponseEntity con resultado de la operación
	 */
	@DeleteMapping("/deletebyname")
	public ResponseEntity<String> deleteByName(@RequestParam String name) {
		return usuarioService.deleteByUsername(name) == 0 ? ResponseEntity.ok("Usuario eliminado exitosamente")
				: ResponseEntity.notFound().build();
	}

}
