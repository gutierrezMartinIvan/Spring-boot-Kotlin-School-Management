package ar.com.school.management.service

import ar.com.school.management.models.request.CareerRequest
import ar.com.school.management.models.response.CareerResponse
import ar.com.school.management.models.response.StudentResponse

interface CareerService {
    fun save(request: CareerRequest): CareerResponse
    fun signUpStudent2Career(careerId: Long, studentSsN: Int): StudentResponse
    fun addSubject2Career(careerId: Long, subjectId: Long): CareerResponse
    fun getCareerById(id: Long): CareerResponse
    fun getCareers(): List<CareerResponse>
}