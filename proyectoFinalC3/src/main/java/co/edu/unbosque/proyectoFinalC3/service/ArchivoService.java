package co.edu.unbosque.proyectoFinalC3.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import co.edu.unbosque.proyectoFinalC3.dto.ArchivoDTO;
import co.edu.unbosque.proyectoFinalC3.dto.UsuarioDTO;
import co.edu.unbosque.proyectoFinalC3.model.Archivo;
import co.edu.unbosque.proyectoFinalC3.model.Usuario;
import co.edu.unbosque.proyectoFinalC3.repository.ArchivoRepository;
import co.edu.unbosque.proyectoFinalC3.repository.UsuarioRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.convertapi.client.ConvertApi;

@Service
public class ArchivoService {

	@Autowired
	private ArchivoRepository archivoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ModelMapper modelMapper;

	public ArchivoService() {

	}

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

	public List<ArchivoDTO> getByUsuarioId(Integer usuarioId) {
		List<Archivo> archivos = archivoRepository.findByUsuarioId(usuarioId);
		return archivos.stream().map(archivo -> modelMapper.map(archivo, ArchivoDTO.class))
				.collect(Collectors.toList());
	}

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

	public int deleteById(Integer archivoId) {
		Optional<Archivo> archivoOpt = archivoRepository.findById(archivoId);
		if (archivoOpt.isEmpty()) {
			return 1;
		}
		archivoRepository.delete(archivoOpt.get());
		return 0;
	}

	public List<ArchivoDTO> getAll() {
		List<Archivo> entityList = (List<Archivo>) archivoRepository.findAll();
		List<ArchivoDTO> dtoList = new ArrayList<>();
		entityList.forEach((entity) -> {
			ArchivoDTO dto = modelMapper.map(entity, ArchivoDTO.class);
			dtoList.add(dto);
		});
		return dtoList;
	}
	public int validarConvertirArchivo(String archivo, String fromFormat, String toFormat) {
		for (ArchivoDTO archivoDTO : getAll()) {
			if(archivoDTO.getNombre().equals(archivo)) {
				
				// Guardar archivo temporalmente
				Path archivoPath = Paths.get(archivoDTO.getNombre() );
				String url = ExternalHTTPRequestHandler.convertApi(fromFormat, toFormat, archivoPath.toString());
				if (url != null && !url.isEmpty()) {
					return 0;
				} else {
					return 1;
				}
			}else {
				return 0;
			}
		}
		return 1;
		
	}
	
	public String obtenerURLArchivoConv(String archivo, String fromFormat, String toFormat) {
		int resultado = validarConvertirArchivo(archivo,fromFormat,toFormat);
		
		if(resultado == 1) {
			return ExternalHTTPRequestHandler.convertApi(fromFormat, toFormat, toFormat);
		}else {
			return null;
		}
	}
}
