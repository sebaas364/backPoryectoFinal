package co.edu.unbosque.proyectoFinalC3.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Entidad que representa un usuario en el sistema.
 * <p>
 * Esta clase implementa la interfaz {@link UserDetails} de Spring Security para
 * la autenticación y autorización. Mapea a la tabla "usuario" en la base de
 * datos y contiene información sobre credenciales, roles y archivos convertidos
 * por el usuario.
 * </p>
 * 
 * <p>
 * Características principales:
 * </p>
 * <ul>
 * <li>Autenticación mediante Spring Security</li>
 * <li>Relación uno-a-muchos con archivos convertidos</li>
 * <li>Soporte para roles de usuario (USER, ADMIN)</li>
 * <li>Validación de credenciales</li>
 * </ul>
 */
@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails {

	/**
	 * Identificador único del usuario.
	 */
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Integer id;

	/**
	 * Versión serial para la serialización.
	 */
	private static final long serialVersionUID = -4615719286144010194L;

	/**
	 * Nombre de usuario único.
	 */
	@Column(unique = true)
	private String username;

	/**
	 * Correo electrónico único.
	 */
	@Column(unique = true)
	private String correo;

	/**
	 * Contraseña encriptada del usuario.
	 */
	private String password;

	/**
	 * Rol del usuario (USER o ADMIN).
	 */
	@Enumerated(EnumType.STRING)
	private Role role;

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
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonBackReference
	private List<Archivo> historial = new ArrayList<>();

	/**
	 * Roles disponibles para los usuarios del sistema.
	 */
	public enum Role {
		/** Rol de usuario normal */
		USER,
		/** Rol de administrador con privilegios */
		ADMIN
	}

	/**
	 * Constructor por defecto que inicializa valores predeterminados:
	 * <ul>
	 * <li>credentialsNonExpired = true</li>
	 * <li>enabled = true</li>
	 * <li>role = USER</li>
	 * </ul>
	 */
	public Usuario() {
		this.credentialsNonExpired = true;
		this.enabled = true;
		this.role = Role.USER;
	}

	/**
	 * Constructor con credenciales básicas.
	 * 
	 * @param username Nombre de usuario
	 * @param password Contraseña del usuario
	 */
	public Usuario(String username, String password) {
		this();
		this.username = username;
		this.password = password;
	}

	/**
	 * Constructor con credenciales y rol específico.
	 * 
	 * @param username Nombre de usuario
	 * @param password Contraseña del usuario
	 * @param role     Rol del usuario (USER o ADMIN)
	 */
	public Usuario(String username, String password, Role role) {
		this(username, password);
		this.role = role;
	}

	/**
	 * Constructor completo con todos los campos.
	 * 
	 * @param username              Nombre de usuario
	 * @param correo                Correo electrónico
	 * @param password              Contraseña
	 * @param role                  Rol del usuario
	 * @param credentialsNonExpired Estado de expiración de credenciales
	 * @param enabled               Estado de habilitación del usuario
	 * @param historial             Lista de archivos convertidos por el usuario
	 */
	public Usuario(String username, String correo, String password, Role role, boolean credentialsNonExpired,
			boolean enabled, List<Archivo> historial) {
		this.username = username;
		this.correo = correo;
		this.password = password;
		this.role = role;
		this.credentialsNonExpired = credentialsNonExpired;
		this.enabled = enabled;
		this.historial = historial;
	}

	/**
	 * Obtiene los permisos/roles del usuario para Spring Security.
	 * 
	 * @return Colección de autoridades (roles) del usuario
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
	}

	/**
	 * Compara este usuario con otro objeto.
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
		Usuario other = (Usuario) obj;
		return Objects.equals(id, other.id) && Objects.equals(password, other.password)
				&& Objects.equals(username, other.username);
	}

	// Métodos de UserDetails con documentación completa

	/**
	 * Obtiene la contraseña del usuario.
	 * 
	 * @return Contraseña del usuario
	 */
	@Override
	public String getPassword() {
		return password;
	}

	/**
	 * Obtiene el nombre de usuario.
	 * 
	 * @return Nombre de usuario
	 */
	@Override
	public String getUsername() {
		return username;
	}

	/**
	 * Verifica si la cuenta no ha expirado.
	 * 
	 * @return true si la cuenta no ha expirado, false en caso contrario
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * Verifica si la cuenta no está bloqueada.
	 * 
	 * @return true si la cuenta no está bloqueada, false en caso contrario
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * Verifica si las credenciales no han expirado.
	 * 
	 * @return true si las credenciales no han expirado, false en caso contrario
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	/**
	 * Verifica si el usuario está habilitado.
	 * 
	 * @return true si el usuario está habilitado, false en caso contrario
	 */
	@Override
	public boolean isEnabled() {
		return enabled;
	}

	// Resto de getters y setters con documentación

	/**
	 * Obtiene el ID del usuario.
	 * 
	 * @return ID del usuario
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Establece el ID del usuario.
	 * 
	 * @param id ID a asignar
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Obtiene el correo electrónico del usuario.
	 * 
	 * @return Correo electrónico
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * Establece el correo electrónico del usuario.
	 * 
	 * @param correo Correo electrónico a asignar
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	/**
	 * Obtiene el rol del usuario.
	 * 
	 * @return Rol del usuario
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * Establece el rol del usuario.
	 * 
	 * @param role Rol a asignar
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * Establece si las credenciales han expirado.
	 * 
	 * @param credentialsNonExpired Estado a asignar
	 */
	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	/**
	 * Establece si el usuario está habilitado.
	 * 
	 * @param enabled Estado a asignar
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Obtiene el historial de archivos convertidos por el usuario.
	 * 
	 * @return Lista de archivos convertidos
	 */
	public List<Archivo> getHistorial() {
		return historial;
	}

	/**
	 * Establece el historial de archivos convertidos por el usuario.
	 * 
	 * @param historial Lista de archivos a asignar
	 */
	public void setHistorial(List<Archivo> historial) {
		this.historial = historial;
	}

	/**
	 * Establece el nombre de usuario.
	 * 
	 * @param username Nombre de usuario a asignar
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Establece la contraseña del usuario.
	 * 
	 * @param password Contraseña a asignar
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Representación en String del objeto Usuario.
	 * 
	 * @return String que representa al usuario
	 */
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", username=" + username + ", correo=" + correo + ", password=" + password
				+ ", role=" + role + ", credentialsNonExpired=" + credentialsNonExpired + ", enabled=" + enabled
				+ ", historial=" + historial + "]";
	}

}
