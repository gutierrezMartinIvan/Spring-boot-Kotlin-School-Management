package ar.com.school.management.repository

import ar.com.school.management.models.entity.StudentEntity
import ar.com.school.management.models.entity.StudentSubjectEntity
import ar.com.school.management.models.entity.SubjectEntity
import ar.com.school.management.utils.Mark
import ar.com.school.management.utils.Status
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface StudentSubjectRepository: JpaRepository<StudentSubjectEntity, Long> {

    fun findBySubjectAndStudent(subject: SubjectEntity, student: StudentEntity): Optional<StudentSubjectEntity>
    fun findAllBySubject(subject: SubjectEntity): List<StudentSubjectEntity>
    fun findAllByStudent(student: StudentEntity): List<StudentSubjectEntity>
    fun findAllByStatus(status: Status): List<StudentSubjectEntity>
    fun findAllByMark(mark: Mark): List<StudentSubjectEntity>
}