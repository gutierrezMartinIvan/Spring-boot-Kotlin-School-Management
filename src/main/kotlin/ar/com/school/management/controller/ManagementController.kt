package ar.com.school.management.controller

import ar.com.school.management.models.request.AuthenticationRequest
import ar.com.school.management.models.request.UserRequest
import ar.com.school.management.models.response.AuthenticationResponse
import ar.com.school.management.models.response.UserResponse
import ar.com.school.management.service.ManagementService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class ManagementController {

    @Autowired
    private lateinit var service: ManagementService

    @PostMapping("/register")
    fun register(@RequestBody request: UserRequest, @RequestParam role: String): ResponseEntity<UserResponse> =
        ResponseEntity.status(HttpStatus.CREATED).body(service.registerAdminOrModerator(request, role))

    @PostMapping("/authenticate")
    fun authenticate(@RequestBody authenticate: AuthenticationRequest): ResponseEntity<AuthenticationResponse> =
        ResponseEntity.ok(service.authenticate(authenticate))
}