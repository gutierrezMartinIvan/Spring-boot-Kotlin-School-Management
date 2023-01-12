package ar.com.school.management.models.response

import ar.com.school.management.models.entity.SubjectEntity
import ar.com.school.management.models.entity.TeacherEntity
import io.swagger.v3.oas.annotations.media.Schema

class CareerResponse(
    @Schema(title = "Career id", example = "1")
    var id: Long?,

    @Schema(title = "The career's name", example = "Bachelor of Science in Computer and Information Systems")
    var name: String?,

    @Schema(title = "Career description", example = "The Bachelor of Science in computer is for students who like technology")
    var description: String?
) {
    constructor() : this(null,null, null)
}