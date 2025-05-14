package co.edu.unbosque.proyectoFinalC3.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Collection;
import java.util.List;

import javax.management.relation.Role;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Integer id;
	private static final long serialVersionUID = -4615719286144010194L;
	private String nombre;
	@Column(unique = true)
	private String correo;
	@Column(unique = true)
	private String username;
	private String password;
	private Role role;

	public Usuario() {
		// TODO Auto-generated constructor stub
	}

	public Usuario(String nombre, String correo, String username, String password) {
		super();
		this.nombre = nombre;
		this.correo = correo;
		this.username = username;
		this.password = password;
	}

	public Usuario(String nombre, String correo, String username, String password, Role role) {
		this.nombre = nombre;
		this.correo = correo;
		this.username = username;
		this.password = password;
		this.role = role;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
	}

	public enum Role {
		// colocar los roles que se necesiten.
		USER, ADMIN
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
