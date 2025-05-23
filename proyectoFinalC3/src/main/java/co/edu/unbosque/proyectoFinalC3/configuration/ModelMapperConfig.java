package co.edu.unbosque.proyectoFinalC3.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.hibernate.collection.spi.PersistentCollection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración personalizada para ModelMapper.
 * <p>
 * Define un bean de ModelMapper con configuración específica para:
 * <ul>
 *   <li>Estrategia de mapeo estricta</li>
 *   <li>Manejo de colecciones Hibernate</li>
 * </ul>
 * </p>
 */
@Configuration
public class ModelMapperConfig {

    /**
     * Configura y provee una instancia personalizada de ModelMapper.
     * <p>
     * La configuración incluye:
     * <ul>
     *   <li>Estrategia STRICT para mapeo preciso de propiedades</li>
     *   <li>Condición para manejar correctamente colecciones lazy de Hibernate</li>
     * </ul>
     * </p>
     * 
     * @return Instancia configurada de ModelMapper lista para inyección
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
            .setMatchingStrategy(MatchingStrategies.STRICT)
            .setPropertyCondition(context -> {
                if (context.getSource() instanceof PersistentCollection) {
                    return ((PersistentCollection) context.getSource()).wasInitialized();
                }
                return true;
            });

        return modelMapper;
    }
    
}
