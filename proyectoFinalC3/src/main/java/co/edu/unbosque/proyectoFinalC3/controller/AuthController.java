package co.edu.unbosque.proyectoFinalC3.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.proyectoFinalC3.dto.UsuarioDTO;
import co.edu.unbosque.proyectoFinalC3.model.Usuario;
import co.edu.unbosque.proyectoFinalC3.security.JwtUtil;
import co.edu.unbosque.proyectoFinalC3.service.EmailVerificationService;
import co.edu.unbosque.proyectoFinalC3.service.UsuarioService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = { "*" })
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private EmailVerificationService emailService;

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
		int result = usuarioService.create(registerRequest);
		if (result == 0) {
			return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado exitosamente");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El nombre de usuario ya existe");
		}
	}

	@PostMapping("/send-verification")
	public ResponseEntity<?> sendVerificationCode(@RequestParam String email) {
		try {
			String verificationCode = emailService.sendVerificationCode(email);
			usuarioService.saveVerificationCode(email, verificationCode);

			return ResponseEntity.ok().body("Código de verificación enviado");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Error al enviar el código: " + e.getMessage());
		}
	}

	@PostMapping("/verify-code")
	public ResponseEntity<?> verifyCode(@RequestParam String email, @RequestParam String code) {

		if (usuarioService.validateVerificationCode(email, code)) {
			return ResponseEntity.ok().body("Cuenta verificada con éxito");
		} else {
			return ResponseEntity.badRequest().body("Código inválido o expirado");
		}
	}

	private static class AuthResponse {

		private final String token;

		private final String role;

		public AuthResponse(String token) {
			this.token = token;
			this.role = null;
		}

		public AuthResponse(String token, String role) {
			this.token = token;
			this.role = role;
		}

		public String getToken() {
			return token;
		}

		public String getRole() {
			return role;
		}
	}

}
