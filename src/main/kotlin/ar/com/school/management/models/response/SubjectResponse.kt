package ar.com.school.management.models.response

import io.swagger.v3.oas.annotations.media.Schema

data class SubjectResponse(
    @field:Schema(title = "Subject id", example = "1")
    var id: Long?,

    @field:Schema(title = "The subjects name", example = "Programming 1")
    var name: String?,

    @field:Schema(title = "The subjects students")
    var students: MutableList<StudentSimpleResponse>?,

    @field:Schema(title = "The subjects teachers")
    var teachers: MutableList<TeacherSimpleResponse>?,

    @field:Schema(title = "The subjects careers")
    var careers: MutableList<CareerSimpleResponse>?
) {
    constructor(): this(null, null, null, null, null)
}
