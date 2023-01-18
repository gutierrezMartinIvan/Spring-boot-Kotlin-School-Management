package ar.com.school.management.models.response

import ar.com.school.management.utils.Role
import io.swagger.v3.oas.annotations.media.Schema

data class TeacherResponse(
    @field:Schema(title = "Teacher id", example = "1")
    var id: Long?,

    @field:Schema(title = "Teacher social security number", example = "4534654623")
    var socialSecurityNumber: Int?,

    @field:Schema(title = "teacher's name", example = "James")
    var name: String?,

    @field:Schema(title = "The teacher's surname", example = "Alvarez")
    var surname: String?,

    @field:Schema(title = "The teacher's phone number", example = "1212574574")
    var phone: Int?,

    @field:Schema(title = "The teacher's email", example = "example@gmail.com")
    var email: String?,

    @field:Schema(title = "Teacher subjects", implementation = SubjectSimpleResponse::class)
    var subjects: MutableList<SubjectSimpleResponse>?,

    @Schema(title = "Teacher role")
    var role: Role?
) {
    constructor() : this(null,null,null,
        null,null,null,null, null)
}
