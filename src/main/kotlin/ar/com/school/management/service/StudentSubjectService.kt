package ar.com.school.management.service

import ar.com.school.management.models.entity.StudentEntity
import ar.com.school.management.models.entity.StudentSubjectEntity
import ar.com.school.management.models.entity.SubjectEntity
import ar.com.school.management.models.response.StudentSubjectResponse
import ar.com.school.management.utils.Mark
import ar.com.school.management.utils.Status

interface StudentSubjectService {
    fun save(student: StudentEntity, subject: SubjectEntity, mark: String): StudentSubjectResponse
    fun getBySubjectAndStudent(subject: SubjectEntity, student: StudentEntity): StudentSubjectResponse
    fun getAllBySubject(subject: SubjectEntity): List<StudentSubjectResponse>
    fun getAllByStudent(student: StudentEntity): List<StudentSubjectResponse>
    fun getAllByStatus(status: Status): List<StudentSubjectResponse>
    fun getAllByMark(mark: Mark): List<StudentSubjectResponse>
    fun updateMark(student: StudentEntity, subject: SubjectEntity, mark: Mark): StudentSubjectResponse
    fun updateStatus(subject: SubjectEntity, student: StudentEntity, status: Status): StudentSubjectResponse
}