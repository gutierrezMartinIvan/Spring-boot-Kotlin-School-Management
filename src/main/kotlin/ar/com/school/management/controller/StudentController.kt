package ar.com.school.management.controller

import ar.com.school.management.models.request.StudentRequest
import ar.com.school.management.models.response.StudentResponse
import ar.com.school.management.service.StudentService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/student")
@Schema(name = "Students management",
    description = "Here all the users can interact with the features provided to an student")
class StudentController {

    @Autowired
    private lateinit var studentService: StudentService

    @Operation(summary = "Sign up a student.",
        description = "This feature lets a person be able to register to the university app.")
    @PostMapping("/save")
    @ApiResponse(responseCode = "201", description = "Student registered correctly!")
    fun registerStudent(@RequestBody request: StudentRequest): ResponseEntity<StudentResponse>
        = ResponseEntity.status(HttpStatus.CREATED).body(studentService.save(request))
}