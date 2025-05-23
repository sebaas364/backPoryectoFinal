package co.edu.unbosque.proyectoFinalC3.configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración personalizada para la documentación OpenAPI/Swagger.
 * <p>
 * Esta clase define la configuración de documentación de la API REST
 * incluyendo:
 * <ul>
 * <li>Esquema de seguridad JWT</li>
 * <li>Información general de la API</li>
 * <li>Respuestas HTTP estandarizadas</li>
 * <li>Ejemplos de uso</li>
 * </ul>
 * </p>
 */
@Configuration
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public class OpenApiConfig {

	/**
	 * Configuración personalizada de OpenAPI.
	 * <p>
	 * Define la documentación completa de la API incluyendo:
	 * <ul>
	 * <li>Metadatos e información general</li>
	 * <li>Mecanismo de autenticación JWT</li>
	 * <li>Respuestas HTTP comunes</li>
	 * <li>Ejemplos de errores</li>
	 * </ul>
	 * </p>
	 * 
	 * @return Configuración personalizada de OpenAPI
	 */
	@Bean
	public OpenAPI customOpenAPI() {
		String mainDescription = "<h2>Guía para principiantes de la API REST</h2>"
				+ "<p>Esta API proporciona funcionalidades para gestionar usuarios y autenticación mediante JWT.</p>"
				+ "<h3>Conceptos básicos:</h3>" + "<ul>"
				+ "    <li><strong>JWT (JSON Web Token)</strong>: Un estándar para crear tokens de acceso.</li>"
				+ "    <li><strong>Autenticación</strong>: Proceso de verificar tu identidad mediante credenciales.</li>"
				+ "    <li><strong>Autorización</strong>: Determina qué acciones puede realizar un usuario según su rol.</li>"
				+ "</ul>";
		String securityDescription = "Autenticación mediante JWT (JSON Web Token)."
				+ "<p>Para autenticarte, sigue estos pasos:</p>" + "<ol>"
				+ "    <li>Obtén un token JWT usando el endpoint <code>/auth/login</code></li>"
				+ "    <li>Incluye el token en el encabezado Authorization: <code>Bearer tu_token_jwt</code></li>"
				+ "</ol>";
		io.swagger.v3.oas.models.info.Info info = new io.swagger.v3.oas.models.info.Info()
				.title("API de Primera Aplicación Spring").version("1.0").description(mainDescription)
				.contact(new io.swagger.v3.oas.models.info.Contact().name("Equipo de Desarrollo")
						.email("soporte@ejemplo.com").url("https://github.com/tu-usuario/SpringFirstAppJWT"))
				.license(new io.swagger.v3.oas.models.info.License().name("Licencia MIT")
						.url("https://opensource.org/licenses/MIT"));
		io.swagger.v3.oas.models.security.SecurityScheme securityScheme = new io.swagger.v3.oas.models.security.SecurityScheme()
				.type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
				.description(securityDescription);
		SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");
		return new OpenAPI().info(info).addSecurityItem(securityRequirement).components(new Components()
				.addSecuritySchemes("bearerAuth", securityScheme)
				.addResponses("UnauthorizedError",
						new ApiResponse().description("No autenticado - Token JWT inválido o expirado")
								.content(new Content().addMediaType("application/json",
										new MediaType().addExamples("error",
												new Example().value("{\"error\": \"No autorizado\", \"mensaje\":"
														+ " \"Token inválido o expirado\"}")))))
				.addResponses("ForbiddenError",
						new ApiResponse().description("Acceso prohibido - No tienes permisos suficientes")
								.content(new Content().addMediaType("application/json",
										new MediaType().addExamples("error",
												new Example().value("{\"error\": \"Acceso prohibido\", \"mensaje\":"
														+ " \"No tienes permisos para esta" + " operación\"}")))))
				.addResponses("NotFoundError",
						new ApiResponse().description("Recurso no encontrado")
								.content(new Content().addMediaType("application/json",
										new MediaType().addExamples("error",
												new Example().value("{\"error\": \"No encontrado\", \"mensaje\":"
														+ " \"El recurso solicitado no" + " existe\"}"))))));
	}

}
