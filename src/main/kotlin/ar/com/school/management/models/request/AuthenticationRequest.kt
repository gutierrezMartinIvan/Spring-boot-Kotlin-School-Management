package ar.com.school.management.models.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

data class AuthenticationRequest(
    @field:NotBlank(message = "Email can't be null, empty or blank")
    @Schema(title = "The Student's email", example = "example@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    var email: String,

    @field:NotBlank(message = "Password can't be null, empty or blank")
    @Schema(title = "The user's password", example = "paSSwo3Rd", requiredMode = Schema.RequiredMode.REQUIRED)
    var pw: String,
)
