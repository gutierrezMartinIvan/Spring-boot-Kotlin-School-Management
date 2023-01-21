package ar.com.school.management.service

import ar.com.school.management.models.request.UserRequest
import ar.com.school.management.models.response.StudentResponse
import org.springframework.http.ResponseEntity

interface StudentService {
    fun save(request: UserRequest): StudentResponse
    fun getStudentBySocialSecurityNumber(ssNumber: Int): StudentResponse
    fun getAllStudents(): List<StudentResponse>
    fun updateStudent(ssNumber: Int, request: UserRequest): StudentResponse
}