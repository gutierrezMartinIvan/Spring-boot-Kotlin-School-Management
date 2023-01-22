package ar.com.school.management.models.response

import ar.com.school.management.utils.Mark
import ar.com.school.management.utils.State
import io.swagger.v3.oas.annotations.media.Schema

data class SubjectInfoResponseForStudent(
    @field:Schema(title = "The subjects name", example = "Programming 1")
    var name: String?,

    @field:Schema(title = "The subject currently state", example = "FINISHED")
    var state: State?,

    @field:Schema(title = "The subject currently mark", example = "TEN")
    var mark: Mark?
) {
    constructor(): this(null, null, null)
}
