package ar.com.school.management.models.response

import ar.com.school.management.models.entity.SubjectEntity
import com.fasterxml.jackson.annotation.JsonIgnore

data class StudentResponse(
    var id: Long?,
    var socialSecurityNumber: Int?,
    var name: String?,
    var surname: String?,
    var phone: Int?,
    var email: String?,
    @JsonIgnore
    var careerId: CareerResponse?,
    @JsonIgnore
    var subjects: List<SubjectEntity>?,
) {
    constructor() : this(null, null, null, null, null, null, null, null)
}
