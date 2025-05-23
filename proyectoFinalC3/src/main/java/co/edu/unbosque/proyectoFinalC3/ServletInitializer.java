package co.edu.unbosque.proyectoFinalC3;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Clase de inicialización para despliegue en servlet containers externos.
 * <p>
 * Extiende {@link SpringBootServletInitializer} para configurar la aplicación
 * cuando se despliega en un servidor de aplicaciones tradicional (Tomcat, Jetty, etc.)
 * en lugar de usar el servidor embebido de Spring Boot.
 * </p>
 * <p>
 * Esta clase es requerida cuando se genera un WAR para despliegue en servidores externos.
 * </p>
 */
public class ServletInitializer extends SpringBootServletInitializer {

    /**
     * Configura la aplicación Spring Boot para el despliegue en servlet container.
     * <p>
     * Este método es llamado por el servlet container durante el inicio para
     * inicializar el contexto de Spring correctamente.
     * </p>
     * 
     * @param application Builder para la configuración de la aplicación Spring Boot
     * @return El {@link SpringApplicationBuilder} configurado
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ProyectoFinalC3Application.class);
    }
    
}
