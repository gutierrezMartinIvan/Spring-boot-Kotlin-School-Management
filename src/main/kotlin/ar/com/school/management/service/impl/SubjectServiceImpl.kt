package ar.com.school.management.service.impl

import ar.com.school.management.exception.NotFoundException
import ar.com.school.management.exception.AlreadyRegisteredException
import ar.com.school.management.models.entity.SubjectEntity
import ar.com.school.management.models.request.SubjectRequest
import ar.com.school.management.models.response.StudentResponse
import ar.com.school.management.models.response.SubjectResponse
import ar.com.school.management.models.response.TeacherResponse
import ar.com.school.management.repository.StudentRepository
import ar.com.school.management.repository.SubjectRepository
import ar.com.school.management.repository.TeacherRepository
import ar.com.school.management.service.SubjectService
import ar.com.school.management.utils.Mapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SubjectServiceImpl: SubjectService {
    @Autowired
    private lateinit var studentRepository: StudentRepository
    @Autowired
    private lateinit var teacherRepository: TeacherRepository
    @Autowired
    private lateinit var subjectRepository: SubjectRepository

    @Autowired
    private lateinit var mapper: Mapper
    override fun save(request: SubjectRequest): SubjectResponse {
        val entitySave = mapper.map(request, SubjectEntity::class.java)
        return mapper.map(subjectRepository.save(entitySave), SubjectResponse::class.java)
    }

    override fun getSubjectById(id: Long): SubjectResponse {
        val entity = subjectRepository.findById(id)
            .orElseThrow { NotFoundException("The id: $id does not belong to any subject registered") }
        return mapper.map(entity, SubjectResponse::class.java)
    }


    override fun addStudent2Subject(id: Long, studentSsn: Int): StudentResponse {
        val subjectEntity = subjectRepository.findById(id).orElseThrow{ NotFoundException("Subject not found with ID: $id") }
        val studentEntity = studentRepository.findBySocialSecurityNumber(studentSsn).orElseThrow{ NotFoundException("Student not found with SSN: $studentSsn") }
        if (subjectEntity.students!!.contains(studentEntity))
            throw AlreadyRegisteredException("The student ${studentEntity.name} ${studentEntity.surname} already has the subject: ${subjectEntity.name}")
        subjectEntity.students?.add(studentEntity)
        studentEntity.subjects?.add(subjectEntity)
        subjectRepository.save(subjectEntity)
        return mapper.map(studentRepository.save(studentEntity), StudentResponse::class.java)
    }

    override fun addTeacher2Subject(id: Long, teacherSsn: Int): TeacherResponse {
        val subjectEntity = subjectRepository.findById(id).orElseThrow{ NotFoundException("Subject not found with ID: $id") }
        val teacherEntity = teacherRepository.findBySocialSecurityNumber(teacherSsn).orElseThrow{ NotFoundException("Teacher not found with SSN: $teacherSsn") }
        if (subjectEntity.teachers!!.contains(teacherEntity))
            throw AlreadyRegisteredException("The teacher ${teacherEntity.name} is already in the subject ${subjectEntity.name}")

        subjectEntity.teachers?.add(teacherEntity)
        teacherEntity.subjects?.add(subjectEntity)
        subjectRepository.save(subjectEntity)
        return mapper.map(teacherRepository.save(teacherEntity), TeacherResponse::class.java)
    }

    override fun getAll(): List<SubjectResponse> =
        mapper.mapLists(subjectRepository.findAll(), SubjectResponse::class.java)
}