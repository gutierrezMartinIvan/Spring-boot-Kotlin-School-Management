package ar.com.school.management.controller

import ar.com.school.management.models.request.SubjectRequest
import ar.com.school.management.models.response.ApiErrorResponse
import ar.com.school.management.models.response.StudentResponse
import ar.com.school.management.models.response.SubjectResponse
import ar.com.school.management.models.response.TeacherResponse
import ar.com.school.management.service.SubjectService
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
@RequestMapping("/subject")
@Tag(name = "Subjects Management",
    description = "Here admins and moderators can use the features provided for the management of subjects")
@SecurityRequirement(name = "Bearer Authentication")
class SubjectController {
    @Autowired
    private lateinit var subjectService: SubjectService

    @Operation(
        summary = "Save a new subject",
        description = "This feature lets admins and moderators be able to register a new subject at the university app."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Subject saved correctly!"),
            ApiResponse(responseCode = "409", description = "Subject is already registered!",
                content = [(Content(schema = Schema(implementation = ApiErrorResponse::class)))])
        ]
    )
    @Transactional
    @PostMapping
    fun registerSubject(@Valid @RequestBody request: SubjectRequest) =
        ResponseEntity.status(HttpStatus.CREATED).body(subjectService.save(request))

    @Operation(
    summary = "Get a subject by its ID.",
    description = "This feature lets all users to get the information of a subject by its ID."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Subject found successfully!"),
            ApiResponse(responseCode = "404", description = "The ID does not belong to any subject!",
                content = [(Content(schema = Schema(implementation = ApiErrorResponse::class)))])
        ]
    )
    @GetMapping("/{id}")
    fun getSubjectById(@PathVariable id: Long): ResponseEntity<SubjectResponse> =
        ResponseEntity.status(HttpStatus.OK).body(subjectService.getSubjectById(id))

    @Operation(
        summary = "add a student to a subject",
        description = "This feature lets admins, moderators and teachers be able to register a student to a subject."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Student added correctly!"),
            ApiResponse(responseCode = "404", description = "Student or career not found!",
                content = [(Content(schema = Schema(implementation = ApiErrorResponse::class)))])
        ]
    )
    @Transactional
    @PostMapping("/{id}/{studentSsn}")
    fun addStudentToSubject(@PathVariable id: Long, @PathVariable studentSsn: Int): ResponseEntity<StudentResponse> = ResponseEntity.ok().body(subjectService.addStudent2Subject(id, studentSsn))

    @Operation(
        summary = "Add a teacher to a subject",
        description = "This feature lets admins, moderators and teachers be able to register a teacher to a subject."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Teacher added correctly!"),
            ApiResponse(responseCode = "404", description = "Teacher or career not found!",
                content = [(Content(schema = Schema(implementation = ApiErrorResponse::class)))])
        ]
    )
    @Transactional
    @PostMapping("/{id}/{teacherSsn}")
    fun addTeacherToSubject(@PathVariable id: Long, @PathVariable teacherSsn: Int): ResponseEntity<TeacherResponse> = ResponseEntity.ok().body(subjectService.addTeacher2Subject(id, teacherSsn))
}