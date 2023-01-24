package ar.com.school.management.controller.errorHandler

import ar.com.school.management.exception.*
import ar.com.school.management.models.response.ApiErrorResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
@RestControllerAdvice
class RestExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [AlreadyRegisteredException::class])
    fun handleUserAlreadyRegistered(ex: RuntimeException, request: WebRequest): ResponseEntity<Any>? {
        val error = ApiErrorResponse(
            HttpStatus.CONFLICT,
            ex.message,
            listOf("Already Registered!")
        )
        return handleExceptionInternal(ex, error, HttpHeaders(), HttpStatus.CONFLICT, request)
    }

    @ExceptionHandler(value = [NotFoundException::class])
    fun handleNotFoundException(ex: RuntimeException, request: WebRequest): ResponseEntity<Any>? {
        val error = ApiErrorResponse(
            HttpStatus.NOT_FOUND,
            ex.message,
            listOf("Not Found Exception")
        )
        return handleExceptionInternal(ex, error, HttpHeaders(), HttpStatus.NOT_FOUND, request)
    }

    @ExceptionHandler(value = [CareerRegisteredException::class])
    fun handleCareerAlreadyRegistered(ex: RuntimeException, request: WebRequest): ResponseEntity<Any>? {
        val error = ApiErrorResponse(
            HttpStatus.CONFLICT,
            ex.message,
            listOf("Career Already Exists!")
        )
        return handleExceptionInternal(ex, error, HttpHeaders(), HttpStatus.CONFLICT, request)
    }

    @ExceptionHandler(value = [InvalidRoleException::class])
    fun handleInvalidRoleException(ex: RuntimeException, request: WebRequest): ResponseEntity<Any>? {
        val error = ApiErrorResponse(
            HttpStatus.BAD_REQUEST,
            ex.message,
            listOf("Role Not Found")
        )
        return handleExceptionInternal(ex, error, HttpHeaders(), HttpStatus.BAD_REQUEST, request)
    }
    @ExceptionHandler(value = [InvalidPasswordException::class])
    fun handleInvalidPassword(ex: RuntimeException, request: WebRequest): ResponseEntity<Any>? {
        val error = ApiErrorResponse(
            HttpStatus.BAD_REQUEST,
            ex.message,
            listOf("Please verify your email and password")
        )
        return handleExceptionInternal(ex, error, HttpHeaders(), HttpStatus.BAD_REQUEST, request)
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