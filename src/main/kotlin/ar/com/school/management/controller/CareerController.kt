package ar.com.school.management.controller

import ar.com.school.management.models.request.CareerRequest
import ar.com.school.management.models.response.ApiErrorResponse
import ar.com.school.management.models.response.CareerResponse
import ar.com.school.management.service.CareerService
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
@RequestMapping("/career")
@Schema(
    name = "Careers management",
    description = "Here admins and moderators can use the features provided for the management of a career"
)
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
    @PostMapping("/save")
    fun registerCareer(@Valid @RequestBody request: CareerRequest): ResponseEntity<CareerResponse> =
        ResponseEntity.status(HttpStatus.CREATED).body(careerService.save(request))
}