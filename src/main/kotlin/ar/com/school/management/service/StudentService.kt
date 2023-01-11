package ar.com.school.management.service

import ar.com.school.management.models.request.StudentRequest
import ar.com.school.management.models.response.StudentResponse

interface StudentService {
    fun save(request: StudentRequest): StudentResponse
}