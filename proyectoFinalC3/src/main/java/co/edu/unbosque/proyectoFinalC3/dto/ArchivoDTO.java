package co.edu.unbosque.proyectoFinalC3.dto;

import java.time.LocalDateTime;

public class ArchivoDTO {

	private Integer id;
	private String tipo;
	private String nombre;
	private String tipoArchivoOriginal;
	private String tipoArchivoConvertido;
	private LocalDateTime fechaConversion;
	private boolean exito;

	public ArchivoDTO() {

	}

	public ArchivoDTO(String tipo, String nombre, String tipoArchivoOriginal, String tipoArchivoConvertido,
			LocalDateTime fechaConversion, boolean exito) {
		super();
		this.tipo = tipo;
		this.nombre = nombre;
		this.tipoArchivoOriginal = tipoArchivoOriginal;
		this.tipoArchivoConvertido = tipoArchivoConvertido;
		this.fechaConversion = fechaConversion;
		this.exito = exito;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
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

	@Override
	public String toString() {
		return "Archivo [id=" + id + ", tipo=" + tipo + ", nombre=" + nombre + ", tipoArchivoOriginal="
				+ tipoArchivoOriginal + ", tipoArchivoConvertido=" + tipoArchivoConvertido + ", fechaConversion="
				+ fechaConversion + ", exito=" + exito + "]";
	}

}
