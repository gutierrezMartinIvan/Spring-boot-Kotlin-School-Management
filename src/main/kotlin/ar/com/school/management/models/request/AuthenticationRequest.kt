package ar.com.school.management.models.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

data class AuthenticationRequest(
    @field:NotBlank(message = "Email can't be null, empty or blank")
    @Schema(title = "The User's email", example = "emilysmith@email.com", requiredMode = Schema.RequiredMode.REQUIRED)
    var email: String,

    @field:NotBlank(message = "Password can't be null, empty or blank")
    @Schema(title = "The user's password", example = "p@ssw0rd1", requiredMode = Schema.RequiredMode.REQUIRED)
    var pw: String,
)
