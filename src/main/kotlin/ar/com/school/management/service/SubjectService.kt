package ar.com.school.management.service

import ar.com.school.management.models.request.SubjectRequest
import ar.com.school.management.models.response.StudentResponse
import ar.com.school.management.models.response.SubjectResponse
import ar.com.school.management.models.response.TeacherResponse

interface SubjectService {
    fun save(request: SubjectRequest): SubjectResponse
    fun getSubjectById(id: Long): SubjectResponse
    fun addStudent2Subject(id: Long, studentSnn: Int): StudentResponse
    fun addTeacher2Subject(id: Long, teacherSsn: Int): TeacherResponse
    fun getAll(): List<SubjectResponse>
}