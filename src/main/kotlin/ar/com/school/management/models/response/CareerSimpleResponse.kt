package ar.com.school.management.models.response

import io.swagger.v3.oas.annotations.media.Schema

data class CareerSimpleResponse(
    @field:Schema(title = "The career's name", example = "Bachelor of Science in Computer and Information Systems")
    var name: String?,

    @field:Schema(title = "Career description", example = "The Bachelor of Science in computer is for students who like technology")
    var description: String?,

) {
    constructor() : this(null, null)
    constructor(name: String?) :this(name, "")
}