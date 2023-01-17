package ar.com.school.management.config

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@io.swagger.v3.oas.annotations.security.SecurityScheme(
    name = "Bearer Authentication",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer"
)
class OpenAPIConfig {

    @Bean
    fun customOpenAPI(): OpenAPI = OpenAPI()
        .info(apiInfo())

    private fun apiInfo(): Info = Info()
        .title("University System Management and Functionalities")
        .version("0.9")
        .contact(apiContact())

    private fun apiContact(): Contact = Contact()
        .name("Martin Gutierrez")
        .email("martin192012@gmail.com")
        .url("https://www.linkedin.com/in/martgutierrez/")

}