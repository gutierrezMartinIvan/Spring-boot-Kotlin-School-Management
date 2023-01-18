package ar.com.school.management.service

import ar.com.school.management.models.request.UserRequest
import ar.com.school.management.models.response.TeacherResponse

interface TeacherService {
    fun save(request: UserRequest): TeacherResponse
    fun getTeacherBySocialSecurityNumber(ssNumber: Int): TeacherResponse
}