package co.edu.unbosque.proyectoFinalC3.service;

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

}
