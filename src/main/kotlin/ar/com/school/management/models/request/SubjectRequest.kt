package ar.com.school.management.models.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class SubjectRequest(
    @field:NotNull(message = "The name can't be null")
    @field:NotEmpty(message = "the name can't be empty")
    @field:NotBlank(message = "the name can't be blank")
    @field:Schema(title = "The subject's name", example = "Programming 1")
    val name: String?
)
