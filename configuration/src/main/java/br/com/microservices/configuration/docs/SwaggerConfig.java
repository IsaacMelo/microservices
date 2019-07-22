package br.com.microservices.configuration.docs;

import org.springframework.context.annotation.Configuration;

import br.com.microservices.core.docs.BaseSwaggerConfig;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {
    public SwaggerConfig() {
        super("br.com.microservices.configuration.endpoint.controller");
    }
}
