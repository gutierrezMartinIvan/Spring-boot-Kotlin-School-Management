package ar.com.school.management.models.response

import org.springframework.http.HttpStatus

data class ApiErrorResponse(val status: HttpStatus,
                            val message: String?,
                            val errors: List<String>)
