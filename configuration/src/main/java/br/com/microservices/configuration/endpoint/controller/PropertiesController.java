package br.com.microservices.configuration.endpoint.controller;

import java.net.URISyntaxException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.microservices.core.model.Properties;
import br.com.microservices.core.repository.PropertiesRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping("v1/props")
@Api(value = "Endpoints to manage properties", authorizations = {
	      @Authorization(value="token", scopes = {})
	    })
public class PropertiesController {

	@Autowired
	private PropertiesRepository repository;

	@GetMapping
	@ApiOperation(value = "List all available properties", authorizations = {@Authorization(value = "Bearer")},  response = Properties[].class)
	public Iterable<Properties> getAll() {
		return repository.findAll();
	}

	@GetMapping("/{application}")
	@ApiOperation(value = "list all available properties by application", response = Properties[].class)
	public Iterable<Properties> get(@PathVariable() String application) {
		return repository.findByApplication(application);
	}

	@GetMapping("/{application}/{profile}")
	@ApiOperation(value = "list all available properties by application and profile", response = Properties[].class)
	public Iterable<Properties> get(@PathVariable() String application, @PathVariable() String profile) {
		return repository.findByApplicationAndProfile(application, profile);
	}

	@GetMapping("/{application}/{profile}/{label}")
	@ApiOperation(value = "list all available properties by application, profile and label", response = Properties[].class)
	public Iterable<Properties> get(@PathVariable() String application, @PathVariable() String profile,
			@PathVariable() String label) {
		return repository.findByApplicationAndProfileAndLabel(application, profile, label);
	}

	@GetMapping("/{application}/{profile}/{label}/{property}")
	@ApiOperation(value = "list all available properties by application, profile, label and property", response = Properties[].class)
	public Iterable<Properties> get(@PathVariable() String application, @PathVariable() String profile,
			@PathVariable() String label, @PathVariable() String property) {
		return repository.findByApplicationAndProfileAndLabelAndProperty(application, profile, label, property);
	}

	@PutMapping
	@ApiOperation(value = "Update property", response = Properties[].class)
	public Optional<Properties> update(@Valid @RequestBody Properties properties) throws URISyntaxException {
		Optional<Properties> existing = repository.findOneByApplicationAndProfileAndLabelAndProperty(
				properties.getApplication(), properties.getProfile(), properties.getLabel(),
				properties.getProperty());
		return existing.map(rProp -> {
			rProp.setValue(properties.getValue());
			return repository.save(rProp);
		});
	}

	@PostMapping
	@ApiOperation(value = "Create property", response = Properties[].class)
	public Properties create(@Valid @RequestBody Properties properties) {
		return repository.save(properties);
	}
	
	@DeleteMapping
	@ApiOperation(value = "Delete property", response = Properties[].class)
	public Optional<Properties> delete(@PathVariable() Long id ) {
		Optional<Properties> existing = repository.findById(id);
		if (existing.isPresent()) {
			repository.delete(existing.get());
		}
		return existing;
	}
}
