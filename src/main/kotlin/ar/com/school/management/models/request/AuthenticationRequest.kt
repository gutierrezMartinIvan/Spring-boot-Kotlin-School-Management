package ar.com.school.management.models.request

import jakarta.validation.constraints.NotBlank

data class AuthenticationRequest(
    @field:NotBlank(message = "Email can't be null, empty or blank")
    var email: String,
    @field:NotBlank(message = "Password can't be null, empty or blank")
    var pw: String,
)
