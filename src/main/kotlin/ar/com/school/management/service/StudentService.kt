package ar.com.school.management.service

import ar.com.school.management.models.request.UserRequest
import ar.com.school.management.models.response.StudentResponse

interface StudentService {
    fun save(request: UserRequest): StudentResponse
    fun getStudentBySocialSecurityNumber(ssNumber: Int): StudentResponse
}