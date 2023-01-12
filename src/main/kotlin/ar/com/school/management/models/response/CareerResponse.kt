package ar.com.school.management.models.response

import ar.com.school.management.config.UserViewConfig
import com.fasterxml.jackson.annotation.JsonView
import io.swagger.v3.oas.annotations.media.Schema

class CareerResponse(
    @field:Schema(title = "Career id", example = "1")
    var id: Long?,

    @field:Schema(title = "The career's name", example = "Bachelor of Science in Computer and Information Systems")
    var name: String?,

    @field:Schema(title = "Career description", example = "The Bachelor of Science in computer is for students who like technology")
    var description: String?,

    @field:Schema(title = "Career teachers", implementation = TeacherResponse::class)
    @JsonView(value = [UserViewConfig.Internal::class])
    var teachers: MutableList<TeacherResponse>?
) {
    constructor() : this(null,null, null, null)
}