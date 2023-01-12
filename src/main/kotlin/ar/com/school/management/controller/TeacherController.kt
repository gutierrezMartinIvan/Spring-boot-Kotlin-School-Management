package ar.com.school.management.controller

import ar.com.school.management.config.UserViewConfig
import ar.com.school.management.models.request.TeacherRequest
import ar.com.school.management.models.response.ApiErrorResponse
import ar.com.school.management.models.response.TeacherResponse
import ar.com.school.management.service.TeacherService
import com.fasterxml.jackson.annotation.JsonView
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/teacher")
@Schema(
    name = "Teachers management",
    description = "Here admins, moderators and teachers themself can interact with the features provided to a teacher"
)
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
    @PostMapping("/save")
    fun registerTeacher(@Valid @RequestBody request: TeacherRequest): ResponseEntity<TeacherResponse> =
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
    @GetMapping("/get/{ssNumber}")
    @JsonView(UserViewConfig.Internal::class)
    fun getTeacherBySocialSecurityNumber(@Valid @PathVariable ssNumber: Int) = teacherService.getTeacherBySocialSecurityNumber(ssNumber)
}