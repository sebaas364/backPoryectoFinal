package co.edu.unbosque.proyectoFinalC3.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * DTO (Data Transfer Object) que representa un usuario del sistema.
 * <p>
 * Contiene información del usuario incluyendo credenciales, roles, estado de la
 * cuenta e historial de archivos convertidos.
 * </p>
 */
public class UsuarioDTO {

	/**
	 * Identificador único del usuario.
	 */
	private Integer id;

	/**
	 * Nombre de usuario único.
	 */
	private String username;

	/**
	 * Correo electrónico único.
	 */
	private String correo;

	/**
	 * Contraseña encriptada del usuario.
	 */
	private String password;

	/**
	 * Rol del usuario (USER o ADMIN).
	 */
	private String role;

	/**
	 * Indica si las credenciales del usuario han expirado.
	 */
	private boolean credentialsNonExpired;
	
    /**
     * Indica si el usuario está habilitado.
     */
	private boolean enabled;
	
    /**
     * Lista de archivos convertidos por el usuario.
     */
	private List<ArchivoDTO> historial = new ArrayList<>();

	/**
	 * Constructor por defecto que inicializa valores básicos: -
	 * credentialsNonExpired = false - enabled = false - role = "USER"
	 */
	public UsuarioDTO() {
		this.credentialsNonExpired = true;
		this.enabled = false;
		this.role = "USER";
	}

	/**
	 * Constructor básico con nombre de usuario y contraseña.
	 * 
	 * @param username Nombre de usuario
	 * @param password Contraseña
	 */
	public UsuarioDTO(String username, String password) {
		this.username = username;
		this.password = password;
	}

	/**
	 * Constructor con nombre de usuario, contraseña y rol.
	 * 
	 * @param username Nombre de usuario
	 * @param password Contraseña
	 * @param role     Rol del usuario (USER/ADMIN)
	 */
	public UsuarioDTO(String username, String password, String role) {
		this.username = username;
		this.password = password;
		this.role = role;
	}

	/**
	 * Constructor completo con todos los campos.
	 * 
	 * @param username              Nombre de usuario
	 * @param correo                Correo electrónico
	 * @param password              Contraseña
	 * @param role                  Rol del usuario
	 * @param credentialsNonExpired Indica si las credenciales han expirado
	 * @param enabled               Indica si el usuario está habilitado
	 * @param historial             Lista de archivos convertidos por el usuario
	 */
	public UsuarioDTO(String username, String correo, String password, String role, boolean credentialsNonExpired,
			boolean enabled, List<ArchivoDTO> historial) {
		this.username = username;
		this.correo = correo;
		this.password = password;
		this.role = role;
		this.credentialsNonExpired = credentialsNonExpired;
		this.enabled = enabled;
		this.historial = historial;
	}

	/**
	 * Compara este UsuarioDTO con otro objeto.
	 * 
	 * @param obj Objeto a comparar
	 * @return true si son iguales, false en caso contrario
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		UsuarioDTO other = (UsuarioDTO) obj;
		return Objects.equals(id, other.id) && Objects.equals(password, other.password)
				&& Objects.equals(role, other.role) && Objects.equals(username, other.username);
	}

	// Getters y Setters con documentación

	/**
	 * @return ID único del usuario
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id ID único a asignar al usuario
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return Correo electrónico del usuario
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * @param correo Correo electrónico a asignar
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	/**
	 * @return Rol del usuario (USER/ADMIN)
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role Rol a asignar al usuario
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return true si las credenciales no han expirado, false en caso contrario
	 */
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	/**
	 * @param credentialsNonExpired Estado de expiración de credenciales a asignar
	 */
	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	/**
	 * @return true si el usuario está habilitado, false en caso contrario
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled Estado de habilitación a asignar
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return Lista de archivos convertidos por el usuario
	 */
	public List<ArchivoDTO> getHistorial() {
		return historial;
	}

	/**
	 * @param historial Lista de archivos a asignar como historial
	 */
	public void setHistorial(List<ArchivoDTO> historial) {
		this.historial = historial;
	}

	/**
	 * @return Nombre de usuario
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username Nombre de usuario a asignar
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return Contraseña del usuario
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password Contraseña a asignar
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return Representación en String del objeto UsuarioDTO
	 */
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", username=" + username + ", correo=" + correo + ", password=" + password
				+ ", role=" + role + ", credentialsNonExpired=" + credentialsNonExpired + ", enabled=" + enabled
				+ ", historial=" + historial + "]";
	}

}
