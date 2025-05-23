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

/**
 * Entidad que representa un archivo en el sistema.
 * <p>
 * Mapea a la tabla "archivo" en la base de datos y contiene información sobre
 * conversiones de archivos realizadas por usuarios.
 */
@Entity
@Table(name = "archivo")
public class Archivo {

	/**
	 * Identificador único del archivo (autogenerado)
	 */
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Integer id;

	/**
	 * Tipo de archivo (video o imagen)
	 */
	@Enumerated(EnumType.STRING)
	private Tipo tipo;

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
	 * Usuario que realizó la conversión
	 */
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	@JsonBackReference
	private Usuario usuario;

	/**
	 * Enum que define los tipos de archivo soportados.
	 */
	public enum Tipo {
		VIDEO, IMAGEN
	}

	/**
	 * Constructor por defecto.
	 */
	public Archivo() {

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

	/**
	 * Constructor con parámetros para inicializar todos los campos.
	 * 
	 * @param tipo                  Tipo de archivo
	 * @param nombre                Nombre del archivo
	 * @param tipoArchivoOriginal   Formato original del archivo
	 * @param tipoArchivoConvertido Formato convertido del archivo
	 * @param fechaConversion       Fecha y hora de la conversión
	 * @param exito                 Indica si la conversión fue exitosa
	 * @param usuario               Indica con que Usuario esta unido
	 */
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
	public Tipo getTipo() {
		return tipo;
	}

	/**
	 * @param tipo Tipo general a asignar al archivo
	 */
	public void setTipo(Tipo tipo) {
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
	 * @return usuario del cual es el archivo
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario usuario al cual fue asignado
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
