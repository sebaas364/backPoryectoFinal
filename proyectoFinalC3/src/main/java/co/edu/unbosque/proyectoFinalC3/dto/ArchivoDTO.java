package co.edu.unbosque.proyectoFinalC3.dto;

import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) que representa un archivo convertido en el
 * sistema.
 * <p>
 * Contiene información sobre la conversión de archivos, incluyendo tipos de
 * archivo, fecha de conversión y estado de la operación.
 * </p>
 */
public class ArchivoDTO {

	/**
	 * Identificador único del archivo (autogenerado)
	 */
	private Integer id;
	
	/**
	 * Tipo de archivo (video o imagen)
	 */
	private String tipo;
	
	/**
	 * Nombre del archivo
	 */
	private String nombre;

	/**
	 * Formato original del archivo antes de la conversión
	 */
	private String tipoArchivoOriginal;

	/**
	 * Formato resultante después de la conversión
	 */
	private String tipoArchivoConvertido;

	/**
	 * Fecha y hora en que se realizó la conversión
	 */
	private LocalDateTime fechaConversion;

	/**
	 * Indica si la conversión fue exitosa
	 */
	private boolean exito;

	/**
	 * Constructor por defecto.
	 */
	public ArchivoDTO() {
	}

	/**
	 * Constructor con parámetros para inicializar todos los campos.
	 * 
	 * @param tipo                  Tipo de archivo
	 * @param nombre                Nombre del archivo
	 * @param tipoArchivoOriginal   Formato original del archivo
	 * @param tipoArchivoConvertido Formato convertido del archivo
	 * @param fechaConversion       Fecha y hora de la conversión
	 * @param exito                 Indica si la conversión fue exitosa
	 */
	public ArchivoDTO(String tipo, String nombre, String tipoArchivoOriginal, String tipoArchivoConvertido,
			LocalDateTime fechaConversion, boolean exito) {
		this.tipo = tipo;
		this.nombre = nombre;
		this.tipoArchivoOriginal = tipoArchivoOriginal;
		this.tipoArchivoConvertido = tipoArchivoConvertido;
		this.fechaConversion = fechaConversion;
		this.exito = exito;
	}

	/**
	 * @return ID único del archivo
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id ID único a asignar al archivo
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return Tipo general del archivo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo Tipo general a asignar al archivo
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return Nombre del archivo
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre Nombre a asignar al archivo
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return Formato original del archivo antes de la conversión
	 */
	public String getTipoArchivoOriginal() {
		return tipoArchivoOriginal;
	}

	/**
	 * @param tipoArchivoOriginal Formato original a asignar
	 */
	public void setTipoArchivoOriginal(String tipoArchivoOriginal) {
		this.tipoArchivoOriginal = tipoArchivoOriginal;
	}

	/**
	 * @return Formato resultante después de la conversión
	 */
	public String getTipoArchivoConvertido() {
		return tipoArchivoConvertido;
	}

	/**
	 * @param tipoArchivoConvertido Formato convertido a asignar
	 */
	public void setTipoArchivoConvertido(String tipoArchivoConvertido) {
		this.tipoArchivoConvertido = tipoArchivoConvertido;
	}

	/**
	 * @return Fecha y hora en que se realizó la conversión
	 */
	public LocalDateTime getFechaConversion() {
		return fechaConversion;
	}

	/**
	 * @param fechaConversion Fecha y hora de conversión a asignar
	 */
	public void setFechaConversion(LocalDateTime fechaConversion) {
		this.fechaConversion = fechaConversion;
	}

	/**
	 * @return true si la conversión fue exitosa, false en caso contrario
	 */
	public boolean isExito() {
		return exito;
	}

	/**
	 * @param exito Estado de éxito a asignar a la conversión
	 */
	public void setExito(boolean exito) {
		this.exito = exito;
	}

	/**
	 * @return Representación en String del objeto ArchivoDTO
	 */
	@Override
	public String toString() {
		return "Archivo [id=" + id + ", tipo=" + tipo + ", nombre=" + nombre + ", tipoArchivoOriginal="
				+ tipoArchivoOriginal + ", tipoArchivoConvertido=" + tipoArchivoConvertido + ", fechaConversion="
				+ fechaConversion + ", exito=" + exito + "]";
	}

}
