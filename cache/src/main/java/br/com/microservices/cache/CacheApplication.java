package br.com.microservices.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import br.com.microservices.core.property.JwtConfiguration;

@SpringBootApplication
@EntityScan({ "br.com.microservices.core.model" })
@EnableJpaRepositories({ "br.com.microservices.core.repository" })
@EnableConfigurationProperties(value = JwtConfiguration.class)
@ComponentScan("br.com.microservices")
public class CacheApplication {

	public static void main(String[] args) {
		SpringApplication.run(CacheApplication.class, args);
	}

}
