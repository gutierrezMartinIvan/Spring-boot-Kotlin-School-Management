package ar.com.school.management.service

import ar.com.school.management.models.request.SubjectRequest
import ar.com.school.management.models.response.SubjectResponse

interface SubjectService {
    fun save(request: SubjectRequest): SubjectResponse
    fun getSubjectById(id: Long): SubjectResponse
    fun addStudent2Subject(id: Long, ssn: Int): Any
}