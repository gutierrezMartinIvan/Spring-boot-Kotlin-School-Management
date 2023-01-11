package ar.com.school.management.models.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class StudentRequest(@NotNull(message = "The social security number can't be null")
                          @NotEmpty(message = "the social security number can't be empty")
                          @NotBlank(message = "the social security number can't be blank")
                          @Schema(title = "The user's social security number", example = "34584320", requiredMode = Schema.RequiredMode.REQUIRED)
                          val socialSecurityNumber: Int,

                          @NotNull(message = "The name can't be null")
                          @NotEmpty(message = "the name can't be empty")
                          @NotBlank(message = "the name can't be blank")
                          @Schema(title = "The user's name", example = "James", requiredMode = Schema.RequiredMode.REQUIRED)
                          val name: String,

                          @NotNull(message = "The surname can't be null")
                          @NotEmpty(message = "the surname can't be empty")
                          @NotBlank(message = "the surname can't be blank")
                          @Schema(title = "The user's surname", example = "Alvarez", requiredMode = Schema.RequiredMode.REQUIRED)
                          val surname: String,

                          @NotNull(message = "The surname can't be null")
                          @NotEmpty(message = "the surname can't be empty")
                          @NotBlank(message = "the surname can't be blank")
                          @Schema(title = "The user's phone number", example = "1212574574", requiredMode = Schema.RequiredMode.REQUIRED)
                          val phone: Int,

                          @NotNull(message = "The surname can't be null")
                          @Email(message = "Invalid email format")
                          @Schema(title = "The user's email", example = "example@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
                          val email: String,

                          @NotNull(message = "The password can't be null")
                          @NotEmpty(message = "the password can't be empty")
                          @NotBlank(message = "the password can't be blank")
                          @Schema(title = "The user's password", example = "paSSwo3Rd", requiredMode = Schema.RequiredMode.REQUIRED)
                          val password: String
)