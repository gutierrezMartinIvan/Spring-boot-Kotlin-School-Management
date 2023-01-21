package ar.com.school.management.models.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

data class CareerRequest(
    @field:NotBlank(message = "the name can't be blank")
    @field:Schema(title = "The career's name", example = "Bachelor of Science in Computer and Information Systems")
    val name: String?,

    @field:Schema(title = "Career description", example = "The Bachelor of Science in computer is for students who like technology")
    val description: String?)

