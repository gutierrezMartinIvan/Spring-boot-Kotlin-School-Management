package ar.com.school.management.controller.errorHandler

import ar.com.school.management.exception.UserRegisteredException
import ar.com.school.management.models.response.ApiErrorResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

class RestExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [UserRegisteredException::class])
    fun handleUserAlreadyRegistered(ex: RuntimeException, request: WebRequest): ResponseEntity<Any>? {
        val error = ApiErrorResponse(
            HttpStatus.CONFLICT,
            ex.message,
            listOf("User Already Exists!")
        )
        return handleExceptionInternal(ex, error, HttpHeaders(), HttpStatus.CONFLICT, request)
    }
}