package co.edu.unbosque.proyectoFinalC3.configuration;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import co.edu.unbosque.proyectoFinalC3.model.Usuario;
import co.edu.unbosque.proyectoFinalC3.repository.UsuarioRepository;

@Configuration
public class LoadDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	@Bean
	CommandLineRunner initDatabase(UsuarioRepository userRepo, PasswordEncoder passwordEncoder) {
		return args -> {
			Optional<Usuario> found = userRepo.findByUsername("admin");
			if (found.isPresent()) {
				log.info("El administrador ya existe, omitiendo la creación del administrador...");
			} else {
				Usuario adminUser = new Usuario("admin", passwordEncoder.encode("1234567890"), Usuario.Role.ADMIN);
				adminUser.setEnabled(true);
				adminUser.setCredentialsNonExpired(true);
				userRepo.save(adminUser);
				log.info("Precargando usuario administrador");
			}
			Optional<Usuario> found2 = userRepo.findByUsername("user");
			if (found2.isPresent()) {
				log.info("El usuario normal ya existe, omitiendo la creación del usuario normal...");
			} else {
				Usuario normalUser = new Usuario("user", passwordEncoder.encode("1234567890"), Usuario.Role.USER);
				userRepo.save(normalUser);
				log.info("Precargando usuario normal");
			}
		};
	}
}
