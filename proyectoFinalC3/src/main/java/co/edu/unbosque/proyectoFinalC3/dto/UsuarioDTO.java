package co.edu.unbosque.proyectoFinalC3.dto;

import java.util.Objects;

import co.edu.unbosque.proyectoFinalC3.model.Usuario.Role;


public class UsuarioDTO {

	private Integer id;
	private String nombre;
	private String correo;
	private String username;
	private String password;
	private Role role;
	
	public UsuarioDTO() {
		
	}

	public UsuarioDTO(Integer id, String nombre, String correo, String username, String password, Role role) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.correo = correo;
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
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
	
	@Override
	public int hashCode() {
		// TODO atributos que se les tiene que hacer hash 
		
		return super.hashCode();
	}
}
