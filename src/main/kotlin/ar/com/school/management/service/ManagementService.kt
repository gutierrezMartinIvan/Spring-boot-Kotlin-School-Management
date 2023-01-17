package ar.com.school.management.service

import ar.com.school.management.models.request.AuthenticationRequest
import ar.com.school.management.models.request.UserRequest
import ar.com.school.management.models.response.AuthenticationResponse
import ar.com.school.management.models.response.UserResponse

interface ManagementService {
    fun registerAdminOrModerator(request: UserRequest, role: String): UserResponse
    fun authenticate(request: AuthenticationRequest): AuthenticationResponse
}