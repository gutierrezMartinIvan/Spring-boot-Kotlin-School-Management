package ar.com.school.management.models.response

import io.swagger.v3.oas.annotations.media.Schema

data class SubjectSimpleResponse(
    @field:Schema(title = "Career id", example = "1")
    var id: Long?,

    @field:Schema(title = "The career's name", example = "Bachelor of Science in Computer and Information Systems")
    var name: String?,

    var teachers: MutableList<TeacherSimpleResponse>?
) {
    constructor() : this(null, null, null)
}
