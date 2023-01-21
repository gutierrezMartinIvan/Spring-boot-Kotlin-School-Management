package ar.com.school.management.controller

import ar.com.school.management.models.request.AuthenticationRequest
import ar.com.school.management.models.request.UserRequest
import ar.com.school.management.models.response.ApiErrorResponse
import ar.com.school.management.models.response.AuthenticationResponse
import ar.com.school.management.models.response.UserResponse
import ar.com.school.management.service.ManagementService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
@Tag(name = "Management Features",
    description = "Here admins and moderators can be registered and also more features for them.")
@SecurityRequirement(name = "Bearer Authentication")
class ManagementController {

    @Autowired
    private lateinit var service: ManagementService

    @Operation(
        summary = "Register a moderator or admin",
        description = "This feature lets register admins and moderators."
    )
    @ApiResponses(value = [ ApiResponse(responseCode = "200", description = "Moderator or admin registered successfully!") ])
    @Transactional
    @PostMapping("/register")
    fun register(@RequestBody request: UserRequest, @RequestParam role: String): ResponseEntity<UserResponse> =
        ResponseEntity.status(HttpStatus.CREATED).body(service.registerAdminOrModerator(request, role))

    @Operation(
        summary = "Authenticate.",
        description = "This feature lets admins and moderators authenticate themself."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Authenticated successfully!"),
            ApiResponse(responseCode = "404", description = "The email does not belong to moderator or admin!",
                content = [(Content(schema = Schema(implementation = ApiErrorResponse::class)))])
        ]
    )
    @PostMapping("/authenticate")
    fun authenticate(@RequestBody authenticate: AuthenticationRequest): ResponseEntity<AuthenticationResponse> =
        ResponseEntity.ok(service.authenticate(authenticate))

    @Operation(
        summary = "Get a moderator or admin by its ID.",
        description = "This feature lets admins and moderators to get the information of other ones or themself by its ID."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Moderator or admin found successfully!"),
            ApiResponse(responseCode = "404", description = "The ID does not belong to any moderator or admin!",
                content = [(Content(schema = Schema(implementation = ApiErrorResponse::class)))])
        ]
    )
    @GetMapping("/{ssNumber}")
    fun getAdminOrModerator(@PathVariable ssNumber: Int): ResponseEntity<UserResponse> =
        ResponseEntity.ok(service.getUser(ssNumber))

    @Operation(
        summary = "Get all admins, moderators or both.",
        description = "This feature lets admins and moderators to get the information of all the admins, moderators or both of them."
    )
    @ApiResponses(value = [ ApiResponse(responseCode = "200", description = "Users found successfully!") ])
    @GetMapping
    fun getAll(@RequestParam role: String): ResponseEntity<List<UserResponse>> = ResponseEntity.ok(service.getAllAdmins(role))

    @Operation(
        summary = "Updates an admin",
        description = "This feature lets admins to update other admins or itself."
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Admin updated successfully!") ,
        ApiResponse(responseCode = "404", description = "The ID does not belong to any admin!",
            content = [(Content(schema = Schema(implementation = ApiErrorResponse::class)))])
        ]
    )
    @Transactional
    @PatchMapping("/admin/{ssNumber}")
    fun updateAdmin(@PathVariable ssNumber: Int, @RequestBody request: UserRequest): ResponseEntity<UserResponse> =
        ResponseEntity.ok(service.updateUser(ssNumber, request))

    @Operation(
        summary = "Updates a moderator",
        description = "This feature lets admins and moderator to update other moderators or itself."
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Moderator updated successfully!") ,
        ApiResponse(responseCode = "404", description = "The ID does not belong to any moderator!",
            content = [(Content(schema = Schema(implementation = ApiErrorResponse::class)))])
    ]
    )
    @Transactional
    @PatchMapping("/moderator/{ssNumber}")
    fun updateModerator(@PathVariable ssNumber: Int, @RequestBody request: UserRequest): ResponseEntity<UserResponse> =
        ResponseEntity.ok(service.updateUser(ssNumber, request))

    @Operation(
        summary = "Updates a moderator",
        description = "This feature lets admins and moderator to update other moderators or itself."
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Moderator updated successfully!") ,
        ApiResponse(responseCode = "404", description = "The ID does not belong to any moderator!",
            content = [(Content(schema = Schema(implementation = ApiErrorResponse::class)))])
    ]
    )
    @Transactional
    @DeleteMapping("/{ssNumber}")
    fun deleteModerator(@PathVariable ssNumber: Int): Unit =
        service.deleteModerator(ssNumber)
}