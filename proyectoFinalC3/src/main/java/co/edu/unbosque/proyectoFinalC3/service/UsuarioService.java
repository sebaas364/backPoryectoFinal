package co.edu.unbosque.proyectoFinalC3.service;

import co.edu.unbosque.proyectoFinalC3.dto.UsuarioDTO;
import co.edu.unbosque.proyectoFinalC3.model.Usuario;
import co.edu.unbosque.proyectoFinalC3.repository.UsuarioRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public UsuarioService() {

	}

	public long count() {
		return usuarioRepository.count();
	}

	public boolean exist(Integer id) {
		return usuarioRepository.existsById(id);
	}

	public int create(UsuarioDTO data) {
		Usuario entity = modelMapper.map(data, Usuario.class);
		if (findUsernameAlreadyTaken(entity.getUsername())) {
			return 1;
		} else {
			entity.setPassword(passwordEncoder.encode(entity.getPassword()));
			if (data.getRole() != null) {
				entity.setRole(Usuario.Role.valueOf(data.getRole().toUpperCase()));
			}
			usuarioRepository.save(entity);
			return 0;
		}
	}

	public List<UsuarioDTO> getAll() {
		List<Usuario> entityList = (List<Usuario>) usuarioRepository.findAll();
		List<UsuarioDTO> dtoList = new ArrayList<>();
		entityList.forEach((entity) -> {
			UsuarioDTO dto = modelMapper.map(entity, UsuarioDTO.class);
			dtoList.add(dto);
		});
		return dtoList;
	}

	public int deleteById(Integer id) {
		Optional<Usuario> found = usuarioRepository.findById(id);
		if (found.isPresent()) {
			usuarioRepository.delete(found.get());
			return 0;
		} else {
			return 1;
		}
	}

	public int deleteByUsername(String username) {
		Optional<Usuario> found = usuarioRepository.findByUsername(username);
		if (found.isPresent()) {
			usuarioRepository.delete(found.get());
			return 0;
		} else {
			return 1;
		}
	}

	public int updateById(Integer id, UsuarioDTO newData) {
		Optional<Usuario> found = usuarioRepository.findById(id);
		if (found.isEmpty()) {
			return 2;
		}
		Optional<Usuario> newFound = usuarioRepository.findByUsername(newData.getUsername());
		if (newFound.isPresent() && !newFound.get().getId().equals(id)) {
			return 1;
		}
		Usuario temp = found.get();
		temp.setUsername(newData.getUsername());
		if (newData.getPassword() != null && !newData.getPassword().isBlank()) {
			temp.setPassword(passwordEncoder.encode(newData.getPassword()));
		}
		if (newData.getRole() != null) {
			temp.setRole(Usuario.Role.valueOf(newData.getRole().toUpperCase()));
		}
		usuarioRepository.save(temp);
		return 0;
	}

	public UsuarioDTO getById(Integer id) {
		Optional<Usuario> found = usuarioRepository.findById(id);
		if (found.isPresent()) {
			return modelMapper.map(found.get(), UsuarioDTO.class);
		} else {
			return null;
		}
	}

	public boolean findUsernameAlreadyTaken(String username) {
		Optional<Usuario> found = usuarioRepository.findByUsername(username);
		return found.isPresent();
	}

	public int validateCredentials(String username, String password) {
		Optional<Usuario> userOpt = usuarioRepository.findByUsername(username);
		if (userOpt.isPresent()) {
			Usuario user = userOpt.get();
			if (passwordEncoder.matches(password, user.getPassword())) {
				return 0;
			}
		}
		return 1;
	}

}
