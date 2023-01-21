package ar.com.school.management.controller

import ar.com.school.management.models.request.UserRequest
import ar.com.school.management.models.response.ApiErrorResponse
import ar.com.school.management.models.response.TeacherResponse
import ar.com.school.management.service.TeacherService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/teacher")
@Tag(name = "Teachers Management",
    description = "Here admins, moderators and teachers themself can interact with the features provided to a teacher")
@SecurityRequirement(name = "Bearer Authentication")
class TeacherController {
    @Autowired
    private lateinit var teacherService: TeacherService

    @Operation(
        summary = "Sign up a teacher.",
        description = "This feature lets a person be able to register at the university app."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Teacher registered correctly!"),
            ApiResponse(responseCode = "409", description = "Teacher is already registered!",
                content = [(Content(schema = Schema(implementation = ApiErrorResponse::class)))])
        ]
    )
    @Transactional
    @PostMapping
    fun registerTeacher(@Valid @RequestBody request: UserRequest): ResponseEntity<TeacherResponse> =
        ResponseEntity.status(HttpStatus.CREATED).body(teacherService.save(request))

    @Operation(
        summary = "Get a teacher by its Social Security Number.",
        description = "This feature lets admins, mods and the teacher itself to get the information from a teacher by its Social Security Number."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Teacher found successfully!"),
            ApiResponse(responseCode = "404", description = "The ID does not belong to any teacher!",
                content = [(Content(schema = Schema(implementation = ApiErrorResponse::class)))])
        ]
    )
    @GetMapping("/{ssNumber}")
    fun getTeacherBySocialSecurityNumber(@Valid @PathVariable ssNumber: Int) = teacherService.getTeacherBySocialSecurityNumber(ssNumber)
}