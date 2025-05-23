package co.edu.unbosque.proyectoFinalC3;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Clase principal de la aplicación ProyectoFinalC3.
 * <p>
 * Esta clase inicia la aplicación Spring Boot y configura beans esenciales.
 * La anotación {@code @SpringBootApplication} habilita:
 * <ul>
 *   <li>Configuración automática de Spring</li>
 *   <li>Escaneo de componentes en este paquete y subpaquetes</li>
 *   <li>Registro de beans de configuración</li>
 * </ul>
 * </p>
 */
@SpringBootApplication
public class ProyectoFinalC3Application {

    /**
     * Punto de entrada principal para la aplicación Spring Boot.
     * <p>
     * Inicia el contexto de Spring y configura el servidor embebido.
     * </p>
     * 
     * @param args Argumentos de línea de comandos (opcionales)
     */
    public static void main(String[] args) {
        SpringApplication.run(ProyectoFinalC3Application.class, args);
    }

    /**
     * Configura y provee una instancia de {@link ModelMapper}.
     * <p>
     * {@code ModelMapper} es una librería para mapeo automático entre objetos DTO y entidades.
     * Este bean estará disponible para inyección en cualquier componente Spring.
     * </p>
     * 
     * @return Nueva instancia configurada de ModelMapper
     * @see org.modelmapper.ModelMapper
     */
    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
    
}
