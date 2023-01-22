package ar.com.school.management.service.impl

import ar.com.school.management.exception.NotFoundException
import ar.com.school.management.models.entity.SubjectEntity
import ar.com.school.management.models.request.SubjectRequest
import ar.com.school.management.models.response.StudentResponse
import ar.com.school.management.models.response.SubjectResponse
import ar.com.school.management.repository.StudentRepository
import ar.com.school.management.repository.SubjectRepository
import ar.com.school.management.service.SubjectService
import ar.com.school.management.utils.Mapper
import ar.com.school.management.utils.Mark
import ar.com.school.management.utils.State
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SubjectServiceImpl: SubjectService {
    @Autowired
    private lateinit var studentRepository: StudentRepository
    @Autowired
    private lateinit var repository: SubjectRepository

    @Autowired
    private lateinit var mapper: Mapper
    override fun save(request: SubjectRequest): SubjectResponse {
        var entitySave = mapper.map(request, SubjectEntity::class.java)
        return mapper.map(repository.save(entitySave), SubjectResponse::class.java)
    }

    override fun getSubjectById(id: Long): SubjectResponse =
        mapper.map(repository.findById(id)
            .orElseThrow { NotFoundException("The id: $id does not belong to any subject registered") }, SubjectResponse::class.java)

    override fun addStudent2Subject(id: Long, ssn: Int): Any {
        val subjectEntity = repository.findById(id).orElseThrow{ NotFoundException("Subject not found with ID: $id") }
        val studentEntity = studentRepository.findBySocialSecurityNumber(ssn).orElseThrow{ NotFoundException("Student not found with SSN: $ssn") }
        subjectEntity.mark = Mark.ZERO
        subjectEntity.state = State.ONGOING
        subjectEntity.students?.add(studentEntity)
        studentEntity.subjects?.add(subjectEntity)
        repository.save(subjectEntity)
        return mapper.map(studentRepository.save(studentEntity), StudentResponse::class.java)
    }
}