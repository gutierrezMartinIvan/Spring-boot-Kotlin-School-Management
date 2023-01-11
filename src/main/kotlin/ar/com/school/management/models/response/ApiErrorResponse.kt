package ar.com.school.management.models.response

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.http.HttpStatus

data class ApiErrorResponse(@Schema(description = "Http Status" , example = "Generic http status")
                            val status: HttpStatus,

                            @Schema(description = "Message error", example = "Generic message error")
                            val message: String?,

                            @Schema(description = "List of errors", example = "Generic list of errors")
                            val errors: List<String>)
