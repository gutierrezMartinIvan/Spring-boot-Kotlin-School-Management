package ar.com.school.management.models.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class UserRequest(
    @field:NotNull(message = "The social security number can't be null")
    @Schema(title = "The student's social security number", example = "34584320", requiredMode = Schema.RequiredMode.REQUIRED)
    var socialSecurityNumber: Int?,

    @field:NotNull(message = "The name can't be null")
    @field:NotEmpty(message = "the name can't be empty")
    @field:NotBlank(message = "the name can't be blank")
    @Schema(title = "The student's name", example = "James")
    var name: String?,

    @field:NotNull(message = "The surname can't be null")
    @field:NotEmpty(message = "the surname can't be empty")
    @field:NotBlank(message = "the surname can't be blank")
    @Schema(title = "The student's surname", example = "Alvarez", requiredMode = Schema.RequiredMode.REQUIRED)
    var surname: String?,

    @field:NotNull(message = "The surname can't be null")
    @Schema(title = "The student's phone number", example = "1212574574", requiredMode = Schema.RequiredMode.REQUIRED)
    var phone: Int?,

    @field:NotNull(message = "The email can't be null")
    @field:NotEmpty(message = "the email can't be empty")
    @field:NotBlank(message = "the email can't be blank")
    @field:Email(message = "Invalid email format")
    @field:Schema(title = "The student's email", example = "example@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    var email: String?,

    @field:NotNull(message = "The password can't be null")
    @field:NotEmpty(message = "the password can't be empty")
    @field:NotBlank(message = "the password can't be blank")
    @Schema(title = "The student's password", example = "paSSwo3Rd", requiredMode = Schema.RequiredMode.REQUIRED)
    var pw: String?
)
