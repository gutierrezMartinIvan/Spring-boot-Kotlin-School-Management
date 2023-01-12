package ar.com.school.management.models.response

import io.swagger.v3.oas.annotations.media.Schema

data class CareerResponse(
    @field:Schema(title = "Career id", example = "1")
    var id: Long?,

    @field:Schema(title = "The career's name", example = "Bachelor of Science in Computer and Information Systems")
    var name: String?,

    @field:Schema(title = "Career description", example = "The Bachelor of Science in computer is for students who like technology")
    var description: String?,

    @field:Schema(title = "Career teachers", implementation = TeacherSimpleResponse::class)
    var teachers: MutableList<TeacherSimpleResponse>?,

    @field:Schema(title = "Career students", implementation = StudentSimpleResponse::class)
    var students: MutableList<StudentSimpleResponse>?
) {
    constructor() : this(null,null, null, null, null)
}
