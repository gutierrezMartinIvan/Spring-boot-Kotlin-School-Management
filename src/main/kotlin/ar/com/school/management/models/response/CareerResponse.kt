package ar.com.school.management.models.response

import ar.com.school.management.models.entity.SubjectEntity
import ar.com.school.management.models.entity.TeacherEntity

class CareerResponse(
    var id: Long?,
    var name: String?,
    var description: String?
) {
    constructor() : this(null,null, null)
}