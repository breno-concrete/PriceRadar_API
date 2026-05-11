package com.breno.PriceRadar_API.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Price Radar API")
                        .version("1.0.0")
                        .description("API para o sistema de rastreamento de preços.")
                        .contact(new Contact()
                                .name("Breno")
                                .email("brenocount@gmail.com")));
    }
}