package co.edu.unbosque.proyectoFinalC3.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.hibernate.collection.spi.PersistentCollection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT).setPropertyCondition(context -> {
			if (context.getSource() instanceof PersistentCollection) {
				return ((PersistentCollection) context.getSource()).wasInitialized();
			}
			return true;
		});

		return modelMapper;
	}
}
