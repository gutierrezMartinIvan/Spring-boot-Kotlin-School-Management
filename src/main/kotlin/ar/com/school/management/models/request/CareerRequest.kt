package ar.com.school.management.models.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class CareerRequest(
    @field:NotNull(message = "The name can't be null")
    @field:NotEmpty(message = "the name can't be empty")
    @field:NotBlank(message = "the name can't be blank")
    @field:Schema(title = "The career's name", example = "Bachelor of Science in Computer and Information Systems")
    val name: String?,

    @field:Schema(title = "Career description", example = "The Bachelor of Science in computer is for students who like technology")
    val description: String?)

