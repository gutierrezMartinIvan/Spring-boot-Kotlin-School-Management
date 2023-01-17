package ar.com.school.management.models.response

data class AuthenticationResponse(
    var token: String?
) {
    constructor(): this(null)
}
