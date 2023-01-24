package ar.com.school.management.models.response

import ar.com.school.management.utils.Mark
import ar.com.school.management.utils.Status

data class StudentSubjectResponse(
    var studentName: String?,
    var subjectName: String?,
    var mark: Mark?,
    var status: Status?
) {
    constructor(): this(null, null, null, null)
}
