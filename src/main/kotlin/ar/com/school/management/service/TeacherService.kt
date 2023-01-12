package ar.com.school.management.service

import ar.com.school.management.models.request.TeacherRequest
import ar.com.school.management.models.response.TeacherResponse

interface TeacherService {
    fun save(request: TeacherRequest): TeacherResponse
    fun getTeacherBySocialSecurityNumber(ssNumber: Int): TeacherResponse
}