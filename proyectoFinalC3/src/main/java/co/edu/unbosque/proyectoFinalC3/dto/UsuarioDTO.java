package co.edu.unbosque.proyectoFinalC3.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UsuarioDTO {

	private Integer id;
	private String username;
	private String correo;
	private String password;
	private String role;
	private boolean credentialsNonExpired;
	private boolean enabled;
	private List<ArchivoDTO> historial = new ArrayList<>();

	public UsuarioDTO() {
		this.credentialsNonExpired = false;
		this.enabled = false;
		this.role = "USER";
	}

	public UsuarioDTO(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public UsuarioDTO(String username, String password, String role) {
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public UsuarioDTO(String username, String correo, String password, String role, boolean credentialsNonExpired,
			boolean enabled, List<ArchivoDTO> historial) {
		super();
		this.username = username;
		this.correo = correo;
		this.password = password;
		this.role = role;
		this.credentialsNonExpired = credentialsNonExpired;
		this.enabled = enabled;
		this.historial = historial;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioDTO other = (UsuarioDTO) obj;
		return Objects.equals(id, other.id) && Objects.equals(password, other.password) && role == other.role
				&& Objects.equals(username, other.username);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<ArchivoDTO> getHistorial() {
		return historial;
	}

	public void setHistorial(List<ArchivoDTO> historial) {
		this.historial = historial;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", username=" + username + ", correo=" + correo + ", password=" + password
				+ ", role=" + role + ", credentialsNonExpired=" + credentialsNonExpired + ", enabled=" + enabled
				+ ", historial=" + historial + "]";
	}

}
