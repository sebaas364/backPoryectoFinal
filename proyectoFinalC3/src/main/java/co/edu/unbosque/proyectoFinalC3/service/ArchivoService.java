package co.edu.unbosque.proyectoFinalC3.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import co.edu.unbosque.proyectoFinalC3.dto.ArchivoDTO;
import co.edu.unbosque.proyectoFinalC3.model.Archivo;
import co.edu.unbosque.proyectoFinalC3.model.Usuario;
import co.edu.unbosque.proyectoFinalC3.repository.ArchivoRepository;
import co.edu.unbosque.proyectoFinalC3.repository.UsuarioRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Servicio para operaciones relacionadas con archivos.
 * <p>
 * Esta clase contiene la lógica de negocio para:
 * <ul>
 * <li>Gestión de archivos (creación, lectura, actualización, eliminación)</li>
 * <li>Conversión de formatos de archivos</li>
 * <li>Mapeo entre entidades Archivo y DTOs</li>
 * </ul>
 * </p>
 * 
 */
@Service
public class ArchivoService {

	@Autowired
	private ArchivoRepository archivoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Constructor por defecto.
	 */
	public ArchivoService() {

	}

	/**
	 * Crea un nuevo archivo asociado a un usuario.
	 * 
	 * @param archivoDTO DTO con los datos del archivo
	 * @param usuarioId  ID del usuario propietario
	 * @return Código de resultado:
	 *         <ul>
	 *         <li>0: Éxito</li>
	 *         <li>1: Usuario no encontrado</li>
	 *         <li>2: Tipo de archivo inválido</li>
	 *         </ul>
	 */
	public int create(ArchivoDTO archivoDTO, Integer usuarioId) {
		Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
		if (usuarioOpt.isEmpty()) {
			return 1;
		}
		Archivo archivo = modelMapper.map(archivoDTO, Archivo.class);
		try {
			archivo.setTipo(Archivo.Tipo.valueOf(archivoDTO.getTipo().toUpperCase()));
		} catch (IllegalArgumentException e) {
			return 2;
		}
		archivo.setUsuario(usuarioOpt.get());
		archivoRepository.save(archivo);
		return 0;
	}

	/**
	 * Obtiene todos los archivos de un usuario.
	 * 
	 * @param usuarioId ID del usuario
	 * @return Lista de DTOs de archivos del usuario
	 */
	public List<ArchivoDTO> getByUsuarioId(Integer usuarioId) {
		List<Archivo> archivos = archivoRepository.findByUsuarioId(usuarioId);
		return archivos.stream().map(archivo -> modelMapper.map(archivo, ArchivoDTO.class))
				.collect(Collectors.toList());
	}

	/**
	 * Actualiza un archivo existente.
	 * 
	 * @param archivoId ID del archivo a actualizar
	 * @param newData   Nuevos datos para el archivo
	 * @return Código de resultado:
	 *         <ul>
	 *         <li>0: Éxito</li>
	 *         <li>1: Archivo no encontrado</li>
	 *         <li>2: Tipo de archivo inválido</li>
	 *         </ul>
	 */
	public int update(Integer archivoId, ArchivoDTO newData) {
		Optional<Archivo> archivoOpt = archivoRepository.findById(archivoId);
		if (archivoOpt.isEmpty()) {
			return 1;
		}
		Archivo archivo = archivoOpt.get();
		archivo.setNombre(newData.getNombre());
		try {
			archivo.setTipo(Archivo.Tipo.valueOf(newData.getTipo().toUpperCase()));
		} catch (IllegalArgumentException e) {
			return 2;
		}
		archivoRepository.save(archivo);
		return 0;
	}

	/**
	 * Elimina un archivo por su ID.
	 * 
	 * @param archivoId ID del archivo a eliminar
	 * @return Código de resultado:
	 *         <ul>
	 *         <li>0: Éxito</li>
	 *         <li>1: Archivo no encontrado</li>
	 *         </ul>
	 */
	public int deleteById(Integer archivoId) {
		Optional<Archivo> archivoOpt = archivoRepository.findById(archivoId);
		if (archivoOpt.isEmpty()) {
			return 1;
		}
		archivoRepository.delete(archivoOpt.get());
		return 0;
	}

	/**
	 * Obtiene todos los archivos del sistema.
	 * 
	 * @return Lista de DTOs de todos los archivos
	 */
	public List<ArchivoDTO> getAll() {
		List<Archivo> entityList = (List<Archivo>) archivoRepository.findAll();
		List<ArchivoDTO> dtoList = new ArrayList<>();
		entityList.forEach((entity) -> {
			ArchivoDTO dto = modelMapper.map(entity, ArchivoDTO.class);
			dtoList.add(dto);
		});
		return dtoList;
	}

	/**
	 * Valida y realiza la conversión de un archivo.
	 * 
	 * @param archivo    Nombre del archivo a convertir
	 * @param fromFormat Formato origen
	 * @param toFormat   Formato destino
	 * @return Código de resultado:
	 *         <ul>
	 *         <li>0: Conversión exitosa</li>
	 *         <li>1: Error en la conversión</li>
	 *         </ul>
	 */
	public int validarConvertirArchivo(String archivo, String fromFormat, String toFormat) {
		for (ArchivoDTO archivoDTO : getAll()) {
			if (archivoDTO.getNombre().equals(archivo)) {
				Path archivoPath = Paths.get(archivoDTO.getNombre());
				String url = ExternalHTTPRequestHandler.convertApi(fromFormat, toFormat, archivoPath.toString());
				if (url != null && !url.isEmpty()) {
					return 0;
				} else {
					return 1;
				}
			} else {
				return 0;
			}
		}
		return 1;
	}

	/**
	 * Obtiene la URL del archivo convertido.
	 * 
	 * @param archivo    Nombre del archivo
	 * @param fromFormat Formato origen
	 * @param toFormat   Formato destino
	 * @return URL del archivo convertido o null si hay error
	 */
	public String obtenerURLArchivoConv(String archivo, String fromFormat, String toFormat) {
		int resultado = validarConvertirArchivo(archivo, fromFormat, toFormat);
		if (resultado == 1) {
			return ExternalHTTPRequestHandler.convertApi(fromFormat, toFormat, toFormat);
		} else {
			return null;
		}
	}

}
