package ar.com.school.management.models.response

import io.swagger.v3.oas.annotations.media.Schema

data class TeacherSimpleResponse(
    @field:Schema(title = "Teacher social security number", example = "4534654623")
    var socialSecurityNumber: Int?,

    @field:Schema(title = "teacher's name", example = "James")
    var name: String?,

    @field:Schema(title = "The teacher's surname", example = "Alvarez")
    var surname: String?,

    @field:Schema(title = "The teacher's email", example = "example@gmail.com")
    var email: String?,
) {
    constructor() : this(null, null, null, null)
}
