package ar.com.school.management.models.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

data class SubjectRequest(
    @field:NotBlank(message = "the name can't be blank")
    @field:Schema(title = "The subject's name", example = "Programming 1")
    val name: String?
)
