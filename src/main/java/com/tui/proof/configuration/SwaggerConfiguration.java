package com.tui.proof.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up Swagger/OpenAPI documentation.
 */
@Configuration
public class SwaggerConfiguration {

    /**
     * Creates a custom OpenAPI bean with specific information.
     *
     * @return an OpenAPI instance with custom information.
     */
    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Java Engineer Technical Test")
                        .version("2.0")
                        .description("Create an Api rest that allows us to order the \"pilotes\" "
                                + "of the great Miquel Montoro")
                        .contact(new Contact()
                                .name("Alvaro Andres Maldonado Pinto")
                                .email("alvaro.maldonado1988@gmail.com")));
    }
}