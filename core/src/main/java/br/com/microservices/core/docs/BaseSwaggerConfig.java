package br.com.microservices.core.docs;

import java.util.Collections;

import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

public class BaseSwaggerConfig {
	private final String basePackage;

	public BaseSwaggerConfig(String basePackage) {
		this.basePackage = basePackage;
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage(basePackage))
				.build().securitySchemes(Collections.singletonList(new ApiKey("token", "Authorization", "header")))
				.apiInfo(metaData());
	}

	private ApiInfo metaData() {
		return new ApiInfoBuilder().title("Doc Microservices")
				.description("microservices").version("1.0")
				.contact(new Contact("Administrator", "http://microservices",
						"administrator@gmail.com"))
				.license("Open").licenseUrl("http://microservices").build();
	}
}
