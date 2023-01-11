package ar.com.school.management.models.response

import ar.com.school.management.models.entity.CareerEntity
import ar.com.school.management.models.entity.SubjectEntity

data class StudentResponse(
    var id: Long?,
    var socialSecurityNumber: Int?,
    var name: String?,
    var surname: String?,
    var phone: Int?,
    var email: String?,
    var password: String?,
    var careerId: CareerEntity?,
    var subjects: List<SubjectEntity>?,
) {
    constructor() : this(null, null, null, null, null, null, null, null, null)
}
