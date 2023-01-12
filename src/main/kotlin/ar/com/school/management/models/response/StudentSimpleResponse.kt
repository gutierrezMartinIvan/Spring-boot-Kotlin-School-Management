package ar.com.school.management.models.response

import io.swagger.v3.oas.annotations.media.Schema

data class StudentSimpleResponse(
    @field:Schema(title = "Student social security number", example = "4534654623")
    var socialSecurityNumber: Int?,

    @field:Schema(title = "Student's name", example = "Ivan")
    var name: String?,

    @field:Schema(title = "The student's surname", example = "Parker")
    var surname: String?,

    @field:Schema(title = "The student's email", example = "iParker@gmail.com")
    var email: String?
) {
    constructor() : this(null, null, null, null)
}
