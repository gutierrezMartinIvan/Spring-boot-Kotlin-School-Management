package ar.com.school.management.service

import ar.com.school.management.models.request.AuthenticationRequest
import ar.com.school.management.models.request.UserRequest
import ar.com.school.management.models.response.AuthenticationResponse
import ar.com.school.management.models.response.StudentResponse
import ar.com.school.management.models.response.TeacherResponse

interface TeacherService {
    fun save(request: UserRequest): TeacherResponse
    fun getTeacherBySocialSecurityNumber(ssNumber: Int): TeacherResponse
    fun getAllTeachers(): List<TeacherResponse>
    fun updateTeacher(ssNumber: Int, request: UserRequest): TeacherResponse
    fun deleteTeacher(ssNumber: Int)
    fun authenticate(request: AuthenticationRequest): AuthenticationResponse

}