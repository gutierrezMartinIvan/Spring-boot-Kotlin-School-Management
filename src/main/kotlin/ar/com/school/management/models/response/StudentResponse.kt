package ar.com.school.management.models.response

import ar.com.school.management.models.entity.SubjectEntity
import com.fasterxml.jackson.annotation.JsonIgnore
import io.swagger.v3.oas.annotations.media.Schema
data class StudentResponse(
    @Schema(title = "Student id", example = "1")
    var id: Long?,

    @Schema(title = "Student social security number", example = "4534654623")
    var socialSecurityNumber: Int?,

    @Schema(title = "Student's name", example = "James")
    var name: String?,

    @Schema(title = "The Student's surname", example = "Alvarez")
    var surname: String?,

    @Schema(title = "The Student's phone number", example = "1212574574")
    var phone: Int?,

    @field:Schema(title = "The Student's email", example = "example@gmail.com")
    var email: String?,

    @Schema(title = "Student career")
    var career: CareerSimpleResponse?,
    @JsonIgnore
    var subjects: List<SubjectEntity>?,
) {
    constructor() : this(null, null, null, null, null, null, null, null)
}
