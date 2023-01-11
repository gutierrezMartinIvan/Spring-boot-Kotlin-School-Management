package ar.com.school.management.controller.errorHandler

import ar.com.school.management.exception.UserRegisteredException
import ar.com.school.management.models.response.ApiErrorResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
@ControllerAdvice
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

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        var errors = mutableListOf<String>()
        ex.bindingResult.fieldErrors.forEach { errors.add("${it.field}: ${it.defaultMessage}") }
        ex.bindingResult.globalErrors.forEach { errors.add("${it.objectName}: ${it.defaultMessage}") }

        val errorResponse = ApiErrorResponse(HttpStatus.BAD_REQUEST, "Please follow schema example", errors)
        return handleExceptionInternal(
            ex, errorResponse, headers, errorResponse.status, request
        )
    }

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val errorResponse = ApiErrorResponse(HttpStatus.BAD_REQUEST, ex.message, listOf(ex.httpInputMessage.toString(), ex.toString()))
        return handleExceptionInternal(
            ex, errorResponse, headers, errorResponse.status, request
        )
    }
}