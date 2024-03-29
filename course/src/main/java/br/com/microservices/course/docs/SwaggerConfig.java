package br.com.microservices.course.docs;

import org.springframework.context.annotation.Configuration;

import br.com.microservices.core.docs.BaseSwaggerConfig;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {
    public SwaggerConfig() {
        super("br.com.microservices.course.endpoint.controller");
    }
}
