package co.edu.unbosque.proyectoFinalC3.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "archivo")
public class Archivo {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Integer id;
	@Enumerated(EnumType.STRING)
	private Tipo tipo;
	private String nombre;
	private String tipoArchivoOriginal;
	private String tipoArchivoConvertido;
	private LocalDateTime fechaConversion;
	private boolean exito;
	@ManyToOne
	@JoinColumn(name = "usuario_id")
    @JsonBackReference
	private Usuario usuario;

	public enum Tipo {
		VIDEO, IMAGEN
	}

	public Archivo() {

	}

	public Archivo(Tipo tipo, String nombre, String tipoArchivoOriginal, String tipoArchivoConvertido,
			LocalDateTime fechaConversion, boolean exito) {
		super();
		this.tipo = tipo;
		this.nombre = nombre;
		this.tipoArchivoOriginal = tipoArchivoOriginal;
		this.tipoArchivoConvertido = tipoArchivoConvertido;
		this.fechaConversion = fechaConversion;
		this.exito = exito;
	}

	public Archivo(Tipo tipo, String nombre, String tipoArchivoOriginal, String tipoArchivoConvertido,
			LocalDateTime fechaConversion, boolean exito, Usuario usuario) {
		this.tipo = tipo;
		this.nombre = nombre;
		this.tipoArchivoOriginal = tipoArchivoOriginal;
		this.tipoArchivoConvertido = tipoArchivoConvertido;
		this.fechaConversion = fechaConversion;
		this.exito = exito;
		this.usuario = usuario;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipoArchivoOriginal() {
		return tipoArchivoOriginal;
	}

	public void setTipoArchivoOriginal(String tipoArchivoOriginal) {
		this.tipoArchivoOriginal = tipoArchivoOriginal;
	}

	public String getTipoArchivoConvertido() {
		return tipoArchivoConvertido;
	}

	public void setTipoArchivoConvertido(String tipoArchivoConvertido) {
		this.tipoArchivoConvertido = tipoArchivoConvertido;
	}

	public LocalDateTime getFechaConversion() {
		return fechaConversion;
	}

	public void setFechaConversion(LocalDateTime fechaConversion) {
		this.fechaConversion = fechaConversion;
	}

	public boolean isExito() {
		return exito;
	}

	public void setExito(boolean exito) {
		this.exito = exito;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "Archivo [id=" + id + ", tipo=" + tipo + ", nombre=" + nombre + ", tipoArchivoOriginal="
				+ tipoArchivoOriginal + ", tipoArchivoConvertido=" + tipoArchivoConvertido + ", fechaConversion="
				+ fechaConversion + ", exito=" + exito + "]";
	}

}
