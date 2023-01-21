package ar.com.school.management.models.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class UserRequest(
    @field:NotNull(message = "The social security number can't be null")
    @Schema(title = "The user's social security number", example = "34584320", requiredMode = Schema.RequiredMode.REQUIRED)
    var socialSecurityNumber: Int?,

    @field:NotBlank(message = "the name can't be blank")
    @Schema(title = "The user's name", example = "James")
    var name: String?,

    @field:NotBlank(message = "the surname can't be blank")
    @Schema(title = "The user's surname", example = "Alvarez", requiredMode = Schema.RequiredMode.REQUIRED)
    var surname: String?,

    @field:NotNull(message = "The surname can't be null")
    @Schema(title = "The user's phone number", example = "1212574574", requiredMode = Schema.RequiredMode.REQUIRED)
    var phone: Int?,

    @field:NotBlank(message = "the email can't be blank")
    @field:Email(message = "Invalid email format")
    @field:Schema(title = "The user's email", example = "example@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    var email: String?,

    @field:NotBlank(message = "the password can't be blank")
    @Schema(title = "The user's password", example = "paSSwo3Rd", requiredMode = Schema.RequiredMode.REQUIRED)
    var pw: String?,
)
