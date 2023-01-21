package ar.com.school.management.config.security

import ar.com.school.management.models.response.ApiErrorResponse
import ar.com.school.management.utils.Role
import jakarta.servlet.http.HttpServletResponse
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Autowired
    private lateinit var jwtAuthFilter: JwtAuthenticationFilter
    @Autowired
    private lateinit var authenticationProvider: AuthenticationProvider

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf().disable()
            .authorizeHttpRequests().requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

            .and().authorizeHttpRequests().requestMatchers(HttpMethod.PATCH, "/auth/admin/{ssNumber}").hasAuthority(Role.ADMIN.name)
            .and().authorizeHttpRequests().requestMatchers(HttpMethod.DELETE, "/auth/{ssNumber}").hasAuthority(Role.ADMIN.name)
            .and().authorizeHttpRequests().requestMatchers(HttpMethod.PATCH, "/auth/moderator/{ssNumber}").hasAnyAuthority(Role.ADMIN.name, Role.MODERATOR.name)
            .and().authorizeHttpRequests().requestMatchers(HttpMethod.GET, "/auth/{ssNumber}").authenticated()
            .and().authorizeHttpRequests().requestMatchers(HttpMethod.GET, "/auth").authenticated()
            .and().authorizeHttpRequests().requestMatchers(HttpMethod.POST, "/auth/authenticate", "/auth/register").permitAll()

            .and().authorizeHttpRequests().requestMatchers(HttpMethod.PATCH,"/student/{ssNumber}").hasAnyAuthority(Role.ADMIN.name, Role.MODERATOR.name)
            .and().authorizeHttpRequests().requestMatchers(HttpMethod.DELETE, "/student/{ssNumber}").hasAnyAuthority(Role.ADMIN.name, Role.MODERATOR.name)
            .and().authorizeHttpRequests().requestMatchers(HttpMethod.GET,"/student").authenticated()
            .and().authorizeHttpRequests().requestMatchers(HttpMethod.GET,"/student/{ssNumber}").authenticated()
            .and().authorizeHttpRequests().requestMatchers(HttpMethod.POST ,"/student").permitAll()

            .and().authorizeHttpRequests().requestMatchers(HttpMethod.POST, "/career").hasAnyAuthority(Role.STUDENT.name)
            .and().authorizeHttpRequests().requestMatchers(HttpMethod.PUT, "/career/{careerId}/add-student/{studentSsN}").hasAnyAuthority(Role.ADMIN.name, Role.MODERATOR.name)
            .and().authorizeHttpRequests().requestMatchers(HttpMethod.PUT, "/career/{careerId}/add-subject/{subjectId}").hasAnyAuthority(Role.ADMIN.name, Role.MODERATOR.name)
            .and().authorizeHttpRequests().requestMatchers(HttpMethod.GET,"/career").authenticated()
            .and().authorizeHttpRequests().requestMatchers(HttpMethod.GET,"/career/{id}").authenticated()

            .and().authorizeHttpRequests().requestMatchers(HttpMethod.POST, "/subject").hasAnyAuthority(Role.ADMIN.name, Role.MODERATOR.name)
            .and().authorizeHttpRequests().requestMatchers(HttpMethod.GET,"/subject/{id}").authenticated()

            .and().authorizeHttpRequests().requestMatchers(HttpMethod.POST, "/teacher").hasAnyAuthority(Role.ADMIN.name, Role.MODERATOR.name)
            .and().authorizeHttpRequests().requestMatchers(HttpMethod.GET,"/teacher/{ssNumber}").authenticated()


            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)

        http.exceptionHandling().authenticationEntryPoint { _, response, _ ->
            response.contentType = "application/json;charset=UTF-8"
            response.status = HttpServletResponse.SC_FORBIDDEN
            response.writer.write(
                JSONObject(
                    ApiErrorResponse(
                        HttpStatus.FORBIDDEN,
                        "Access denied",
                        arrayListOf("You dont have enough permissions to use this functionality")))
                    .toString())
        }
        return http.build()
    }
}