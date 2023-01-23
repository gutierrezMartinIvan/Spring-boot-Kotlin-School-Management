package ar.com.school.management.controller

import ar.com.school.management.models.request.AuthenticationRequest
import ar.com.school.management.models.request.UserRequest
import ar.com.school.management.models.response.ApiErrorResponse
import ar.com.school.management.models.response.AuthenticationResponse
import ar.com.school.management.models.response.StudentResponse
import ar.com.school.management.models.response.SubjectInfoResponseForStudent
import ar.com.school.management.service.StudentService
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
@RequestMapping("/student")
@Tag(name = "Students Management",
    description = "Here all the users can interact with the features provided to an student")
@SecurityRequirement(name = "Bearer Authentication")
class StudentController {
    @Autowired
    private lateinit var studentService: StudentService

    @Operation(
        summary = "Sign up a student.",
        description = "This feature lets a person be able to register at the university app."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Student registered correctly!"),
            ApiResponse(responseCode = "409", description = "Student is already registered!",
                        content = [(Content(schema = Schema(implementation = ApiErrorResponse::class)))])
        ]
    )
    @Transactional
    @PostMapping
    fun registerStudent(@Valid @RequestBody request: UserRequest): ResponseEntity<StudentResponse> =
        ResponseEntity.status(HttpStatus.CREATED).body(studentService.save(request))

    @Operation(
        summary = "Get a student by its Social Security Number.",
        description = "This feature lets all users to get the information from a student by its Social Security Number."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Student found successfully!"),
            ApiResponse(responseCode = "404", description = "The ssN does not belong to any student!",
                content = [(Content(schema = Schema(implementation = ApiErrorResponse::class)))])
        ]
    )
    @GetMapping("/get/{ssNumber}")
    fun getStudentBySocialSecurityNumber(@PathVariable ssNumber: Int): ResponseEntity<StudentResponse> =
        ResponseEntity.ok(studentService.getStudentBySocialSecurityNumber(ssNumber))

    @Operation(
        summary = "Get all the students.",
        description = "This feature lets admins and moderators to get the information from all the students."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Student found successfully!"),
        ]
    )
    @GetMapping
    fun getStudents(): ResponseEntity<List<StudentResponse>> = ResponseEntity.ok(studentService.getAllStudents())

    @Operation(
        summary = "Get student subject status",
        description = "This feature lets students to get the information from a subject that he/she has."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Student found successfully!"),
            ApiResponse(responseCode = "404", description = "The Ssn or ID does not belong to any student or subject!",
                content = [(Content(schema = Schema(implementation = ApiErrorResponse::class)))])
        ]
    )
    @GetMapping("/{subjectId}")
    fun getSubjectStatus(@PathVariable subjectId: Long): ResponseEntity<SubjectInfoResponseForStudent> =
        ResponseEntity.ok(studentService.getSubjectStatus(subjectId))

    @Operation(
        summary = "Updates a student",
        description = "This feature lets admins and moderators to update student info."
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Student updated successfully!") ,
        ApiResponse(responseCode = "404", description = "The ID does not belong to any student!",
            content = [(Content(schema = Schema(implementation = ApiErrorResponse::class)))])
    ]
    )
    @Transactional
    @PatchMapping("/{ssNumber}")
    fun updateStudent(@PathVariable ssNumber: Int, @RequestBody request: UserRequest): ResponseEntity<StudentResponse> =
        ResponseEntity.ok(studentService.updateStudent(ssNumber, request))

    @Operation(
        summary = "Delete a student",
        description = "This feature lets admins and moderator delete a student."
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Student deleted successfully!") ,
        ApiResponse(responseCode = "404", description = "The ID does not belong to any student!",
            content = [(Content(schema = Schema(implementation = ApiErrorResponse::class)))])
    ]
    )
    @Transactional
    @DeleteMapping("/{ssNumber}")
    fun deleteStudent(@PathVariable ssNumber: Int): Unit =
        studentService.deleteStudent(ssNumber)

    @Operation(
        summary = "Authenticate.",
        description = "This feature lets a student log in."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Authenticated successfully!"),
            ApiResponse(responseCode = "404", description = "The email does not belong to any student!",
                content = [(Content(schema = Schema(implementation = ApiErrorResponse::class)))])
        ]
    )
    @PostMapping("/logIn")
    fun logIn(@RequestBody authenticate: AuthenticationRequest): ResponseEntity<AuthenticationResponse> =
        ResponseEntity.ok(studentService.logIn(authenticate))
}