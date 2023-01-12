package ar.com.school.management.service

import ar.com.school.management.models.request.CareerRequest
import ar.com.school.management.models.response.CareerResponse
import ar.com.school.management.models.response.StudentResponse

interface CareerService {
    fun save(request: CareerRequest): CareerResponse
    fun signUpStudent2Career(careerId: Long, studentSsN: Int): StudentResponse
}