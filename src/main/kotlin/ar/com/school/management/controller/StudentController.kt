package ar.com.school.management.controller

import ar.com.school.management.models.request.StudentRequest
import ar.com.school.management.models.response.ApiErrorResponse
import ar.com.school.management.models.response.StudentResponse
import ar.com.school.management.service.StudentService
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
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/student")
@Schema(
    name = "Students management",
    description = "Here all the users can interact with the features provided to an student"
)
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
            ApiResponse(responseCode = "409", description = "User is already registered!",
                        content = [(Content(schema = Schema(implementation = ApiErrorResponse::class)))])
        ]
    )
    @Transactional
    @PostMapping("/save")
    fun registerStudent(@Valid @RequestBody request: StudentRequest): ResponseEntity<StudentResponse> =
        ResponseEntity.status(HttpStatus.CREATED).body(studentService.save(request))
}