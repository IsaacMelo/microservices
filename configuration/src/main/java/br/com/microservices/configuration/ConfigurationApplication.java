package br.com.microservices.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import br.com.microservices.core.property.JwtConfiguration;

@SpringBootApplication
@EntityScan({ "br.com.microservices.core.model" })
@EnableJpaRepositories({ "br.com.microservices.core.repository" })
@ComponentScan("br.com.microservices")
@EnableConfigurationProperties(value = JwtConfiguration.class)
@EnableConfigServer
public class ConfigurationApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(ConfigurationApplication.class, args);
	}
}
