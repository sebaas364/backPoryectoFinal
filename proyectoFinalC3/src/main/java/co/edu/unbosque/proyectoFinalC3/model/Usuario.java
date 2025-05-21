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

@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Integer id;
	private static final long serialVersionUID = -4615719286144010194L;
	@Column(unique = true)
	private String username;
	@Column(unique = true)
	private String correo;
	private String password;
	@Enumerated(EnumType.STRING)
	private Role role;
	private boolean credentialsNonExpired;
	private boolean enabled;
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Archivo> historial = new ArrayList<>();
	
	public enum Role {
		USER, ADMIN
	}

	public Usuario() {
		this.credentialsNonExpired = true;
		this.enabled = true;
		this.role = Role.USER;
	}
	
	public Usuario(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public Usuario(String username, String password, Role role) {
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public Usuario(String username, String correo, String password, Role role, boolean credentialsNonExpired,
			boolean enabled, List<Archivo> historial) {
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
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(id, other.id) && Objects.equals(password, other.password)
				&& Objects.equals(username, other.username);
	}
	
	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
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

	public List<Archivo> getHistorial() {
		return historial;
	}

	public void setHistorial(List<Archivo> historial) {
		this.historial = historial;
	}

	public void setUsername(String username) {
		this.username = username;
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
