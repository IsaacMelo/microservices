package br.com.microservices.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.com.microservices.core.model.Properties;

public interface PropertiesRepository extends CrudRepository<Properties, Long> {

	public List<Properties> findByApplication(String application);

	public List<Properties> findByApplicationAndProfile(String application, String profile);

	public List<Properties> findByApplicationAndProfileAndLabel(String application, String profile, String label);

	public List<Properties> findByApplicationAndProfileAndLabelAndProperty(String application, String profile, String label, String property);

	public Optional<Properties> findOneByApplicationAndProfileAndLabelAndProperty(String application, String profile,
			String label, String Property);

}
