package ar.com.school.management.service.impl

import ar.com.school.management.exception.NotFoundException
import ar.com.school.management.models.entity.StudentEntity
import ar.com.school.management.models.entity.StudentSubjectEntity
import ar.com.school.management.models.entity.SubjectEntity
import ar.com.school.management.models.response.StudentSubjectResponse
import ar.com.school.management.repository.StudentSubjectRepository
import ar.com.school.management.service.StudentSubjectService
import ar.com.school.management.utils.Mapper
import ar.com.school.management.utils.Mark
import ar.com.school.management.utils.Status
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class StudentSubjectServiceImpl: StudentSubjectService {
    @Autowired
    private lateinit var repository: StudentSubjectRepository
    @Autowired
    private lateinit var mapper: Mapper

    override fun save(student: StudentEntity, subject: SubjectEntity, mark: String): StudentSubjectResponse {
        var entity = StudentSubjectEntity(null, student, subject, Mark.setMark(mark), Status.APPROVED, false)
        return mapper.map(repository.save(entity), StudentSubjectResponse::class.java)
    }

    override fun getBySubjectAndStudent(subject: SubjectEntity, student: StudentEntity): StudentSubjectResponse =
        mapper.map(repository.findBySubjectAndStudent(subject, student).orElseThrow { NotFoundException("User") }
            , StudentSubjectResponse::class.java)

    override fun getAllBySubject(subject: SubjectEntity): List<StudentSubjectResponse> =
        mapper.mapLists(repository.findAllBySubject(subject), StudentSubjectResponse::class.java)

    override fun getAllByStudent(student: StudentEntity): List<StudentSubjectResponse> =
        mapper.mapLists(repository.findAllByStudent(student), StudentSubjectResponse::class.java)

    override fun getAllByStatus(status: Status): List<StudentSubjectResponse> =
        mapper.mapLists(repository.findAllByStatus(status), StudentSubjectResponse::class.java)

    override fun getAllByMark(mark: Mark): List<StudentSubjectResponse> =
        mapper.mapLists(repository.findAllByMark(mark), StudentSubjectResponse::class.java)

    override fun updateMark(student: StudentEntity, subject: SubjectEntity, mark: Mark): StudentSubjectResponse {
        var entity = repository.findBySubjectAndStudent(subject, student).orElseThrow { NotFoundException("Not found") }
        entity.mark = mark
        return mapper.map(repository.save(entity), StudentSubjectResponse::class.java)
    }

    override fun updateStatus(subject: SubjectEntity, student: StudentEntity, status: Status): StudentSubjectResponse {
        var entity = repository.findBySubjectAndStudent(subject, student).orElseThrow { NotFoundException("Not found") }
        entity.status = status
        return mapper.map(repository.save(entity), StudentSubjectResponse::class.java)
    }

}