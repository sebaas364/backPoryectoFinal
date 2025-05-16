package co.edu.unbosque.proyectoFinalC3.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.proyectoFinalC3.dto.UsuarioDTO;
import co.edu.unbosque.proyectoFinalC3.model.Usuario;
import co.edu.unbosque.proyectoFinalC3.security.JwtUtil;
import co.edu.unbosque.proyectoFinalC3.service.UsuarioService;

@RestController
@RequestMapping("/autentificacion")
@CrossOrigin(origins = { "*" })
public class AuthController {

	private final AuthenticationManager authenticationManager;

	private final JwtUtil jwtUtil;

	private final UsuarioService usuarioService;

	public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UsuarioService usuarioService) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
		this.usuarioService = usuarioService;
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UsuarioDTO loginRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			String jwt = jwtUtil.generateToken(userDetails);

			String role = null;
			if (userDetails instanceof Usuario) {
				Usuario user = (Usuario) userDetails;
				role = user.getRole().name();
			}
            return ResponseEntity.ok(new AuthResponse(jwt, role));
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("Nombre de usuario o contraseña inválidos o usuario no encontrado");
		}
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UsuarioDTO registerRequest) {
		if (usuarioService.findUsernameAlreadyTaken(registerRequest.getUsername())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("El nombre de usuario ya existe");
		}
		int result = usuarioService.create(registerRequest);
		if (result == 0) {
			return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado exitosamente");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al registrar el usuario");
		}
	}

    private record AuthResponse(String token, String role) {
    	
    }

}
