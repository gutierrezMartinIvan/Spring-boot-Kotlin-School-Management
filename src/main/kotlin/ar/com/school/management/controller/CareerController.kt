package ar.com.school.management.controller

import ar.com.school.management.models.request.CareerRequest
import ar.com.school.management.models.response.ApiErrorResponse
import ar.com.school.management.models.response.CareerResponse
import ar.com.school.management.models.response.StudentResponse
import ar.com.school.management.service.CareerService
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
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/career")
@Tag(name = "Careers Management",
    description = "Here admins and moderators can use the features provided for the management of a career")
@SecurityRequirement(name = "Bearer Authentication")
class CareerController {
    @Autowired
    private lateinit var careerService: CareerService

    @Operation(
        summary = "Save a new career",
        description = "This feature lets admins and moderators be able to register a new career at the university app."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Career saved correctly!"),
            ApiResponse(responseCode = "409", description = "Career is already registered!",
                content = [(Content(schema = Schema(implementation = ApiErrorResponse::class)))])
        ]
    )
    @Transactional
    @PostMapping
    fun registerCareer(@Valid @RequestBody request: CareerRequest): ResponseEntity<CareerResponse> =
        ResponseEntity.status(HttpStatus.CREATED).body(careerService.save(request))

    @Operation(
        summary = "Get a career by its ID.",
        description = "This feature lets all users to get the information of a career by its ID."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Career found successfully!"),
            ApiResponse(responseCode = "404", description = "The ID does not belong to any career!",
                content = [(Content(schema = Schema(implementation = ApiErrorResponse::class)))])
        ]
    )
    @GetMapping("/{id}")
    fun getCareerById(@PathVariable id: Long): CareerResponse = careerService.getCareerById(id)

    @Operation(
        summary = "Get all the careers",
        description = "This feature lets all users to get the information of all the careers."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Careers found successfully!"),
        ]
    )
    @GetMapping
    fun getAllCareers(): ResponseEntity<List<CareerResponse>> = ResponseEntity.ok(careerService.getCareers())

    @Operation(
        summary = "Student sign up to career",
        description = "This feature lets admins and moderators to sign up a student in a career and students can sign up in a career too."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Student signed up successfully!"),
            ApiResponse(responseCode = "409", description = "Student is already registered in the career!",
                content = [(Content(schema = Schema(implementation = ApiErrorResponse::class)))]),
            ApiResponse(responseCode = "404", description = "Student or Career not found",
                content = [(Content(schema = Schema(implementation = ApiErrorResponse::class)))])
        ]
    )
    @Transactional
    @PutMapping("/{careerId}/add-student/{studentSsN}")
    fun signUpStudentToCareer(@PathVariable careerId: Long, @PathVariable studentSsN: Int): ResponseEntity<StudentResponse> =
        ResponseEntity.status(HttpStatus.OK).body(careerService.signUpStudent2Career(careerId, studentSsN))

    @Operation(
        summary = "Add a subject to a career",
        description = "This feature lets admins and moderators to add a subject in a career."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Teacher added successfully!"),
            ApiResponse(responseCode = "409", description = "Teacher is already added in the career!",
                content = [(Content(schema = Schema(implementation = ApiErrorResponse::class)))]),
            ApiResponse(responseCode = "404", description = "Teacher or Career not found",
                content = [(Content(schema = Schema(implementation = ApiErrorResponse::class)))])
        ]
    )
    @Transactional
    @PutMapping("/{careerId}/add-subject/{subjectId}")
    fun addSubjectToCareer(@PathVariable careerId: Long, @PathVariable subjectId: Long): ResponseEntity<CareerResponse> =
        ResponseEntity.status(HttpStatus.OK).body(careerService.addSubject2Career(careerId, subjectId))
}