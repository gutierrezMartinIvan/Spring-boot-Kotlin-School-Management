package ar.com.school.management.service

import ar.com.school.management.models.request.AuthenticationRequest
import ar.com.school.management.models.request.UserRequest
import ar.com.school.management.models.response.AuthenticationResponse
import ar.com.school.management.models.response.StudentResponse
import ar.com.school.management.models.response.StudentSubjectResponse

interface StudentService {
    fun save(request: UserRequest): StudentResponse
    fun getStudentBySocialSecurityNumber(ssNumber: Int): StudentResponse
    fun getAllStudents(): List<StudentResponse>
    fun updateStudent(ssNumber: Int, request: UserRequest): StudentResponse
    fun deleteStudent(ssNumber: Int)
    fun logIn(request: AuthenticationRequest): AuthenticationResponse
    fun getSubjectStatus(subjectId: Long): StudentSubjectResponse
}